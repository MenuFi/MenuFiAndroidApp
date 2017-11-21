package com.ckmcknight.android.menufi.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wyckoff on 11/14/2017.
 */

public class Restaurant {
    private static final String TAG = "Restaurant";

    private String name;
    private String location;
    private FoodType type;

    public Restaurant(String name, String location, FoodType type) {
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public static Restaurant from(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String location =jsonObject.getString("location");
            FoodType type = FoodType.AMERICAN;
            return new Restaurant(name, location, type);
        } catch(JSONException e) {
            Log.e(TAG, "error while parsing restaurant from jsonObject: " + e.getMessage());
        }
        return null;
    }

    public static List<Restaurant> restaurantListFrom(JSONArray jsonArray) {
        try {
            List<Restaurant> restaurants = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                restaurants.add(Restaurant.from(jsonArray.getJSONObject(i)));
            }
            return restaurants;
        } catch (JSONException e) {
            Log.e(TAG, "error while parsing restaurants from jsonArray: " + e.getMessage());
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public FoodType getType() {
        return type;
    }

}
