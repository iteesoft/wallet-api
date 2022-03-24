package com.iteesoft.accountUser;


import com.iteesoft.KYC.KycAdminVerificationDto;
import com.iteesoft.KYC.KycDto;
import com.iteesoft.role.RoleDto;
import com.iteesoft.shared.dto.TransferDto;
import com.iteesoft.shared.dto.TopUpDto;
import com.iteesoft.shared.dto.ChangeTransactionPinDto;
import com.iteesoft.shared.dto.WithdrawalDto;
import com.iteesoft.transaction.Transaction;
import com.iteesoft.transaction.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService
{
    String createAccountUser(AccountUserDto userDto);
    String saveRole (RoleDto roleDto);
    String addROleTOUser(AddRoleToUserDto addRoleToUserDto);
    AccountUserDto getUser(String userId);
    List<AppUser> getAllUsers();
    Transaction topUpWalletBalance(TopUpDto topUpDto);
    Transaction withdrawal(WithdrawalDto withdrawalDto);
//    TransactionDto transferMoney (TransferDto transferDto);
//    String changeTransactionPin (ChangeTransactionPinDto changeTransactionPinDto);
//    String doKycDocumentation (KycDto kycDto);
//    String kycApprovalByAdmin (KycAdminVerificationDto kycAdminVerificationDto);
}
