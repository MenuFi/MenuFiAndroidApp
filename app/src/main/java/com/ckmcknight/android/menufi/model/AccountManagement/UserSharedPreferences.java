package com.ckmcknight.android.menufi.model.AccountManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserSharedPreferences {
    private static final String SHARED_PREFERENCE_KEY = "com.ckmcknight.android.menufi";
    private static final String EMAIL_KEY = "EMAIL_KEY";
    private static final String SESSION_TOKEN_KEY = "SESSION_TOKEN_KEY";
    private static final String READ_ERROR_DEFAULT = "READ_ERROR_DEFAULT";

    private String email = "";
    private SessionToken sessionToken = new SessionToken("");
    private SharedPreferences sharedPreferences;
    private boolean loggedIn;

    @Inject
    UserSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        loggedIn = false;
    }

    @WorkerThread
    public synchronized void establishCurrentSession(String email, SessionToken sessionToken) {
        this.email = email;
        this.sessionToken = sessionToken;
        loggedIn = true;
        writeState();
    }

    @WorkerThread
    private void writeState() {
        sharedPreferences.edit()
                .putString(EMAIL_KEY, email)
                .putString(SESSION_TOKEN_KEY, sessionToken.getTokenValue())
                .apply();
    }

    @WorkerThread
    public synchronized void restablishCurrentSession() {
        email = sharedPreferences.getString(EMAIL_KEY, READ_ERROR_DEFAULT);
        sessionToken = new SessionToken(sharedPreferences.getString(SESSION_TOKEN_KEY,READ_ERROR_DEFAULT));
        loggedIn = true;
    }

    @WorkerThread
    public synchronized boolean logout() {
        sessionToken = new SessionToken("");
        writeState();
        return true;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public String getEmail() {
        return email;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

}
