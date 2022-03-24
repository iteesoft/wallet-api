package com.iteesoft.accountUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleToUserDto {
    private String userEmail;
    private String roleName;
}
