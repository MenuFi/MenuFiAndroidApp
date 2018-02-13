package com.ckmcknight.android.menufi.model.accountvalidation;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Invalid Username or Password");
    }
}
