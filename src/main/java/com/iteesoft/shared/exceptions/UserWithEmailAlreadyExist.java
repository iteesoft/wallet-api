package com.iteesoft.shared.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserWithEmailAlreadyExist extends RuntimeException {
    public UserWithEmailAlreadyExist (String message) {
        super(message);
    }

}
