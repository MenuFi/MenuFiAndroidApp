package com.ckmcknight.android.menufi.model;

/**
 * Created by Wyckoff on 11/14/2017.
 */

public class Restaurant {
    private String name;
    private String distance;
    private FoodType type;

    public Restaurant(String name, String distance, FoodType type) {
        this.name = name;
        this.distance = distance;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public FoodType getType() {
        return type;
    }

}
