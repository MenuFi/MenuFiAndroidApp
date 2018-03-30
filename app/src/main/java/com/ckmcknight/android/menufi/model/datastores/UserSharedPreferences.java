package com.ckmcknight.android.menufi.model.datastores;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;

import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.containers.SessionToken;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<DietaryPreference.Type, List<DietaryPreference>> dietaryPreferenceMap;
    private boolean loggedIn;

    @Inject
    UserSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
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
        List<String> dietaryPreferenceStringList = new ArrayList<>();
        for (List<DietaryPreference> preferencesOfType: dietaryPreferenceMap.values()) {
            List<Integer> ids = new ArrayList<>();
            for (DietaryPreference preference: preferencesOfType) {
                ids.add(preference.getId());
            }
            dietaryPreferenceStringList.add(Joiner.on(",").join(ids));
        }
        String dietaryPreferenceString = Joiner.on(",").join(dietaryPreferenceStringList);
        sharedPreferences.edit()
                .putString(SESSION_TOKEN_KEY, sessionToken.getTokenValue())
                .putString(DIETARY_PREFERENCE_KEY, dietaryPreferenceString)
                .apply();
    }

    @WorkerThread
    public synchronized void restablishCurrentSession() {
        sessionToken = new SessionToken(sharedPreferences.getString(SESSION_TOKEN_KEY,READ_ERROR_DEFAULT));
        String dietaryPreferenceKey = sharedPreferences.getString(DIETARY_PREFERENCE_KEY, "");
        dietaryPreferenceMap = new HashMap<>();
        for (DietaryPreference.Type type: DietaryPreference.Type.values()) {
            dietaryPreferenceMap.put(type, new ArrayList<DietaryPreference>());
        }
        /* for (String s : dietaryPreferenceKey.split(",")) {
            if (!"".equals(s)) {
                int id = Integer.parseInt(s);
                DietaryPreference p = dietaryPreferenceStore.getDietaryPreference(id);
                dietaryPreferenceMap.get(p.getType()).add(p);
            }
        } */

    }

    @WorkerThread
    public synchronized void logout() {
        sessionToken = new SessionToken("");
        writeState();
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public synchronized void setUserDietaryPreferences(DietaryPreference.Type type, List<DietaryPreference> dietaryPreferences) {
        dietaryPreferenceMap.put(type, dietaryPreferences);
        writeState();
    }

    public List<DietaryPreference> getUserDietaryPreferences(DietaryPreference.Type type) {
        return dietaryPreferenceMap.get(type);
    }

}
