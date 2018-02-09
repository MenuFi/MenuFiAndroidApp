package com.ckmcknight.android.menufi.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.DataContainers.MenuItem;

public class MenuItemDetailFragment extends Fragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_detail);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = getLayoutInflater().inflate(R.layout.activity_menu_item_detail, parent, false);

        }

        TextView foodText = itemView.findViewById(R.id.foodName);

        TextView descText = itemView.findViewById(R.id.itemDescription);

        TextView calorieText = itemView.findViewById(R.id.itemPrice);

        return itemView;
    }
}
