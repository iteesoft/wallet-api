package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.role.RoleDto;
import com.decadave.ewalletapp.shared.dto.TopUpDto;
import com.decadave.ewalletapp.shared.responses.HttpResponse;
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
    private  UserService userService;

    public AccountUserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountUser>> getAllUsers ()
    {
        return new ResponseEntity<>(userService.AccountUsers(), HttpStatus.OK);
    }

//    @GetMapping("/test")
//    public ResponseEntity<HttpResponse> test ()
//    {
//        return response(HttpStatus.OK,"Yes working");
//    }

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> saveUsers (@RequestBody AccountUserDto accountUserDto)
    {
        return response(HttpStatus.OK, userService.createAccountUser(accountUserDto));
    }

    @PostMapping("/role/save")
    public ResponseEntity<HttpResponse> saveRole (@RequestBody RoleDto roleDto)
    {
        return response(HttpStatus.OK, userService.saveRole(roleDto));
    }


    @PutMapping("/topUp")
    public ResponseEntity<TransactionDto> topUpAccount (@RequestBody TopUpDto topUpDto)
    {
        return new ResponseEntity<>(userService.topUpWallet(topUpDto), HttpStatus.OK);
    }

    public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message)
    {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

}
