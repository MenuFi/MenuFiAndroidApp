package com.ckmcknight.android.menufi.model.datahandlers;

import com.ckmcknight.android.menufi.model.MenuItem;
import com.ckmcknight.android.menufi.model.Restaurant;

import java.util.List;

/**
 * Interface for explaining what data can be retrieved.
 */
public interface MenuDataRetriever {

    List<Restaurant> getNearbyRestaurants(String location);
    List<MenuItem> getMenuItems(Restaurant restaurant);
}
