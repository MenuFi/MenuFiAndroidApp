package com.ckmcknight.android.menufi.model.AccountManagement;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ckmcknight.android.menufi.model.datahandlers.NetworkController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

public class RemoteAccountValidator implements AccountValidator {

    private NetworkController networkController;
    private Logger logger = Logger.getLogger("RemoteAccountValidator");
    private static String loginExtension = "/patron/loginToken/";
    private static String registrationExtension = "/patron/registration/";

    @Inject
    public RemoteAccountValidator(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void login(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password) {
        String url = NetworkController.BASE_SERVER_URL + loginExtension;
        JSONObject request = new JSONObject(getRequestMap(email, password));
        JsonObjectRequest loginRequest = new JsonObjectRequest(url, request, listener, errorListener);
        networkController.addToRequestQueue(loginRequest);
    }

    @Override
    public void register(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String email, String password) {
        String url = NetworkController.BASE_SERVER_URL + registrationExtension;
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
