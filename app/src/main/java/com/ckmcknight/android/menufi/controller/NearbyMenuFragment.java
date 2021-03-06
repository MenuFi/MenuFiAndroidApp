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

import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.containers.Restaurant;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;

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
        getActivity().setTitle("Restaurants");
        return inflater.inflate(R.layout.fragment_nearby_menu, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        menuDataRetriever.requestNearbyRestaurantList(mRestaurants, listAdapter, Restaurant.getCreator());
        //mockPopulateRestaurantList();
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
                bundle.putInt("id", mRestaurants.get(position).getId());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentLocLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        menuDataRetriever.requestNearbyRestaurantList(mRestaurants, listAdapter, Restaurant.getCreator());
        //mockPopulateRestaurantList();
    }

    //create mock restaurants and menu items in order to show view items
    private void mockPopulateRestaurantList() {
        Restaurant fiveGuys = new Restaurant(0, "Five Guys", 2, "no pic");
        Restaurant fourGirls = new Restaurant(1, "Four Girls", 3, "no pic");
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

            TextView priceText = (TextView)itemView.findViewById(R.id.price);
            int dollars = thisRest.getPrice();
            priceText.setText(String.format("%0" + dollars + "d", 0).replace("0", "$"));

            return itemView;
        }
    }

}
