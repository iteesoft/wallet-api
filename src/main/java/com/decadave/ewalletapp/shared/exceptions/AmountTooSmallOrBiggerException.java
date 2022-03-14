package com.decadave.ewalletapp.shared.exceptions;

public class AmountTooSmallOrBiggerException extends RuntimeException {
    public AmountTooSmallOrBiggerException(String message)
    {
        super(message);
    }
}
