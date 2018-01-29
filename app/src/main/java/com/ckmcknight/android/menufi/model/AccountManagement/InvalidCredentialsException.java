package com.ckmcknight.android.menufi.model.AccountManagement;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Invalid Username or Password");
    }
}
