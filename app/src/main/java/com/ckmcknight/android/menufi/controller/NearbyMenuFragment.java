package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.Restaurant;
import com.ckmcknight.android.menufi.model.FoodType;

import java.util.ArrayList;
import java.util.List;

public class NearbyMenuFragment extends Fragment {
    private List<Restaurant> mRestaurants = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_menu, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        populateRestaurantList();
        ListView restaurantListView = getView().findViewById(R.id.restsListView);
        MyListAdapter restaurantListAdapter = new MyListAdapter();
        restaurantListView.setAdapter(restaurantListAdapter);
    }

    private void populateRestaurantList() {
        mRestaurants.add(new Restaurant("Six Guys and Fries", "1.3", FoodType.AMERICAN));
        mRestaurants.add(new Restaurant("Nick's Pies", "2.2", FoodType.DESSERT));
    }

    private class MyListAdapter extends ArrayAdapter<Restaurant> {
        public MyListAdapter() {
            super(getActivity(), R.layout.row, mRestaurants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.row, parent, false);
            }

            Restaurant thisRest = mRestaurants.get(position);

          //  ImageView imageView = (ImageView)itemView.findViewById(R.id.ran_image);

            TextView nameText = (TextView)itemView.findViewById(R.id.restName);
            nameText.setText(thisRest.getName());

            TextView distanceText = (TextView)itemView.findViewById(R.id.restDistance);
            distanceText.setText(thisRest.getDistance());

            return itemView;
        }
    }

}
