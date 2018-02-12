package com.ckmcknight.android.menufi.model.AccountManagement;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.WorkerThread;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.dagger.components.MenuFiComponent;

import org.json.JSONArray;

import java.util.logging.Logger;

import javax.inject.Inject;


public class AccountService extends IntentService {
    private static Logger logger = Logger.getLogger("AccountService");
    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    public static final String REGISTER_ACTION = "REGISTER_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";
    public static final String EMAIL_EXTRA = "EMAIL_EXTRA";
    public static final String PASSWORD_EXTRA = "PASSWORD_EXTRA";
    public static final String BROADCAST_LOGGED_IN_EXTRA = "BROADCAST_LOGGED_IN_EXTRA";
    public static final String BROADCAST_REGISTERED_EXTRA = "BROADCAST_REGISTERED_EXTRA";
    public static final String MISSING_EXTRA_DEFAULT = "MISSING_EXTRA_DEFAULT";

    private AccountValidator accountValidator;
    private UserSharedPreferences userSharedPreferences;

    public AccountService() {
        super("AccountService");
        MenuFiComponent component = ((MenuFiApplication) getApplication()).getMenuFiComponent();
        userSharedPreferences = component.getUserSharedPreferences();
        accountValidator = component.accountValidator();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch(intent.getAction()) {
            case LOGIN_ACTION:
                login(intent);
                break;
            case REGISTER_ACTION:
                register(intent);
                break;
            case LOGOUT_ACTION:
                logout(intent);
                break;
        }
    }

    @WorkerThread
    private void login(Intent intent) {
        String email = intent.getStringExtra(EMAIL_EXTRA);
        String password = intent.getStringExtra(PASSWORD_EXTRA);
        logger.info("Attempting to log in with email: " + email);
        accountValidator.login(loginListener, email, password);
    }

    @WorkerThread
    private void register(Intent intent) {
        String email = intent.getStringExtra(EMAIL_EXTRA);
        String password = intent.getStringExtra(PASSWORD_EXTRA);
        logger.info("Attempting to register new user: " + email);
        accountValidator.register(registerListener, email, password);
    }

    private Response.Listener<JSONArray> registerListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            logger.severe("Got a REGISTER response");
        }
    };

    private Response.Listener<JSONArray> loginListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            logger.severe("Got a LOGIN response");
        }
    };

    @WorkerThread
    private boolean logout(Intent intent) {
        return userSharedPreferences.logout();
    }

}
