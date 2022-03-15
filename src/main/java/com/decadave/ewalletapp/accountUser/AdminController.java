package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.KYC.KycAdminVerificationDto;
import com.decadave.ewalletapp.KYC.KycDto;
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
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    private  final UserService userService;

    public AdminController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/view-all")
    public ResponseEntity<List<AccountUser>> getAllUsers ()
    {
        return new ResponseEntity<>(userService.AccountUsers(), HttpStatus.OK);
    }

    @PostMapping("/save-new-role")
    public ResponseEntity<HttpResponse> saveRole (@RequestBody RoleDto roleDto)
    {
        return response(HttpStatus.OK, userService.saveRole(roleDto));
    }

    @PostMapping("/admin-validates-upgrades")
    public ResponseEntity<HttpResponse> upgradeUsersLevel (@RequestBody KycAdminVerificationDto kycAdminVerificationDto)
    {
        return response(HttpStatus.OK, userService.kycApprovalByAdmin(kycAdminVerificationDto));
    }


    public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message)
    {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

}
