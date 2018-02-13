package com.ckmcknight.android.menufi.model.AccountManagement;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;

@Singleton
public class MockAccountValidator implements AccountValidator {
    private Map<String, String> accounts = new HashMap<>();
    private static final String JSON_VALID_LOGIN_RESPONSE_FORMAT = "";
    private static final String JSON_INVALID_LOGIN_RESPONSE_FORMAT = "";
    private static final String JSON_REGISTER_RESPONSE_FORMAT = "";


    private MockAccountValidator() {
        accounts.put("a", "a");
    }

    public static AccountValidator getAccountValidator() {
        return accountValidator;
    }

    @Override
    public void login(Response.Listener<JSONArray> listener, String email, String password) {
        if (accounts.containsKey(email) && accounts.get(email).equals(password)) {
            try {
                listener.onResponse(new JSONArray(JSON_VALID_LOGIN_RESPONSE_FORMAT));
            } catch (JSONException e) {
                Logger.getAnonymousLogger("exception while trying to trigger fake login response in mock Account validator");
            }
        } else {
            try {
                listener.onResponse(new JSONArray(JSON_INVALID_LOGIN_RESPONSE_FORMAT));
            } catch (JSONException e) {
                Logger.getAnonymousLogger("exception while trying to trigger fake login response in mock Account validator");
            }
        }
    }

    @Override
    public void register(Response.Listener<JSONArray> listener, String email, String password) {
        accounts.put(email, password);
        try {
            listener.onResponse(new JSONArray(JSON_REGISTER_RESPONSE_FORMAT));
        } catch (JSONException e) {
            Logger.getAnonymousLogger("exception while trying to trigger fake register response in mock Account validator");
        }
    }
}
