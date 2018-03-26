package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ckmcknight.android.menufi.R;

public class MenuItemDetailFragment extends Fragment {
    private RatingBar ratings;
    private Button rateButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_item_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {

        super.onStart();

        TextView text1 = getView().findViewById(R.id.foodName);
        TextView text2 = getView().findViewById(R.id.foodCalories);
        Bundle bundle = this.getArguments();

        text1.setText(bundle.getString("name"));
       // text2.setText("Calories: " + Integer.toString(bundle.getInt("cal")));

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
                Toast.makeText(getActivity(), rate + " Star Rating Submitted", Toast.LENGTH_LONG).show();
            }
        });

    }
}



