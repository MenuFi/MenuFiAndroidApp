package com.ckmcknight.android.menufi.model.datastores;

import android.util.Log;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DietaryPreferenceStore {
    private Map<DietaryPreference.Type, Map<Integer, DietaryPreference>> availiblePreferences;
    private boolean receivedPreferences;

    private RemoteMenuDataRetriever dataRetriever;
    private Logger logger = Logger.getLogger("DietaryPreferenceStore");

    @Inject
    DietaryPreferenceStore(RemoteMenuDataRetriever dataRetriever) {
        availiblePreferences = new HashMap<>();
        for (DietaryPreference.Type type: DietaryPreference.Type.values()) {
            availiblePreferences.put(type, new HashMap<Integer, DietaryPreference>());
        }
        this.dataRetriever = dataRetriever;
        receivedPreferences = false;
    }

    public DietaryPreference getDietaryPreference(int id) {
        for (Map<Integer, DietaryPreference> preferenceMap: availiblePreferences.values())
            if (preferenceMap.containsKey(id)) {
                return preferenceMap.get(id);
            }
        logger.warning("Tried to retreive Dietary Preference by id but preference does not exist");
        return null;
    }

    public List<DietaryPreference> getDietaryPreferencesList(JSONArray array) {
        List<DietaryPreference> dietaryPreferences = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                dietaryPreferences.add(getDietaryPreference(array.getInt(i)));
            }
        } catch (JSONException e) {
            logger.severe("Error while parsing list of DietaryPreferences:" + e.getMessage());
        }
        return dietaryPreferences;
    }


    public Collection<DietaryPreference> getDietaryPreferences(DietaryPreference.Type type) {
        return availiblePreferences.get(type).values();
    }

    public Collection<Integer> getDietaryPreferenceIds(DietaryPreference.Type type) {
        return availiblePreferences.get(type).keySet();
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
                    availiblePreferences.get(preference.getType()).put(preference.getId(), preference);
                }
            } catch (JSONException e) {
                logger.severe("Couldn't retrieve preferences");
            }
        }
    };

}
