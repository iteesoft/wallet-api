package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.KYC.KYC;
import com.decadave.ewalletapp.KYC.KycRepository;
import com.decadave.ewalletapp.account.Account;
import com.decadave.ewalletapp.account.AccountRepository;
import com.decadave.ewalletapp.role.Role;
import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.role.RoleRepository;
import com.decadave.ewalletapp.shared.enums.TransactionLevel;
import com.decadave.ewalletapp.shared.exceptions.RoleNotFoundException;
import com.decadave.ewalletapp.shared.exceptions.UserWithEmailAlreadyExist;
import com.decadave.ewalletapp.shared.exceptions.UserWithEmailNotFound;
import com.decadave.ewalletapp.wallet.Wallet;
import com.decadave.ewalletapp.wallet.WalletRepositiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService
{

    private final AccountUserRepository userRepository;
    private final AccountRepository accountRepository;
    private final WalletRepositiry walletRepositiry;
    private final RoleRepository roleRepository;
    private final KycRepository kycRepository;
    private  final ModelMapper mapper;

//    @Autowired
//    public UserServiceImpl(AccountUserRepository userRepository, RoleRepository roleRepository, ModelMapper mapper) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.mapper = mapper;
//    }

    @Override
    public String saveAccountUser(AccountUserDto userDto)
    {
        log.info("Saving new account user, {}", userDto.getEmail());
        Optional<AccountUser> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isEmpty())
        {
            AccountUser userToSave = mapper.map(userDto, AccountUser.class);
            userToSave.setTransactionLevel(TransactionLevel.LEVEL_ONE_ALL);
            userRepository.save(userToSave);
            System.out.println(userToSave.getId());

            Account userAccountCreation = createAccountForUser(userToSave);

            KYC userKyc = createAKycDirectoryForUser(userAccountCreation);

            createWalletForUser(userAccountCreation, userKyc);

        } else
        {
            throw new UserWithEmailAlreadyExist("Email: " + userDto.getEmail() + " already exist");
        }
        return "Account user created successfully";
    }

    private void createWalletForUser(Account userAccountCreation, KYC userKyc) {
        Wallet userWallet = Wallet.builder()
                .walletBalance(0.00)
                .walletAccountNumber(generateRandomAccountNumber())
                .transactionPin("0000")
                .kycId(userKyc.getId())
                .build();

        userAccountCreation.setWallet(userWallet);
        userWallet.setAccountId(userAccountCreation.getId());

        walletRepositiry.save(userWallet);
    }

    private KYC createAKycDirectoryForUser(Account userAccountCreation) {
        KYC userKyc = KYC.builder()
                .accountHolderId(userAccountCreation.getId())
                .build();
        kycRepository.save(userKyc);
        return userKyc;
    }

    private Account createAccountForUser(AccountUser userToSave) {
        Account userAccountCreation = Account.builder()
                .accountHolderId(userToSave.getId())
                .accountName(userToSave.getFirstName() +" "+ userToSave.getLastName())
                .build();
        accountRepository.save(userAccountCreation);
        return userAccountCreation;
    }

    @Override
    public String saveRole(RoleDto roleDto)
    {
        log.info("Saving new user role", roleDto.getName());
        Optional<Role> role = roleRepository.findByName(roleDto.getName().toUpperCase());
        if(role.isEmpty())
        {
            RoleDto roleDtoCapitalized = new RoleDto();
            roleDtoCapitalized.setName(roleDto.getName().toUpperCase());
            Role roleToSave = mapper.map(roleDtoCapitalized, Role.class);
            roleRepository.save(roleToSave);
        } else
        {
            throw new UserWithEmailAlreadyExist("Role already exist already exist");
        }
        return "Role saved successfully";

    }

    @Override
    public void addROleTOUser(AddRoleToUserDto addRoleToUserDto)
    {
        log.info("Adding new role to a user");
        AccountUser user = userRepository.findByEmail(addRoleToUserDto.getUserEmail())
                .orElseThrow(() ->new UserWithEmailNotFound("User with email "+ addRoleToUserDto.getUserEmail()+ "was not found"));
        addRoleToUserDto.getRoleName().toUpperCase();
        Role role = roleRepository.findByName(addRoleToUserDto.getRoleName())
                .orElseThrow(() ->new RoleNotFoundException("Role was not found"));
        if(user.getRoles().contains(role))
        {
            throw new RoleNotFoundException("User already has this role");
        }  else
        {
            user.getRoles().add(role);
        }
    }

    @Override
    public AccountUserDto getUser(String userEmail)
    {
        log.info("Fetching a particular user by email, {}", userEmail);
        AccountUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->new UserWithEmailNotFound("User with email "+userEmail+ "was not found"));
        AccountUserDto userFound = mapper.map(user, AccountUserDto.class);
        return userFound;
    }

    @Override
    public List<AccountUser> AccountUsers()
    {
        log.info("Getting all registered account users");
        return userRepository.findAll();
    }
    private String generateRandomAccountNumber () {
        StringBuilder accNum = new StringBuilder();
        Random rand = new Random();
        for (int i = 1; i <= 5; i++) {
            int resRandom = rand.nextInt((99 - 10) + 1) + 10;
            accNum.append(resRandom);
        }
        return accNum.toString();
    }
}
