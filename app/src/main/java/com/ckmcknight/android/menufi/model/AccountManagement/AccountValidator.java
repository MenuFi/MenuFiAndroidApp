package com.ckmcknight.android.menufi.model.AccountManagement;

import com.android.volley.Response;

import org.json.JSONObject;

public interface AccountValidator {
    String JSON_EMAIL_KEY = "email";
    String JSON_PASSWORD_KEY = "password";

    void login(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password);
    void register(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password);

}
