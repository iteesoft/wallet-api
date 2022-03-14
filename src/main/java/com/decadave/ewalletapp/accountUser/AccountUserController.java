package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.shared.dto.ChangeTransactionPinDto;
import com.decadave.ewalletapp.shared.dto.TopUpDto;
import com.decadave.ewalletapp.shared.dto.TransferDto;
import com.decadave.ewalletapp.shared.dto.WithdrawalDto;
import com.decadave.ewalletapp.shared.responses.HttpResponse;
import com.decadave.ewalletapp.transaction.Transaction;
import com.decadave.ewalletapp.transaction.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountUserController
{
    @Autowired
    private  final UserService userService;

    public AccountUserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountUser>> getAllUsers ()
    {
        return new ResponseEntity<>(userService.AccountUsers(), HttpStatus.OK);
    }

    @PostMapping("/create-account")
    public ResponseEntity<HttpResponse> saveUsers (@RequestBody AccountUserDto accountUserDto)
    {
        return response(HttpStatus.OK, userService.createAccountUser(accountUserDto));
    }

    @PostMapping("/role/save")
    public ResponseEntity<HttpResponse> saveRole (@RequestBody RoleDto roleDto)
    {
        return response(HttpStatus.OK, userService.saveRole(roleDto));
    }


    @PutMapping("/top-up-wallet-balance")
    public ResponseEntity<TopUpDto> topUpAccount (@RequestBody TopUpDto topUpDto)
    {
        return new ResponseEntity<>(userService.topUpWalletBalance(topUpDto), HttpStatus.OK);
    }

    @PutMapping("/withdrawal-from-wallet")
    public ResponseEntity<Transaction> withdrawal (@RequestBody WithdrawalDto withdrawalDto)
    {
        return new ResponseEntity<>(userService.withdrawal(withdrawalDto), HttpStatus.OK);
    }

    @PutMapping("/transfer-from-wallet")
    public ResponseEntity<TransactionDto> transfer (@RequestBody TransferDto transferDto)
    {
        return new ResponseEntity<>(userService.transferMoney(transferDto), HttpStatus.OK);
    }


    @PutMapping("/change-transaction-pin")
    public  ResponseEntity<HttpResponse> changeTransactionPin (@RequestBody ChangeTransactionPinDto changeTransactionPinDto)
    {
        return response(HttpStatus.CREATED, userService.changeTransactionPin(changeTransactionPinDto));
    }

    public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message)
    {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

}
