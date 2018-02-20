package com.ckmcknight.android.menufi.model.containers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DietaryPreference {
    private static Map<Integer, Type> typeMap = getTypeMap();

    private int id;
    private String name;
    private Type type;

    public DietaryPreference(int id, String name, Type type) {
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

    public Type getType() {
        return type;
    }

    public static DietaryPreference from(JSONObject object) {
        try {
            int id = object.getInt("dietaryPreferenceId");
            String name = object.getString("name");
            Type type = DietaryPreference.getType(object.getInt("type"));
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
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null) {return false;}
        if (!(o instanceof DietaryPreference)) {return false;}
        DietaryPreference test = (DietaryPreference) o;
        return this.id == test.id;
    }

    @Override
    public String toString() {
        return name;
    }

    private static Map<Integer, Type> getTypeMap() {
        Map<Integer, Type> typeMap = new HashMap<>();
        for (Type t: Type.values()) {
            typeMap.put(t.getTypeId(), t);
        }
        return typeMap;
    }

    private static Type getType(int id) {
        if (typeMap.containsKey(id)) {
            return typeMap.get(id);
        } else {
            Logger.getLogger("DietaryPreference").warning("Type id does not exist in Preference Enum");
            return Type.INVALID;
        }
    }

    public enum Type {
        INVALID(-1), PREFERENCE(0), ALLERGY(1);

        private int typeId;

        Type(int typeId) {
            this.typeId = typeId;
        }

        public int getTypeId() {
            return typeId;
        }
    }

}
