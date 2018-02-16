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
    private int id;
    private String pictureUri;

    public Restaurant(int id, String name, int price, String pictureUri) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pictureUri = pictureUri;
    }

    public static Restaurant from(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("restaurantId");
            String name = jsonObject.getString("name");
            int price = jsonObject.getInt("price");
            String pictureUri = jsonObject.getString("pictureUri");
            return new Restaurant(id, name, price, pictureUri);
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

    public String getPictureUri() {
        return pictureUri;
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
