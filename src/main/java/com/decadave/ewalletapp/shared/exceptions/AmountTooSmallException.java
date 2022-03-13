package com.decadave.ewalletapp.shared.exceptions;

public class AmountTooSmallException extends RuntimeException {
    public AmountTooSmallException(String message)
    {
        super(message);
    }
}
