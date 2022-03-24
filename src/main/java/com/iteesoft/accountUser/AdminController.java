package com.iteesoft.accountUser;

import com.iteesoft.KYC.KycAdminVerificationDto;
import com.iteesoft.role.RoleDto;
import com.iteesoft.shared.responses.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private  final UserService userService;

    public AdminController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/view-all")
    public ResponseEntity<List<AppUser>> getAllUsers ()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/save-new-role")
    public ResponseEntity<HttpResponse> saveRole (@RequestBody RoleDto roleDto)
    {
        return response(HttpStatus.OK, userService.saveRole(roleDto));
    }

    @PostMapping("/add-role-to-users")
    public ResponseEntity<HttpResponse> saveRoleToUser (@RequestBody AddRoleToUserDto addRoleToUserDto)
    {
        return response(HttpStatus.OK, userService.addROleTOUser(addRoleToUserDto));
    }

//    @PostMapping("/admin-validates-upgrades")
//    public ResponseEntity<HttpResponse> upgradeUsersLevel (@RequestBody KycAdminVerificationDto kycAdminVerificationDto)
//    {
//        return response(HttpStatus.OK, userService.kycApprovalByAdmin(kycAdminVerificationDto));
//    }


    public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message)
    {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus );
    }

}
