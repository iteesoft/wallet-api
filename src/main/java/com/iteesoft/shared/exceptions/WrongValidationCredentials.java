package com.iteesoft.shared.exceptions;

public class WrongValidationCredentials extends RuntimeException {
    public WrongValidationCredentials(String message)
    {
        super(message);
    }
}
