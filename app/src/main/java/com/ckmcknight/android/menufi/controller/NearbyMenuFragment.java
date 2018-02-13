package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.containers.FoodType;
import com.ckmcknight.android.menufi.model.containers.Restaurant;
import com.ckmcknight.android.menufi.model.datahandlers.RemoteMenuDataRetriever;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class NearbyMenuFragment extends Fragment {
    private List<Restaurant> mRestaurants = new ArrayList<>();
    private MyListAdapter listAdapter;
    private RemoteMenuDataRetriever menuDataRetriever;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_menu, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
        mockPopulateRestaurantList();
    }

    @Override
    public void onStart() {
        super.onStart();
        menuDataRetriever = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().dataRetriever();

        final ListView restaurantListView = getView().findViewById(R.id.restsListView);
        listAdapter = new MyListAdapter();
        restaurantListView.setAdapter(listAdapter);
        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new MenuItemFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", mRestaurants.get(position).getName());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentLocLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        mRestaurants.clear();
    }

    private void populateRestaurantList() {
                menuDataRetriever.retrieveNearbyRestaurantList(new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        mRestaurants.clear();
                        mRestaurants.addAll(Restaurant.restaurantListFrom(response));
                        listAdapter.notifyDataSetChanged();
                    }
                });
    }

    //create mock restaurants and menu items in order to show view items
    private void mockPopulateRestaurantList() {
        Restaurant fiveGuys = new Restaurant(0, "Five Guys", "860 Peachtree St. SW", FoodType.AMERICAN);
        Restaurant fourGirls = new Restaurant(1, "Four Girls", "1253 Caroline St. NW", FoodType.AMERICAN);
        mRestaurants.add(fiveGuys);
        mRestaurants.add(fourGirls);
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

            TextView typeText = (TextView) itemView.findViewById(R.id.restType);
            typeText.setText(thisRest.getType().name());

            return itemView;
        }
    }

}
