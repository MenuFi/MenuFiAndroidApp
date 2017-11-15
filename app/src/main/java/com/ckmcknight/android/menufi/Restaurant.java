package com.ckmcknight.android.menufi;

/**
 * Created by Wyckoff on 11/14/2017.
 */

public class Restaurant {
    private String name;
    private String distance;
    private String type;

    public Restaurant(String name, String distance, String type) {
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

    public String getType() {
        return type;
    }

}
