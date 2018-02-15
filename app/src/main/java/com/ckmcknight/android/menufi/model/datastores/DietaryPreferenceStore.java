package com.ckmcknight.android.menufi.model.datastores;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DietaryPreferenceStore {
    private Map<Integer, DietaryPreference> availiblePreferences;
    private boolean receivedPreferences;

    private RemoteMenuDataRetriever dataRetriever;
    private Logger logger = Logger.getLogger("DietaryPreferenceStore");

    @Inject
    DietaryPreferenceStore(RemoteMenuDataRetriever dataRetriever) {
        availiblePreferences = new HashMap<>();
        this.dataRetriever = dataRetriever;
        receivedPreferences = false;
    }

    public DietaryPreference getDietaryPreference(int id) {
        return availiblePreferences.get(id);
    }

    public Collection<DietaryPreference> getDietaryPreferences() {
        return availiblePreferences.values();
    }

    public Collection<Integer> getDietaryPreferenceIds() {
        return availiblePreferences.keySet();
    }

    public boolean getPreferencesReceived() {
        return receivedPreferences;
    }

    public void syncDietaryPreferences() {
        logger.info("Attempting to sync Dietary Preferences");
        dataRetriever.requestDietaryPreferences(preferenceListener);
    }

    private Response.Listener<JSONObject> preferenceListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray preferenceList = response.getJSONArray(RemoteUrls.JSON_DATA_KEY);
                receivedPreferences = true;
                for (int i = 0; i < preferenceList.length(); i++) {
                    DietaryPreference preference = DietaryPreference.from(preferenceList.getJSONObject(i));
                    availiblePreferences.put(preference.getId(), preference);
                }
            } catch (JSONException e) {
                logger.severe("Couldn't retrieve preferences");
            }
        }
    };

}
