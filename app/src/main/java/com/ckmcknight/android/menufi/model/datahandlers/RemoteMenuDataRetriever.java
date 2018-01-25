package com.ckmcknight.android.menufi.model.datahandlers;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.Locale;

/**
 * Created by charlie on 11/20/17.
 */
public class RemoteMenuDataRetriever {
    private NetworkController controller;
    private static RemoteMenuDataRetriever dataRetriever;

    private static final String BASE_URL = "http://128.61.105.97:8080";
    private static final String NEARBY_RESTAURANT_QUERY_FORMAT = "/restaurants/nearby?location=%s";
    private static final String MENU_ITEM_LIST_QUERY_FORMAT = "/items?restaurantId=%d";

    private RemoteMenuDataRetriever(Context context) {
        controller = NetworkController.getNetworkController(context);
    }

    public static synchronized RemoteMenuDataRetriever getRemoteMenuDataRetriever(Context c) {
        if (dataRetriever == null) {
            dataRetriever = new RemoteMenuDataRetriever(c);
        }
        return dataRetriever;
    }

    public void retrieveNearbyRestaurantList(Response.Listener<JSONArray> listener) {
        String url = BASE_URL + String.format(Locale.US, NEARBY_RESTAURANT_QUERY_FORMAT, "here");
        makeJsonArrayRequest(url, listener);
    }

    public void retrieveMenuItemsList(Response.Listener<JSONArray> listener, int restaurantId) {
        String url = BASE_URL + String.format(Locale.US, MENU_ITEM_LIST_QUERY_FORMAT, restaurantId);
        makeJsonArrayRequest(url, listener);
    }

    private void makeJsonArrayRequest(String url, Response.Listener<JSONArray> listener) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, listener, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        controller.addToRequestQueue(jsObjRequest);
    }
}