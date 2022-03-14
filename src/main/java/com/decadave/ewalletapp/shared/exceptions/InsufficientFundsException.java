package com.decadave.ewalletapp.shared.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InsufficientFundsException extends  RuntimeException {
    public InsufficientFundsException (String message)
    {
        super(message);
    }

}
