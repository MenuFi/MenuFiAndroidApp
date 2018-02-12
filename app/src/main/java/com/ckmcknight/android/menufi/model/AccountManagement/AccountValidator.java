package com.ckmcknight.android.menufi.model.AccountManagement;

import com.android.volley.Response;

import org.json.JSONArray;

public interface AccountValidator {

    void login(Response.Listener<JSONArray> listener, String email, String password);
    void register(Response.Listener<JSONArray> listener, String email, String password);

}
