package com.iteesoft.accountUser;

import com.iteesoft.shared.BaseClass;
import com.iteesoft.shared.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountUserDto extends BaseClass {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String bvn;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
