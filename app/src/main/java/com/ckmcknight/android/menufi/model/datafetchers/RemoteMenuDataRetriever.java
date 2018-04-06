package com.ckmcknight.android.menufi.model.datafetchers;


import android.widget.BaseAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ckmcknight.android.menufi.model.containers.JsonCreator;
import com.ckmcknight.android.menufi.model.containers.MenuItem;
import com.ckmcknight.android.menufi.model.containers.Restaurant;
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;
import com.google.common.collect.ImmutableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

public class RemoteMenuDataRetriever {
    private NetworkController controller;
    private UserSharedPreferences preferences;
    private Logger logger = Logger.getLogger("RemoteMenuDataRetriever");

    @Inject
    RemoteMenuDataRetriever(NetworkController controller, UserSharedPreferences preferences) {
        this.controller = controller;
        this.preferences = preferences;
    }

    public void requestNearbyRestaurantList(Collection<Restaurant> restaurantList, BaseAdapter adapter, JsonCreator<Restaurant> creator) {
        Response.Listener<JSONObject> listener = listenerCreator(restaurantList, adapter, creator);
        Response.ErrorListener errorListener = errorListenerCreator("Received error while querying restaurant list");
        String url = RemoteUrls.BASE_SERVER_URL + RemoteUrls.RESTAURANTS_EXT;
        makeJsonObjectRequest(url, listener, errorListener);
    }

    public void requestMenuItemsList(int restaurantId, Collection<MenuItem> restaurantList, BaseAdapter adapter, JsonCreator<MenuItem> creator) {
        Response.Listener<JSONObject> listener = listenerCreator(restaurantList, adapter, creator);
        Response.ErrorListener errorListener = errorListenerCreator("Received error while querying menu items");
        String url = RemoteUrls.BASE_SERVER_URL + String.format(Locale.US, RemoteUrls.MENU_ITEMS_FORMAT_EXT, restaurantId);
        makeJsonObjectRequest(url, listener, errorListener);
    }

    public void requestDietaryPreferences(Response.Listener<JSONObject> listener) {
        Response.ErrorListener errorListener = errorListenerCreator("Received error while querying dietary preferences");
        String url = RemoteUrls.BASE_SERVER_URL + RemoteUrls.PREFERENCES_EXT;
        makeJsonObjectRequest(url, listener, errorListener);
    }

    public void requestPersonalMenuItemRating(Response.Listener<JSONObject> listener, int menuItemId) {
        Response.ErrorListener errorListener = errorListenerCreator("Failed to retrieve personal menu item rating");
        String url = RemoteUrls.BASE_SERVER_URL + String.format(RemoteUrls.PERSONAL_MENU_ITEM_RATING_FORMAT_EXT, menuItemId);
        makeJsonObjectRequest(url, listener, errorListener);
    }

    public void requestAverageMenuItemRating(Response.Listener<JSONObject> listener, int menuItemId) {
        Response.ErrorListener errorListener = errorListenerCreator("Failed to retrieve average menu item rating");
        String url = RemoteUrls.BASE_SERVER_URL + String.format(RemoteUrls.AVERAGE_MENU_ITEM_RATING_FORMAT_EXT, menuItemId);
        makeJsonObjectRequest(url, listener, errorListener);
    }

    public void putMenuItemRating(int menuItemId, double rating) {
        Map<String, String> requestMap = ImmutableMap.<String, String>of("rating",Double.toString(rating));
        JSONObject request = new JSONObject(requestMap);
        Response.ErrorListener errorListener = errorListenerCreator("Failed to retrieve menu item rating");
        Response.Listener<JSONObject> putListner = putListenerCreator("Reveived putMenuItemRating response");
        String url = RemoteUrls.BASE_SERVER_URL + String.format(RemoteUrls.PERSONAL_MENU_ITEM_RATING_FORMAT_EXT, menuItemId);
        makeJsonPutRequest(url, request, putListner, errorListener);
    }

    public void registerMenuItemClick(int menuItemId, int restaurantId) {
        Map<String, String> requestMap = ImmutableMap.<String, String>of("menuItemId",Integer.toString(menuItemId), "restaurantId", Integer.toString(restaurantId));
        JSONObject request = new JSONObject(requestMap);
        Response.ErrorListener errorListener = errorListenerCreator("Failed to register menu click");
        Response.Listener<JSONObject> putListner = putListenerCreator("Reveived putMenuItemClick response");
        String url = RemoteUrls.BASE_SERVER_URL + String.format(RemoteUrls.REGISTER_MENU_ITEM_CLICK_FORMAT_EXT, restaurantId, menuItemId);
        makeJsonPostRequest(url, request, putListner, errorListener);

    }

    public void makeJsonObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "MenuFi " + preferences.getSessionToken().getTokenValue());
                return headers;
            }
        };
        logger.info("Token: " + preferences.getSessionToken().getTokenValue());
        controller.addToRequestQueue(jsObjRequest);
    }

    public void makeJsonPutRequest(String url, JSONObject request, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, request, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "MenuFi " + preferences.getSessionToken().getTokenValue());
                return headers;
            }
        };
        logger.info("Token: " + preferences.getSessionToken().getTokenValue());
        controller.addToRequestQueue(jsObjRequest);
    }

    public void makeJsonPostRequest(String url, JSONObject request, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, request, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "MenuFi " + preferences.getSessionToken().getTokenValue());
                return headers;
            }
        };
        logger.info("Token: " + preferences.getSessionToken().getTokenValue());
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

    private Response.Listener<JSONObject> putListenerCreator(final String logMessage) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                logger.info(logMessage);
            }
        };
    }

    private Response.ErrorListener errorListenerCreator(final String logMessage) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logger.info(logMessage);
            }
        };
    }
}
