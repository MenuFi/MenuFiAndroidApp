package com.ckmcknight.android.menufi.model.accountvalidation;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.WorkerThread;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.dagger.components.MenuFiComponent;
import com.ckmcknight.android.menufi.model.containers.SessionToken;
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class AccountService extends IntentService {
    private static Logger logger = Logger.getLogger("AccountService");

    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    public static final String REGISTER_ACTION = "REGISTER_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";

    public static final String EMAIL_EXTRA = "EMAIL_EXTRA";
    public static final String PASSWORD_EXTRA = "PASSWORD_EXTRA";

    public static final String BROADCAST_LOG_IN = "BROADCAST_LOG_IN";
    public static final String BROADCAST_REGISTERED = "BROADCAST_REGISTERED";
    public static final String BROADCAST_STATUS = "BROADCAST_STATUS";

    public static final String JSON_STATUS_KEY = "status";
    public static final String JSON_STATUS_SUCCESS = "success";
    public static final String JSON_DATA_KEY = "data";
    public static final String JSON_REGISTER_DATA_SUCCESS = "true";

    private AccountValidator accountValidator;
    private UserSharedPreferences userSharedPreferences;

    public AccountService() {
        super("AccountService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MenuFiComponent component = ((MenuFiApplication) getApplication()).getMenuFiComponent();
        userSharedPreferences = component.getUserSharedPreferences();
        accountValidator = component.accountValidator();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch(intent.getAction()) {
            case LOGIN_ACTION:
                logger.info("Received an Login Intent");
                login(intent);
                break;
            case REGISTER_ACTION:
                logger.info("Received an Register Intent");
                register(intent);
                break;
            case LOGOUT_ACTION:
                logger.info("Received a Log out Intent");
                logout(intent);
                break;
        }
    }

    @WorkerThread
    private void login(Intent intent) {
        String email = intent.getStringExtra(EMAIL_EXTRA);
        String password = intent.getStringExtra(PASSWORD_EXTRA);
        logger.info("Attempting to log in with email: " + email);
        accountValidator.login(loginListener, loginErrorListener, email, password);
    }

    @WorkerThread
    private void register(Intent intent) {
        String email = intent.getStringExtra(EMAIL_EXTRA);
        String password = intent.getStringExtra(PASSWORD_EXTRA);
        logger.info("Attempting to register new user: " + email);
        accountValidator.register(registerListener, registerErrorListener, email, password);
    }

    @WorkerThread
    private void logout(Intent intent) {
        userSharedPreferences.logout();
    }

    private Response.Listener<JSONObject> registerListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            logger.info("Got a registration response: " + response.toString());
            try {
                String status = response.getString(JSON_STATUS_KEY);
                if (JSON_STATUS_SUCCESS.equals(status) && JSON_REGISTER_DATA_SUCCESS.equals(response.getString(JSON_DATA_KEY))) {
                    broadcast(BROADCAST_REGISTERED, true);
                } else {
                    broadcast(BROADCAST_REGISTERED, false);
                }
            } catch (JSONException e) {
                logger.severe("Error while parsing JSON in AccountService");
                logger.severe(e.getMessage());
            }
        }
    };

    private Response.ErrorListener loginErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            logger.info("Received an error while loggin in user");
            broadcast(BROADCAST_LOG_IN, false);
        }
    };

    private Response.ErrorListener registerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            logger.info("Received an error while registering user");
            broadcast(BROADCAST_REGISTERED, false);
        }
    };

    private Response.Listener<JSONObject> loginListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            logger.info("Got a LOGIN response: " + response.toString());
            try {
                String status = response.getString(JSON_STATUS_KEY);
                if (JSON_STATUS_SUCCESS.equals(status)) {
                    String sessionToken = response.getString(JSON_DATA_KEY);
                    userSharedPreferences.restablishCurrentSession();
                    userSharedPreferences.establishCurrentSession(new SessionToken(sessionToken));
                    broadcast(BROADCAST_LOG_IN, true);
                } else {
                    broadcast(BROADCAST_LOG_IN, false);
                }
            } catch (JSONException e) {
                logger.severe("Error while parsing JSON in AccountService");
                logger.severe(e.getMessage());
            }
        }
    };

    private void broadcast(String broadcast, boolean status) {
        Intent intent = new Intent();
        intent.setAction(broadcast);
        intent.putExtra(BROADCAST_STATUS, status);
        sendBroadcast(intent);
    }

}
