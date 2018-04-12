package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.datafetchers.ImageRetriever;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class MenuItemDetailFragment extends Fragment {
    private RatingBar ratings;
    private Button rateButton;
    private ImageView foodImageView;
    private TextView avgRatingTextView;
    private TextView foodNameTextView;
    private TextView caloriesTextView;
    private TextView ingredientsTextView;
    private Logger logger = Logger.getLogger("MenuItemDetailFragment");
    private RemoteMenuDataRetriever dataRetriever;
    private int menuItemId;
    private int restaurantId;
    private String imageUrl;
    private String ingredients;
    private String foodName;
    private int calories;


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

        foodNameTextView = getView().findViewById(R.id.foodName);
        caloriesTextView = getView().findViewById(R.id.foodCalories);
        ingredientsTextView = getView().findViewById(R.id.foodIngredients);
        foodImageView = getView().findViewById(R.id.foodImage);
        avgRatingTextView = getView().findViewById(R.id.avgStarRating);
        Bundle bundle = this.getArguments();

        foodName = bundle.getString("name");
        menuItemId = bundle.getInt("menuItemId");
        restaurantId = bundle.getInt("restaurantId");
        imageUrl = bundle.getString("imageUrl");
        calories = bundle.getInt("cal");
        ingredients = bundle.getString("ingredients");

        caloriesTextView.setText("Calories: " + Integer.toString(calories));
        ingredientsTextView.setText("Ingredients: " + ingredients);
        foodNameTextView.setText(foodName);

        new RetrieveImage().execute(imageUrl);

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

    private class RetrieveImage extends AsyncTask<String, String, Bitmap>{

        @Override
        protected Bitmap doInBackground(String[] url) {
            return (new ImageRetriever()).retreiveBitmapFromUrl(url[0]);
        }

        @Override
        protected void onPostExecute (Bitmap bitmap) {
            if (bitmap == null) {
                logger.info("failed to load item image");
            } else {
                foodImageView.setImageBitmap(bitmap);
            }
        }
    }

}



