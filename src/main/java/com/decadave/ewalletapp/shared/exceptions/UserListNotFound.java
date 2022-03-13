package com.decadave.ewalletapp.shared.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListNotFound extends RuntimeException {
    public UserListNotFound(String message) {
        super(message);
    }

}
