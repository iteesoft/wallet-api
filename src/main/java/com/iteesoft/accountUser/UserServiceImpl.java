package com.iteesoft.accountUser;

import com.iteesoft.KYC.*;
import com.iteesoft.account.Account;
import com.iteesoft.account.AccountRepository;
import com.iteesoft.role.Role;
import com.iteesoft.role.RoleDto;
import com.iteesoft.role.RoleRepository;
import com.iteesoft.shared.dto.TopUpDto;
import com.iteesoft.shared.dto.WithdrawalDto;
import com.iteesoft.shared.enums.TransactionLevel;
import com.iteesoft.shared.enums.TransactionStatus;
import com.iteesoft.shared.enums.TransactionType;
import com.iteesoft.shared.exceptions.*;
import com.iteesoft.shared.global_constants.Constants;
import com.iteesoft.transaction.Transaction;
import com.iteesoft.transaction.TransactionRepository;
import com.iteesoft.wallet.Wallet;
import com.iteesoft.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final AccountUserRepository userRepository;
    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;
    private final RoleRepository roleRepository;
    private final KycRepository kycRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private  final ModelMapper mapper;

    @Override
    public String createAccountUser(AccountUserDto userDto) {
        Optional<AppUser> user = userRepository.findByEmail(userDto.getEmail());
        user.ifPresent(u -> alreadyExist.accept(u.getEmail()));
        log.info("Saving new account user, {}", userDto.getEmail());

        KYC userKyc = createKycRecordForUser(userDto.getBvn());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        AppUser newUser = mapper.map(userDto, AppUser.class);
        newUser.setTransactionLevel(TransactionLevel.BASIC);
        newUser.setAccountVerified(true);
        newUser.setKycDetails(userKyc);
//            Collection<Role> roles = new ArrayList<>();
//            roles.add(new Role("USER"));
//            newUser.setRoles(roles);
        userRepository.save(newUser);
        createWallet(newUser);
        return "Account user created successfully";
    }

    Consumer<String> alreadyExist = (String email) -> {
        throw new UserWithEmailAlreadyExist("Email: " + email + " already exist");
    };

    private void createWallet(AppUser user) {
        Wallet userWallet = Wallet.builder().walletBalance(new BigDecimal("0.00"))
                .walletAccountNumber(generateRandomAccountNumber())
                .transactionPin("0000").transactionLevel(TransactionLevel.BASIC).build();
        walletRepository.save(userWallet);
        Account account = Account.builder().accountHolder(user).wallet(userWallet).build();
        accountRepository.save(account);
    }

    private KYC createKycRecordForUser(String bvn) {
        KYC userKyc = KYC.builder().bvn(bvn).approved(false).build();
        kycRepository.save(userKyc);
        return userKyc;
    }

    private AppUser verifyLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserWithEmailNotFound("USER_NOT_FOUND"));
    }

    BiConsumer<String, String> validatePin = (pin1, pin2) -> {
        if (Objects.equals(pin1, "0000")) {
            throw new WrongTransactionPin("For your security, you need to change your transaction pin before performing your first transaction.");
        }
        if (Objects.equals(pin1, pin2)) {
            throw new WrongTransactionPin("You have entered a wrong transaction pin!");
        }
    };

    BiConsumer<BigDecimal, BigDecimal> checkWithdrawalAmount = (request, balance) -> {
        if (request.compareTo(balance) > 0) {
            throw new InsufficientFundsException("You cannot make transaction below your wallet balance");
        }
    };

    Consumer<BigDecimal> checkForMinimumTransfer = (amount) -> {
        if (amount.compareTo(Constants.MINIMUM_TRANSFER_LIMIT) < 0) {
            throw new InvalidAmountException("Amount to topUp cannot be less than " + Constants.MINIMUM_TRANSFER_LIMIT);
        }
    };

    private Transaction generateTransaction(double amount, String summary, TransactionType type) {
        Transaction userTransaction = Transaction.builder()
                .transactionAmount(amount)
                .transactionStatus(TransactionStatus.SUCCESSFUL).transactionType(type)
                .dateAndTimeOfTransaction(Instant.now())
                .Summary(summary)
                .build();
        transactionRepository.save(userTransaction);
        return userTransaction;
    }

    @Override
    public Transaction topUpWalletBalance (TopUpDto topUpDto) {
        var topUp = BigDecimal.valueOf(topUpDto.getAmount());
        AppUser user = verifyLoggedInUser();
        log.info("Topping up user: {} wallet", user.getFirstName());
        Wallet walletToTopUp = Optional.ofNullable(accountRepository.findByAccountHolder(user).getWallet())
                .orElseThrow(() -> new UserWithEmailNotFound("Wallet was not found"));
        validatePin.accept(topUpDto.getTransactionPin(), walletToTopUp.getTransactionPin());
        checkForMinimumTransfer.accept(topUp);
        Transaction userTransaction = null;
        TransactionLevel userLevel = user.getTransactionLevel();

        switch(userLevel) {
            case BASIC -> {
                if (topUp.compareTo(Constants.BASIC_TRANSACTION_LIMIT) <= 0) {
                    userTransaction = generateTransaction(topUp.doubleValue(), topUpDto.getTransactionSummary(), TransactionType.DEPOSIT);
                } else {
                    throw new InvalidAmountException("Amount to topUp cannot be more than "+Constants.BASIC_TRANSACTION_LIMIT);
                }
            }
            case SILVER -> {
                if (topUp.compareTo(Constants.SILVER_TRANSACTION_LIMIT) <= 0) {
                    userTransaction = generateTransaction(topUp.doubleValue(), topUpDto.getTransactionSummary(), TransactionType.DEPOSIT);
                } else {
                    throw new InvalidAmountException("Amount to topUp cannot be more than "+Constants.SILVER_TRANSACTION_LIMIT);
                }
            }
            case GOLD -> {
                if (topUp.compareTo(Constants.GOLD_TRANSACTION_LIMIT) <= 0) {
                    userTransaction = generateTransaction(topUp.doubleValue(), topUpDto.getTransactionSummary(), TransactionType.DEPOSIT);
                } else {
                    throw new InvalidAmountException("Amount to topUp cannot be more than "+Constants.GOLD_TRANSACTION_LIMIT);
                }
            }
        }
        walletRepository.save(walletToTopUp);
        return userTransaction;
    }


    @Override
    public Transaction withdrawal(WithdrawalDto withdrawalDto) {
        var withdrawalAmount = BigDecimal.valueOf(withdrawalDto.getAmount());
        AppUser user = verifyLoggedInUser();
        log.info("Withdrawing from user: {} wallet", user.getFirstName());
        Wallet wallet = Optional.ofNullable(accountRepository.findByAccountHolder(user).getWallet())
                .orElseThrow(() -> new UserWithEmailNotFound("Wallet not found"));
        validatePin.accept(withdrawalDto.getTransactionPin(), wallet.getTransactionPin());

        var walletBalance = wallet.getWalletBalance();
        checkWithdrawalAmount.accept(withdrawalAmount, walletBalance);

        TransactionLevel userLevel = user.getTransactionLevel();
        Transaction userTransaction = null;

        switch(userLevel) {
            case BASIC -> {
                if(withdrawalAmount.compareTo(Constants.BASIC_TRANSACTION_LIMIT) < 0) {
                    userTransaction = generateTransaction(withdrawalAmount.doubleValue(),
                            withdrawalDto.getTransactionSummary(), TransactionType.WITHDRAWALS);
                } else {
                    throw new InvalidAmountException("Amount to Withdraw cannot be more than "+Constants.BASIC_TRANSACTION_LIMIT);
                }
            }
            case SILVER -> {
                if(withdrawalAmount.compareTo(Constants.SILVER_TRANSACTION_LIMIT) < 0) {
                    userTransaction = generateTransaction(withdrawalAmount.doubleValue(),
                            withdrawalDto.getTransactionSummary(), TransactionType.WITHDRAWALS);
                } else {
                    throw new InvalidAmountException("Amount to Withdraw cannot be more than "+Constants.SILVER_TRANSACTION_LIMIT);
                }
            }
            case GOLD -> {
                if(withdrawalAmount.compareTo(Constants.GOLD_TRANSACTION_LIMIT) < 0) {
                    userTransaction = generateTransaction(withdrawalAmount.doubleValue(),
                            withdrawalDto.getTransactionSummary(), TransactionType.WITHDRAWALS);
                } else {
                    throw new InvalidAmountException("Amount to Withdraw cannot be more than "+Constants.GOLD_TRANSACTION_LIMIT);
                }
            }
        }
        walletRepository.save(wallet);
        return userTransaction;
    }

