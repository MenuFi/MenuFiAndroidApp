package com.ckmcknight.android.menufi.model.datahandlers;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by charlie on 11/20/17.
 */

public class RemoteMenuDataRetriever {
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

    public void makeJsonArrayRequest(String url, Response.Listener<JSONArray> listener) {
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
