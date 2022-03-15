package com.decadave.ewalletapp.shared.responses;

import com.decadave.ewalletapp.accountUser.AccountUserDto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final AccountUserDto userDto;
    private final String jwttoken;

    public JwtResponse(AccountUserDto userDto, String jwttoken) {
        this.userDto = userDto;
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public AccountUserDto getUserDto() {
        return userDto;
    }
}