//    @Override
//    public TransactionDto transferMoney(TransferDto transferDto) {
//        log.info("Transfer of currency");
//        AppUser user = verifyLoggedInUser();
//
//        Optional<Wallet> sendersWallet = Optional.ofNullable(walletRepository
//                .findByAccountHolderId(user.getId())
//                .orElseThrow(() -> new UserWithEmailNotFound("Wallet was not found")));
//
//        Optional<Wallet> receiversWallet = Optional.ofNullable(walletRepository
//                .findByAccountHolderEmailOrWalletAccountNumber(transferDto.getReceiversEmail(), transferDto.getReceiversAccountNumber())
//                .orElseThrow(() -> new UserWithEmailNotFound("Wallet was not found")));
//
//        Double amountToTransfer = transferDto.getAmount();
//        TransactionDto transactionDto = new TransactionDto();
//        Double amountHaving = sendersWallet.get().getWalletBalance();
//        if(Objects.equals(transferDto.getTransactionPin(), "0000")) {
//            throw new InvalidAmountException("For your security, you need to change your transaction pin before any transaction.");
//        }
//        if(sendersWallet.get().getTransactionPin().equals(transferDto.getTransactionPin())) {
//            if(amountHaving < 999 || amountToTransfer > amountHaving) {
//                throw new InsufficientFundsException("Amount initiated is below your wallet balance");
//            }
//            TransactionLevel userLevel = sender.getTransactionLevel();
//            if (userLevel.equals(TransactionLevel.BASIC))
//            {
//                if(transferDto.getAmount()>=1000 && transferDto.getAmount()<=20000)
//                {
//                    transferFunds(transferDto, sendersWallet, receiversWallet);
//
//                    transactionDto = getTransactionDto(transferDto, transactionDto, sendersWallet);
//                } else
//                {
//                    throw new InvalidAmountException("Amount to Withdraw cannot be less than N1000 and Not more than 20000");
//                }
//            } else if (userLevel.equals(TransactionLevel.SILVER))
//            {
//                if(transferDto.getAmount()>=1000 && transferDto.getAmount()<=700000)
//                {
//                    transferFunds(transferDto, sendersWallet, receiversWallet);
//
//                    transactionDto = getTransactionDto(transferDto, transactionDto, sendersWallet);
//                } else
//                {
//                    throw new InvalidAmountException("Amount to Withdraw cannot be less than N1000 and Not more than 700000 ");
//                }
//            } else if (userLevel.equals(TransactionLevel.GOLD))
//            {
//                if(transferDto.getAmount()>=50 && transferDto.getAmount()<=20000000)
//                {
//                    transferFunds(transferDto, sendersWallet, receiversWallet);
//
//                    transactionDto = getTransactionDto(transferDto, transactionDto, sendersWallet);
//                }
//            } else {
//                throw new InvalidAmountException("Amount to Withdraw cannot be less than N1000 and Not more than 20000000");
//            }
//        } else
//        {
//            throw new WrongTransactionPin("You have entered a wrong transaction pin! ");
//
//        }
//        return transactionDto;
//    }
//
//    private TransactionDto getTransactionDto(TransferDto transferDto, TransactionDto transactionDto, Optional<Wallet> sendersWallet)
//    {
//        String date = setDateAndTimeForTransaction();
//        StringBuilder receiverId = new StringBuilder();
//        if(transferDto.getReceiversAccountNumber()!=null)
//        {
//            receiverId.append(transferDto.getReceiversAccountNumber());
//        } else
//        {
//            receiverId.append(transferDto.getReceiversEmail());
//        }
//        transactionDto = TransactionDto.builder()
//                .senderOrTransfer(sendersWallet.get().getAccountHolderEmail())
//                .receiver(receiverId.toString())
//                .transactionType(TransactionType.TRANSFER)
//                .transactionStatus(TransactionStatus.SUCCESSFUL)
//                .dateAndTimeForTransaction(date)
//                .summary(transferDto.getTransactionSummary())
//                .build();
//        return transactionDto;
//    }
//
//    private void transferFunds(TransferDto transferDto) {
//        var user = verifyLoggedInUser();
//
//        var amountToTransfer = BigDecimal.valueOf(transferDto.getAmount());
//        var senderWallet = accountRepository.findByAccountHolder(user).getWallet();
//        Optional<Wallet> receiverWallet
//                = walletRepository.findByAccountHolderEmailOrWalletAccountNumber(transferDto.getReceiversEmail(), transferDto.getReceiversAccountNumber());
//        var senderBalance = senderWallet.getWalletBalance();
//        if (receiverWallet.isPresent()) {
//            var newSenderBalance = senderBalance.subtract(amountToTransfer);
//            var newReceiversBalance = receiverWallet.get().getWalletBalance().add(amountToTransfer);
//
//            senderWallet.setWalletBalance(newSenderBalance);
//            receiverWallet.get().setWalletBalance(newReceiversBalance);
//
//            walletRepository.save(senderWallet);
//            walletRepository.save(receiverWallet.get());
//        }
//    }
//
//    @Override
//    public String changeTransactionPin(ChangeTransactionPinDto changeTransactionPinDto) {
//        log.info("Changing user wallet transaction pin");
//        Wallet walletToChangeTransactionPin = (walletRepository.findByAccountHolderId(changeTransactionPinDto.getAccountHolderId())
//                .orElseThrow(() -> new UserWithEmailNotFound("Wallet was not found")));
//        AppUser user = userRepository.findById(changeTransactionPinDto.getAccountHolderId())
//                .orElseThrow(() ->new UserWithEmailNotFound("Use was not found"));
//
//        walletToChangeTransactionPin.setTransactionPin(changeTransactionPinDto.getNewPin());
//        walletRepository.save(walletToChangeTransactionPin);
//
//        return "Pin changed Successfully";
//    }
//
//    @Override
//    public String doKycDocumentation(KycDto kycDto) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        String email = userDetails.getUsername();
//        AppUser user = userRepository.findByEmail(email).orElseThrow(() ->
//                new UserWithEmailNotFound("User with email not found"));
//
//            Wallet userWallet = walletRepository.findByAccountHolderId(user.getId())
//                    .orElseThrow(() -> new UserWithEmailNotFound("Wallet user was not found"));
//
//            KYC userKyc = kycRepository.findByAccountHolderId(user.getId());
//
//            if (userKyc.getBvn() == null) {
//                userKyc.setBvn(kycDto.getBVN());
//            }
//            if (userKyc.getValidId() == null) {
//                userKyc.setValidId(kycDto.getDriverLicence());
//            }
//            if (userKyc.getPassportUrl() == null) {
//                userKyc.setPassportUrl(kycDto.getPassportUrl());
//            }
//            kycRepository.save(userKyc);
//            return "KYC completed, wait for verification, validation and approval";
//
//    }
//
//    @Override
//    public String kycApprovalByAdmin(KycAdminVerificationDto kycAdminVerificationDto) {
//        AppUser userToUpgradeAndVerify = userRepository.findById(kycAdminVerificationDto.getAccountHolderId()).orElseThrow(() ->
//                new UserWithEmailNotFound("User with email not found"));
//        KYC userKyc = kycRepository.findByAccountHolderId(userToUpgradeAndVerify.getId());
//        int levelApproved = kycAdminVerificationDto.getLevelApproved();
//        StringBuilder levelUpdated = new StringBuilder();
//        if(levelApproved==2) {
//            userToUpgradeAndVerify.setTransactionLevel(TransactionLevel.SILVER);
//            userKyc.setApprovedLevel(TransactionLevel.SILVER.name());
//            userRepository.save(userToUpgradeAndVerify);
//            kycRepository.save(userKyc);
//            levelUpdated.append(userToUpgradeAndVerify.getTransactionLevel().name());
//        }
//        else if(levelApproved==3) {
//            userToUpgradeAndVerify.setTransactionLevel(TransactionLevel.GOLD);
//            userKyc.setApprovedLevel(TransactionLevel.GOLD.name());
//            userRepository.save(userToUpgradeAndVerify);
//            kycRepository.save(userKyc);
//            levelUpdated.append(userToUpgradeAndVerify.getTransactionLevel().name());
//        }
//        return levelUpdated.toString();
//    }

    private String setDateAndTimeForTransaction() {
        String pattern = "dd MMMMM yyyy HH:mm:ss.zzz";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        return simpleDateFormat.format(new Date());
    }

    private String generateRandomAccountNumber() {
        StringBuilder accNum = new StringBuilder();
        Random rand = new Random();
        for (int i = 1; i <= 5; i++) {
            int resRandom = rand.nextInt((99 - 10) + 1) + 10;
            accNum.append(resRandom);
        }
        return accNum.toString();
    }

    @Override
    public String saveRole(RoleDto roleDto) {
        log.info("Saving new user role, {}", roleDto.getName());
        Optional<Role> role = roleRepository.findByName(roleDto.getName().toUpperCase());
        if(role.isEmpty()) {
            RoleDto roleDtoCapitalized = new RoleDto();
            roleDtoCapitalized.setName(roleDto.getName().toUpperCase());
            Role roleToSave = mapper.map(roleDtoCapitalized, Role.class);
            roleRepository.save(roleToSave);
        } else {
            throw new UserWithEmailAlreadyExist("Role already exist already exist");
        }
        return "Role saved successfully";
    }

    @Override
    @CachePut(cacheNames = "add-role")
    public String addROleTOUser(AddRoleToUserDto addRoleToUserDto) {
        log.info("Adding new role to a user");
        AppUser user = userRepository.findByEmail(addRoleToUserDto.getUserEmail())
                .orElseThrow(() ->new UserWithEmailNotFound("User with email "+ addRoleToUserDto.getUserEmail()+ "was not found"));
        addRoleToUserDto.getRoleName().toUpperCase();
        Role role = roleRepository.findByName(addRoleToUserDto.getRoleName())
                .orElseThrow(() ->new RoleNotFoundException("Role was not found"));
        if(user.getRoles().contains(role)) {
            throw new RoleNotFoundException("User already has this role");
        }  else {
            Set<Role> roles;
            if(user.getRoles() == null || user.getRoles().isEmpty()) {
                roles = new HashSet<>();
            } else {
                roles = user.getRoles();
            }
            roles.add(role);
            user.setRoles(roles);
        }
        return "Role added successfully";
    }

    @Override
    @Cacheable(cacheNames = "user")
    public AccountUserDto getUser(String userEmail) {
        log.info("Fetching a particular user by email, {}", userEmail);
        AppUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->new UserWithEmailNotFound("User with email "+userEmail+ "was not found"));
        return mapper.map(user, AccountUserDto.class);
    }

    @Override
    @Cacheable(cacheNames = "users")
    public List<AppUser> getAllUsers() {
        log.info("Getting all registered account users");
        return userRepository.findAll();
    }

}
