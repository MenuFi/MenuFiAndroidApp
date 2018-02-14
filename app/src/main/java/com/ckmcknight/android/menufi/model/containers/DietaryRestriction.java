package com.ckmcknight.android.menufi.model.containers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DietaryRestriction {
    private int id;
    private String name;
    private int type;

    public DietaryRestriction(int id, String name, int type) {
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

    public static DietaryRestriction from(JSONObject object) {
        try {
            int id = object.getInt("id");
            String name = object.getString("name");
            int type = object.getInt("type");
            return new DietaryRestriction(id, name, type);
        } catch(JSONException e) {
            Log.e("DietaryRestriction", "error while parsing DietaryRestriction from jsonObject: " + e.getMessage());
        }
        return null;
    }

    public static JsonCreator<DietaryRestriction> getCreator() {
        return new JsonCreator<DietaryRestriction>() {
            @Override
            public DietaryRestriction createFromJsonObject(JSONObject object) {
                return from(object);
            }
        };
    }
}
