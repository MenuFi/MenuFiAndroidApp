package com.ckmcknight.android.menufi.model.containers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DietaryPreference {
    private int id;
    private String name;
    private int type;

    public DietaryPreference(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static DietaryPreference from(JSONObject object) {
        try {
            int id = object.getInt("dietaryPreferenceId");
            String name = object.getString("name");
            int type = object.getInt("type");
            return new DietaryPreference(id, name, type);
        } catch(JSONException e) {
            Log.e("DietaryPreferenceStore", "error while parsing DietaryPreferenceStore from jsonObject: " + e.getMessage());
        }
        return null;
    }

    public static JsonCreator<DietaryPreference> getCreator() {
        return new JsonCreator<DietaryPreference>() {
            @Override
            public DietaryPreference createFromJsonObject(JSONObject object) {
                return from(object);
            }
        };
    }

    @Override
    public String toString() {
        return name;
    }

}
