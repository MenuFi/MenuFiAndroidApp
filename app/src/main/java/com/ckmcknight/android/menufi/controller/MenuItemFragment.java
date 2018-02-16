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
import com.ckmcknight.android.menufi.model.containers.MenuItem;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MenuItemFragment extends Fragment {
    private List<MenuItem> menuItemsList = new ArrayList<>();
    private MyListAdapter listAdapter;
    private RemoteMenuDataRetriever menuDataRetriever;
    private LayoutInflater inflater;
    private String name;
    private int restaurantId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;

        Bundle bundle = this.getArguments();
        name = bundle.getString("name");
        restaurantId = bundle.getInt("id");
        getActivity().setTitle(name);
        return inflater.inflate(R.layout.fragment_menu_item, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        menuDataRetriever = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().dataRetriever();
        //mockPopulateMenuItemList();

        ListView itemListView = getView().findViewById(R.id.menuItemListView);
        listAdapter = new MyListAdapter();
        itemListView.setAdapter(listAdapter);
        menuDataRetriever.requestMenuItemsList(restaurantId, menuItemsList, listAdapter, MenuItem.getCreator());

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new MenuItemDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", menuItemsList.get(position).getName());
                bundle.putInt("cal", menuItemsList.get(position).getCalories());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentLocLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
       // menuItemsList.clear();
    }

    private void mockPopulateMenuItemList() {
        MenuItem burger = new MenuItem("Burger", "Half pound angus beef", 6.99f, 4.1f);
        burger.setCalories(350);
        MenuItem bbqBurger = new MenuItem("BBQ Burger", "Sweet and Tangy BBQ", 7.99f, 4.5f);
        MenuItem fries = new MenuItem("Fries", "Crisp Fries", 2.79f, 4f);
        menuItemsList.clear();
        menuItemsList.add(burger);
        menuItemsList.add(bbqBurger);
        menuItemsList.add(fries);
    }


    private class MyListAdapter extends ArrayAdapter<MenuItem> {
        MyListAdapter() { super(getActivity(), R.layout.item_row, menuItemsList); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflater.inflate(R.layout.item_row, parent, false);

            }

            MenuItem thisItem = menuItemsList.get(position);

            TextView nameText = itemView.findViewById(R.id.itemName);
            nameText.setText(thisItem.getName());

            TextView descText = itemView.findViewById(R.id.itemDescription);
            descText.setText(thisItem.getDescription());

            TextView priceText = itemView.findViewById(R.id.itemPrice);
            priceText.setText("$" + String.valueOf(thisItem.getPrice()));

            TextView ratingsText = itemView.findViewById(R.id.itemRating);
            ratingsText.setText(String.valueOf(thisItem.getRatings()));


            return itemView;
        }
    }
}
