package com.ckmcknight.android.menufi.model.AccountManagement;

import android.support.annotation.Nullable;

import com.ckmcknight.android.menufi.model.datahandlers.NetworkController;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AccountManager {
    private static Logger logger = Logger.getLogger("AccountManager");

    private AccountValidator accountValidator;
    private SessionToken sessionToken;

    @Inject
    public AccountManager() {
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
