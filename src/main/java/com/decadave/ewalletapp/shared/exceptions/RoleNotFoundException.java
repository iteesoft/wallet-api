package com.decadave.ewalletapp.shared.exceptions;

public class RoleNotFoundException extends RuntimeException
{
    public RoleNotFoundException (String message)
    {
        super(message);
    }
}
