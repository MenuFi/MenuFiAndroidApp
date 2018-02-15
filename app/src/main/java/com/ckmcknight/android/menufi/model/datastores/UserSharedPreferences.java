package com.ckmcknight.android.menufi.model.datastores;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;

import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.containers.SessionToken;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserSharedPreferences {
    private static final String SHARED_PREFERENCE_KEY = "com.ckmcknight.android.menufi";
    private static final String SESSION_TOKEN_KEY = "SESSION_TOKEN_KEY";
    private static final String READ_ERROR_DEFAULT = "READ_ERROR_DEFAULT";
    private static final String DIETARY_PREFERENCE_KEY = "DIETARY_PREFERENCE_KEY";

    private SessionToken sessionToken = new SessionToken("");
    private SharedPreferences sharedPreferences;
    private DietaryPreferenceStore dietaryPreferenceStore;
    private List<Integer> dietaryPreferenceIds;
    private boolean loggedIn;

    @Inject
    UserSharedPreferences(Context context, DietaryPreferenceStore dietaryPreferenceStore) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        this.dietaryPreferenceStore = dietaryPreferenceStore;
        loggedIn = false;
    }

    @WorkerThread
    public synchronized void establishCurrentSession(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
        loggedIn = true;
        writeState();
    }

    @WorkerThread
    private void writeState() {
        String dietaryPreferenceString = Joiner.on(",").join(dietaryPreferenceIds);
        sharedPreferences.edit()
                .putString(SESSION_TOKEN_KEY, sessionToken.getTokenValue())
                .putString(DIETARY_PREFERENCE_KEY, dietaryPreferenceString)
                .apply();
    }

    @WorkerThread
    public synchronized void restablishCurrentSession() {
        sessionToken = new SessionToken(sharedPreferences.getString(SESSION_TOKEN_KEY,READ_ERROR_DEFAULT));
        String dietaryPreferenceKey = sharedPreferences.getString(DIETARY_PREFERENCE_KEY, "");
        dietaryPreferenceIds = new ArrayList<>();
        for (String s : dietaryPreferenceKey.split(",")) {
            if (!"".equals(s)) {
                dietaryPreferenceIds.add(Integer.parseInt(s));
            }
        }

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

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public synchronized void setUserDietaryPreferences(List<DietaryPreference> dietaryPreferences) {
        List<Integer> newDietaryPreferences = new ArrayList<>();
        for (DietaryPreference preference : dietaryPreferences) {
            newDietaryPreferences.add(preference.getId());
        }
        dietaryPreferenceIds = newDietaryPreferences;
        writeState();
    }

    public synchronized List<DietaryPreference> getUserDietaryPreferences() {
        List<DietaryPreference> dietaryPreferences = new ArrayList<>();
        for (int i : dietaryPreferenceIds) {
            DietaryPreference preference = dietaryPreferenceStore.getDietaryPreference(i);
            if (preference != null) {
                dietaryPreferences.add(preference);
            } else {
                Logger.getLogger("UserSharedPreferences");
            }
        }
        return dietaryPreferences;
    }

}
