package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
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

/**
 * Created by Wyckoff on 11/21/2017.
 */

public class MenuItemActivity extends AppCompatActivity {
    private List<MenuItem> menuItemsList = new ArrayList<>();
    private MyListAdapter listAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        pos = getIntent().getIntExtra("restID", -1);
        Toolbar toolbar = findViewById(R.id.menu_item_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("restName"));

        Log.d("pos", Integer.toString(pos));

        ListView menuItemList = findViewById(R.id.menuItemListView);
        listAdapter = new MyListAdapter();
        menuItemList.setAdapter(listAdapter);
        populateMenuItemList();
    }
    /*
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView menuItemList = getView().findViewById(R.id.menuItemListView);
        listAdapter = new MyListAdapter();
        menuItemList.setAdapter(listAdapter);
    }
    */

    
    private void populateMenuItemList() {
        RemoteMenuDataRetriever.getRemoteMenuDataRetriever(getApplicationContext())
                .retrieveMenuItemsList(new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        menuItemsList.clear();
                        menuItemsList.addAll(MenuItem.menuListFrom(response));
                        listAdapter.notifyDataSetChanged();
                    }
                },pos);
    }


    private class MyListAdapter extends ArrayAdapter<MenuItem> {
        MyListAdapter() { super(MenuItemActivity.this, R.layout.item_row, menuItemsList); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_row, parent, false);
            }

            MenuItem thisItem = menuItemsList.get(position);

            TextView nameText = itemView.findViewById(R.id.itemName);
            nameText.setText(thisItem.getName());

            TextView descText = itemView.findViewById(R.id.itemDescription);
            descText.setText(thisItem.getDescription());

            TextView priceText = itemView.findViewById(R.id.itemPrice);
            priceText.setText(String.valueOf(thisItem.getPrice()));

            TextView ratingsText = itemView.findViewById(R.id.itemRating);
            ratingsText.setText(String.valueOf(thisItem.getRatings()));


            return itemView;
        }
    }
}
