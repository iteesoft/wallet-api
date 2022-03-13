package com.decadave.ewalletapp.shared.exceptions;

public class WrongTransactionPin extends RuntimeException {
    public WrongTransactionPin (String message)
    {
        super(message);
    }

}
