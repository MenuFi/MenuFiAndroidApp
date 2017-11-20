package com.ckmcknight.android.menufi.model.datahandlers;


import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ckmcknight.android.menufi.model.MenuItem;
import com.ckmcknight.android.menufi.model.Restaurant;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by charlie on 11/20/17.
 */

public class RemoteMenuDataRetriever implements MenuDataRetriever {
    private NetworkController controller;
    private static RemoteMenuDataRetriever dataRetriever;

    private RemoteMenuDataRetriever(Context context) {
        controller = NetworkController.getNetworkController(context);
    }

    public static synchronized RemoteMenuDataRetriever getRemoteMenuDataRetriever(Context c) {
        if (dataRetriever == null) {
            dataRetriever = new RemoteMenuDataRetriever(c);
        }
        return dataRetriever;
    }

    @Override
    public List<Restaurant> getNearbyRestaurants(String location) {
        return null;
    }

    @Override
    public List<MenuItem> getMenuItems(Restaurant restaurant) {
        return null;
    }

    private void makeJsonObjectRequest(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("JSON","Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        controller.addToRequestQueue(jsObjRequest);
    }
}
