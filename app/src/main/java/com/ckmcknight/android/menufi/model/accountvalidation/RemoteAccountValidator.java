package com.ckmcknight.android.menufi.model.accountvalidation;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ckmcknight.android.menufi.model.datahandlers.NetworkController;
import com.ckmcknight.android.menufi.model.datahandlers.RemoteUrls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

public class RemoteAccountValidator implements AccountValidator {

    private NetworkController networkController;
    private Logger logger = Logger.getLogger("RemoteAccountValidator");

    @Inject
    public RemoteAccountValidator(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void login(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password) {
        String url = RemoteUrls.BASE_SERVER_URL + RemoteUrls.LOGIN_EXT;
        JSONObject request = new JSONObject(getRequestMap(email, password));
        JsonObjectRequest loginRequest = new JsonObjectRequest(url, request, listener, errorListener);
        networkController.addToRequestQueue(loginRequest);
    }

    @Override
    public void register(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password) {
        String url = RemoteUrls.BASE_SERVER_URL + RemoteUrls.REGISTRATION_EXT;
        JSONObject request = new JSONObject(getRequestMap(email, password));
        JsonObjectRequest loginRequest = new JsonObjectRequest(url, request, listener, errorListener);
        networkController.addToRequestQueue(loginRequest);
    }

    private Map<String, String> getRequestMap(String email, String password) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put(JSON_EMAIL_KEY, email);
        requestMap.put(JSON_PASSWORD_KEY, password);
        return requestMap;
    }

}
