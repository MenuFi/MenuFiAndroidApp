package com.ckmcknight.android.menufi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ckmcknight.android.menufi.model.Restaurant;
import com.ckmcknight.android.menufi.model.RestaurantType;

import java.util.ArrayList;
import java.util.List;

public class NearbyMenuActivity extends AppCompatActivity {
    private List<Restaurant> mRestaurants = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_menu);

        populateRestaurantList();
        populateListView();
    }


    private void populateRestaurantList() {
        mRestaurants.add(new Restaurant("Six Guys and Fries", "1.3", RestaurantType.AMERICAN));
        mRestaurants.add(new Restaurant("Nick's Pies", "2.2", RestaurantType.DESSERT));
    }

    private void populateListView() {
        ArrayAdapter<Restaurant> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.restsListView);
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Restaurant> {
        public MyListAdapter() {
            super(NearbyMenuActivity.this, R.layout.row, mRestaurants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.row, parent, false);
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
