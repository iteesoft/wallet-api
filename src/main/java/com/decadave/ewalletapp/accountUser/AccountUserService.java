package com.decadave.ewalletapp.accountUser;

import com.decadave.ewalletapp.role.RoleDto;

import java.util.List;

public interface AccountUserService {

    String saveAccountUser (AccountUserDto userDto);
    RoleDto saveRole (RoleDto roleDto);
    void addRoleToUser (String userEmail, String roleName);
    AccountUserDto getUser (String userEmail);
    //A list will be paged latter
    List<AccountUser> getAccountUsers ();
}
