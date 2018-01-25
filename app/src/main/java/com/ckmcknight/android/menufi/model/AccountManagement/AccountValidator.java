package com.ckmcknight.android.menufi.model.AccountManagement;

interface AccountValidator {

    SessionToken login(String email, String password) throws InvalidCredentialsException;
    SessionToken register(String email, String password);

}
