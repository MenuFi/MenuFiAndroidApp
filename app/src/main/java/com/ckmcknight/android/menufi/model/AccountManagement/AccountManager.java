package com.ckmcknight.android.menufi.model.AccountManagement;

import android.support.annotation.Nullable;

import java.util.logging.Logger;

public class AccountManager {
    private static AccountManager accountManager = new AccountManager();
    private static Logger logger = Logger.getLogger("AccountManager");

    private AccountValidator accountValidator;
    private SessionToken sessionToken;

    private static AccountManager getAccountManager() {
        return accountManager;
    }

    private AccountManager() {
        accountValidator = MockAccountValidator.getAccountValidator();
    }

    public boolean login(String email, String password) {
        try {
            logger.info("Attempting to log in with email: " + email);
            sessionToken = accountValidator.login(email, password);
            logger.info("Login successful");
            return true;
        } catch (InvalidCredentialsException e) {
            logger.warning("Login Failed");
            logger.warning(e.getMessage());
            return false;
        }
    }

    public boolean register(String email, String password) {
        try {
            logger.info("Attempting to register new user: " + email);
            sessionToken = accountValidator.register(email, password);
            logger.info("Registration successful");
            return true;
        } catch (Exception e) {
            logger.warning("Registration Failed");
            logger.warning(e.getMessage());
            return false;
        }
    }

    public void logout() {
        sessionToken = null;
    }

    @Nullable
    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
