package com.ckmcknight.android.menufi.model.AccountManagement;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ckmcknight.android.menufi.model.datahandlers.NetworkController;

import org.json.JSONArray;

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
    public void login(Response.Listener<JSONArray> listener, String email, String password) {
        String url = NetworkController.BASE_SERVER_URL + loginExtension;
        JsonArrayRequest loginRequest = new JsonArrayRequest(url, listener, null);
        networkController.addToRequestQueue(loginRequest);
    }

    @Override
    public void register(Response.Listener<JSONArray> listener, String email, String password) {
        String url = NetworkController.BASE_SERVER_URL + registrationExtension;
        JsonArrayRequest loginRequest = new JsonArrayRequest(url, listener, null);
        networkController.addToRequestQueue(loginRequest);
    }
}
