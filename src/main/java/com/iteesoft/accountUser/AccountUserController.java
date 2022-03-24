package com.iteesoft.accountUser;

import com.iteesoft.KYC.KycDto;
import com.iteesoft.shared.dto.ChangeTransactionPinDto;
import com.iteesoft.shared.dto.TopUpDto;
import com.iteesoft.shared.dto.TransferDto;
import com.iteesoft.shared.dto.WithdrawalDto;
import com.iteesoft.shared.responses.HttpResponse;
import com.iteesoft.transaction.Transaction;
import com.iteesoft.transaction.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AccountUserController {
    private  final UserService userService;


    @PostMapping("/")
    public ResponseEntity<HttpResponse> register(@RequestBody AccountUserDto accountUserDto) {
        return response(HttpStatus.OK, userService.createAccountUser(accountUserDto));
    }

    @PutMapping("/accounts/topup")
    public ResponseEntity<Transaction> topUpAccount(@RequestBody TopUpDto topUpDto) {
        return new ResponseEntity<>(userService.topUpWalletBalance(topUpDto), HttpStatus.OK);
    }

    @PutMapping("/accounts/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody WithdrawalDto withdrawalDto) {
        return new ResponseEntity<>(userService.withdrawal(withdrawalDto), HttpStatus.OK);
    }

//    @PutMapping("/user/transfer-from-wallet")
//    public ResponseEntity<TransactionDto> transfer(@RequestBody TransferDto transferDto)
//    {
//        return new ResponseEntity<>(userService.transferMoney(transferDto), HttpStatus.OK);
//    }

//
//    @PutMapping("/user/change-transaction-pin")
//    public  ResponseEntity<HttpResponse> changeTransactionPin (@RequestBody ChangeTransactionPinDto changeTransactionPinDto)
//    {
//        return response(HttpStatus.CREATED, userService.changeTransactionPin(changeTransactionPinDto));
//    }
//
//    @PutMapping("/user/kyc-upgrade-level")
//    public ResponseEntity<String> doYourKycAndUpgradeLevel (@RequestBody KycDto kycDto)
//    {
//        return new ResponseEntity<>(userService.doKycDocumentation(kycDto), HttpStatus.OK);
//    }


    public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

}
