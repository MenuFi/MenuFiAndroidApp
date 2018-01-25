package com.ckmcknight.android.menufi.model.AccountManagement;

import java.util.HashMap;
import java.util.Map;

class MockAccountValidator implements AccountValidator {
    private static final MockAccountValidator accountValidator = new MockAccountValidator();
    private Map<String, String> accounts = new HashMap<>();

    private MockAccountValidator() {}

    public static AccountValidator getAccountValidator() {
        return accountValidator;
    }

    @Override
    public SessionToken login(String email, String password) throws InvalidCredentialsException {
        if (accounts.containsKey(email) && accounts.get(email).equals(password)) {
            return new SessionToken("validtoken");
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public SessionToken register(String email, String password) {
        accounts.put(email, password);
        return new SessionToken("validtoken");
    }
}
