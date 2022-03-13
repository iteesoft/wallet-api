package com.decadave.ewalletapp.shared.exceptions;

public class UsersWalletWithUserIdNotFound extends RuntimeException {
    public UsersWalletWithUserIdNotFound (String message)
    {
        super(message);
    }
}
