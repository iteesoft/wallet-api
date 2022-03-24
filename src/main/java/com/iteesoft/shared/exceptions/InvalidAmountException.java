package com.iteesoft.shared.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message)
    {
        super(message);
    }
}
