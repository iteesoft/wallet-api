package com.decadave.ewalletapp.shared.exceptions;

public class WrongValidationCredentials extends RuntimeException {
    public WrongValidationCredentials(String message)
    {
        super(message);
    }
}
