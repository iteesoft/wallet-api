package com.iteesoft.shared.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithEmailNotFound extends RuntimeException {
    public UserWithEmailNotFound(String message) {
        super(message);
    }

}
