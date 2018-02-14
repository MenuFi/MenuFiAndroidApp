package com.ckmcknight.android.menufi.model.datafetchers;


import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ckmcknight.android.menufi.model.containers.JsonCreator;
import com.ckmcknight.android.menufi.model.containers.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Locale;
import java.util.logging.Logger;

import javax.inject.Inject;

public class RemoteMenuDataRetriever {
    private NetworkController controller;
    private Logger logger = Logger.getLogger("RemoteMenuDataRetriever");

    private static final String NEARBY_RESTAURANT_QUERY_FORMAT = "/restaurants/nearby?location=%s";
    private static final String MENU_ITEM_LIST_QUERY_FORMAT = "/items?restaurantId=%d";

    @Inject
    RemoteMenuDataRetriever(NetworkController controller) {
        this.controller = controller;
    }

    public void requestNearbyRestaurantList(Collection<Restaurant> restaurantList, BaseAdapter adapter, JsonCreator<Restaurant> creator) {
        Response.Listener<JSONObject> listener = listenerCreator(restaurantList, adapter, creator);
        Response.ErrorListener errorListener = errorListenerCreator("Received error while querying restaurant list");
        String url = RemoteUrls.BASE_SERVER_URL + RemoteUrls.RESTAURANTS_EXT;
        makeJsonObjectRequest(url, listener, errorListener);
    }

    public void requestMenuItemsList(int restaurantId, Collection<Restaurant> restaurantList, BaseAdapter adapter, JsonCreator<Restaurant> creator) {
        Response.Listener<JSONObject> listener = listenerCreator(restaurantList, adapter, creator);
        Response.ErrorListener errorListener = errorListenerCreator("Received error while querying menu items");
        String url = RemoteUrls.BASE_SERVER_URL + String.format(Locale.US, MENU_ITEM_LIST_QUERY_FORMAT, restaurantId);
        makeJsonObjectRequest(url, listener, errorListener);
    }

    private void makeJsonObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        controller.addToRequestQueue(jsObjRequest);
    }

    private <T> Response.Listener<JSONObject> listenerCreator(final Collection<T> outputCollection, final BaseAdapter adapter, final JsonCreator<T> creator) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray responseData = response.getJSONArray("data");
                    outputCollection.clear();
                    for (int i = 0; i < responseData.length(); i++) {
                            JSONObject object = responseData.getJSONObject(i);
                            outputCollection.add(creator.createFromJsonObject(object));
                    }
                } catch (JSONException e) {
                    logger.severe("Failed to convert read from JSON array in listener creator");
                }
                adapter.notifyDataSetChanged();
            }
        };
    }

    private Response.ErrorListener errorListenerCreator(final String logMessage) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logger.info(logMessage);
                logger.info(error.getLocalizedMessage());

            }
        };
    }
}
