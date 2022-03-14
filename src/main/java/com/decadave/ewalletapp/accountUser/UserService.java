package com.decadave.ewalletapp.accountUser;


import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.shared.dto.TopUpDto;
import com.decadave.ewalletapp.shared.dto.ChangeTransactionPinDto;
import com.decadave.ewalletapp.shared.dto.WithdrawalOrTransferDto;
import com.decadave.ewalletapp.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService
{
    String createAccountUser(AccountUserDto userDto);
    String saveRole (RoleDto roleDto);
    void addROleTOUser(AddRoleToUserDto addRoleToUserDto);
    AccountUserDto getUser(String userId);
    List<AccountUser> AccountUsers();
    TopUpDto topUpWalletBalance(TopUpDto topUpDto);
    Transaction withdrawal(WithdrawalOrTransferDto withdrawalOrTransferDto);
    String changeTransactionPin (ChangeTransactionPinDto changeTransactionPinDto);
}
