package com.decadave.ewalletapp.accountUser;


import com.decadave.ewalletapp.role.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    String saveAccountUser(AccountUserDto userDto);
    String saveRole (RoleDto roleDto);
    void addROleTOUser(AddRoleToUserDto addRoleToUserDto);
    AccountUserDto getUser(String userId);
    List<AccountUser> AccountUsers();
}
