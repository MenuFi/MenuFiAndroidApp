package com.ckmcknight.android.menufi.model.containers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private static final String TAG = "Restaurant";

    private String name;
    private int price;
    private FoodType type;
    private int id;

    public Restaurant(int id, String name, int price, FoodType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public static Restaurant from(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("restaurantId");
            String name = jsonObject.getString("name");
            int price = jsonObject.getInt("price");
            FoodType type = FoodType.AMERICAN;
            return new Restaurant(id, name, price, type);
        } catch (JSONException e) {
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

    public int getPrice() {
        return price;
    }

    public FoodType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public static JsonCreator<Restaurant> getCreator() {
        return new JsonCreator<Restaurant>() {
            @Override
            public Restaurant createFromJsonObject(JSONObject object) {
                return from(object);
            }
        };
    }
}
