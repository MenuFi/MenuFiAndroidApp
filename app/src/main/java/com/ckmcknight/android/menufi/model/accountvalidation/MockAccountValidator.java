package com.ckmcknight.android.menufi.model.accountvalidation;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;

@Singleton
public class MockAccountValidator implements AccountValidator {
    private Map<String, String> accounts = new HashMap<>();
    private static final String JSON_VALID_LOGIN_RESPONSE_FORMAT = "{\"status\":\"success\",\"data\":\"Some Expiring Token!\",\"message\":null}";
    private static final String JSON_REGISTER_RESPONSE_FORMAT = "{\"status\":\"success\",\"data\":true,\"message\":null}";


    public MockAccountValidator() {
        accounts.put("a", "a");
    }

    @Override
    public void login(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password) {
        if (accounts.containsKey(email) && accounts.get(email).equals(password)) {
            try {
                listener.onResponse(new JSONObject(JSON_VALID_LOGIN_RESPONSE_FORMAT));
            } catch (JSONException e) {
                Logger.getAnonymousLogger().severe("exception while trying to trigger fake login response in mock Account validator");
            }
        } else {
            errorListener.onErrorResponse(new VolleyError("Mock Error"));
        }
    }

    @Override
    public void register(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password) {
        if (!accounts.containsKey(email)) {
            accounts.put(email, password);
            try {
                listener.onResponse(new JSONObject(JSON_REGISTER_RESPONSE_FORMAT));
            } catch (JSONException e) {
                Logger.getAnonymousLogger().severe("exception while trying to trigger fake register response in mock Account validator");
                Logger.getAnonymousLogger().severe(e.getMessage());
            }
        } else {
            errorListener.onErrorResponse(new VolleyError("Mock Error"));
        }
    }
}
