package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class MenuItemDetailFragment extends Fragment {
    private RatingBar ratings;
    private Button rateButton;
    private TextView avgRatingTextView;
    private Logger logger = Logger.getLogger("MenuItemDetailFragment");
    private RemoteMenuDataRetriever dataRetriever;
    private int menuItemId;
    private int restaurantId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_item_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        dataRetriever.requestPersonalMenuItemRating(getPersonalUserRatingListener, menuItemId);
        dataRetriever.requestAverageMenuItemRating(getAverageUserRatingListener, menuItemId);
    }

    @Override
    public void onStart() {

        super.onStart();
        dataRetriever = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().dataRetriever();

        TextView text1 = getView().findViewById(R.id.foodName);
        TextView text2 = getView().findViewById(R.id.foodCalories);
        avgRatingTextView = getView().findViewById(R.id.avgStarRating);
        Bundle bundle = this.getArguments();

        text1.setText(bundle.getString("name"));
        menuItemId = bundle.getInt("menuItemId");
        restaurantId = bundle.getInt("restaurantId");

       // text2.setText("Calories: " + Integer.toString(bundle.getInt("cal")));

        dataRetriever.registerMenuItemClick(menuItemId, restaurantId);

        ratings = getView().findViewById(R.id.ratingBar);
        ratings.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            }
        });

        rateButton = getView().findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rate = ratings.getRating();
                dataRetriever.putMenuItemRating(menuItemId, rate);
                Toast.makeText(getActivity(), rate + " Star Rating Submitted", Toast.LENGTH_LONG).show();
            }
        });

    }

    private Response.Listener<JSONObject> getPersonalUserRatingListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONObject data = response.getJSONObject("data");
                float rating = (float) data.getDouble("rating");
                ratings.setRating(rating);
            } catch (JSONException e) {
                logger.info("Failed to parse response after getting personal menu item rating");
            }
        }
    };

    private Response.Listener<JSONObject> getAverageUserRatingListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                float rating = (float) response.getDouble("data");
                String ratingText = String.format("Rating: %.1f", rating);
                avgRatingTextView.setText(ratingText);
            } catch (JSONException e) {
                logger.info("Failed to parse response after getting average menu item rating");
                logger.info(e.getMessage());
            }

        }
    };

}



