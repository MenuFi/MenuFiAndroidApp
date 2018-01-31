package com.ckmcknight.android.menufi.model.AccountManagement;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RemoteAccountValidator implements AccountValidator {

    private RequestQueue requestQueue;

    public RemoteAccountValidator(Context context) {
        requestQueue = Volley.newRequestQueue(context);

    }

    @Override
    public SessionToken login(String email, String password) throws InvalidCredentialsException {
        return null;
    }

    @Override
    public SessionToken register(String email, String password) {
        return null;
    }
}
