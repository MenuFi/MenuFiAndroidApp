package com.ckmcknight.android.menufi.model.containers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ckmcknight.android.menufi.model.datafetchers.NetworkController.TAG;

public class MenuItem {
    private String name;
    private String description;
    private float price;
    private float ratings;
    private int calories;


    public MenuItem(String name, String description, float price, float ratings) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ratings = ratings;
        int calories;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public float getRatings() {
        return ratings;
    }

    public int getCalories() { return calories; }

    public void setCalories(int cal) { calories = cal; }

    public static MenuItem from(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String description =jsonObject.getString("description");
            float price = (float)(jsonObject.getDouble("price"));
            float rating = (float)(jsonObject.getDouble("rating"));
            int calories = (int) (jsonObject.getInt("calories"));
            return new MenuItem(name, description,price,rating);
        } catch(JSONException e) {
            Log.e(TAG, "error while parsing restaurant from jsonObject: " + e.getMessage());
        }
        return null;
    }

    public static List<MenuItem> menuListFrom(JSONArray jsonArray) {
        try {
            List<MenuItem> menuItems = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                menuItems.add(MenuItem.from(jsonArray.getJSONObject(i)));
            }
            return menuItems;
        } catch (JSONException e) {
            Log.e(TAG, "error while parsing restaurants from jsonArray: " + e.getMessage());
        }
        return null;
    }

    public static JsonCreator<MenuItem> getCreator() {
        return new JsonCreator<MenuItem>() {
            @Override
            public MenuItem createFromJsonObject(JSONObject object) {
                return from(object);
            }
        };
    }
}

