package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.MenuItem;
import com.ckmcknight.android.menufi.model.Restaurant;
import com.ckmcknight.android.menufi.model.datahandlers.RemoteMenuDataRetriever;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class NearbyMenuFragment extends Fragment {
    private List<Restaurant> mRestaurants = new ArrayList<>();
    private MyListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_menu, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateRestaurantList();
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView restaurantListView = getView().findViewById(R.id.restsListView);
        listAdapter = new MyListAdapter();
        restaurantListView.setAdapter(listAdapter);
        populateRestaurantList();
        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), MenuItemActivity.class);
                intent.putExtra("restID", mRestaurants.get(position).getId());
                intent.putExtra("restName", mRestaurants.get(position).getName());
                getActivity().startActivity(intent);


            }
        });
    }

    private void populateRestaurantList() {
        RemoteMenuDataRetriever.getRemoteMenuDataRetriever(getActivity().getApplicationContext())
                .retrieveNearbyRestaurantList(new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        mRestaurants.clear();
                        mRestaurants.addAll(Restaurant.restaurantListFrom(response));
                        listAdapter.notifyDataSetChanged();
                    }
                });
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
            distanceText.setText(thisRest.getLocation());

            return itemView;
        }
    }

}
