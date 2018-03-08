package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.containers.MenuItem;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;
import com.ckmcknight.android.menufi.model.datastores.DietaryPreferenceStore;
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MenuItemFragment extends Fragment {
    private List<MenuItem> menuItemsList = new ArrayList<>();
    private MyListAdapter listAdapter;
    private RemoteMenuDataRetriever menuDataRetriever;
    private DietaryPreferenceStore dietaryPreferenceStore;
    private UserSharedPreferences userSharedPreferences;
    private LayoutInflater inflater;
    private int restaurantId;
    private List<DietaryPreference> allergies;
    private Switch allergySwitch;
    private ListView itemListView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");
        restaurantId = bundle.getInt("id");
        getActivity().setTitle(name);
        return inflater.inflate(R.layout.fragment_menu_item, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        allergySwitch.toggle();
        allergySwitch.toggle();
        //filterAllergy(allergySwitch.isChecked());
    }

    @Override
    public void onStart() {
        super.onStart();
        menuDataRetriever = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().dataRetriever();
        dietaryPreferenceStore = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getDietaryPreferenceStore();
        userSharedPreferences = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getUserSharedPreferences();

        allergySwitch = getActivity().findViewById(R.id.allergy_filter);
        allergies = userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.ALLERGY);

        //mockPopulateMenuItemList();

        itemListView = getView().findViewById(R.id.menuItemListView);
        listAdapter = new MyListAdapter();
        itemListView.setAdapter(listAdapter);




        menuDataRetriever.requestMenuItemsList(restaurantId, menuItemsList, listAdapter, MenuItem.getCreator(dietaryPreferenceStore));


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

    private void filterAllergy(boolean checked) {
        Log.e("FILTERING ALLERGIES", Boolean.toString(checked));
        for (int i = 0; i < menuItemsList.size(); i++) {
            if (checked) {
                List<DietaryPreference> itemAllergies = menuItemsList.get(i).getDietaryPreferences(DietaryPreference.Type.ALLERGY);
                for (DietaryPreference pref : itemAllergies) {
                    if (allergies.contains(pref)) {
                        itemListView.getChildAt(i).setBackgroundResource(R.color.oldGold);
                    }
                }
            } else {
                itemListView.getChildAt(i).setBackgroundResource(R.color.white);
            }
        }
    }


    private void mockPopulateMenuItemList() {
        MenuItem burger = new MenuItem("Burger", "Half pound angus beef", 6.99f, 4.1f, 1200, new ArrayList<DietaryPreference>());
        burger.setCalories(350);
        MenuItem bbqBurger = new MenuItem("BBQ Burger", "Sweet and Tangy BBQ", 7.99f, 4.5f, 1400, new ArrayList<DietaryPreference>());
        MenuItem fries = new MenuItem("Fries", "Crisp Fries", 2.79f, 4f, 8000, new ArrayList<DietaryPreference>());
        menuItemsList.clear();
        menuItemsList.add(burger);
        menuItemsList.add(bbqBurger);
        menuItemsList.add(fries);
    }

    private class MyListAdapter extends ArrayAdapter<MenuItem> {

        MyListAdapter() { super(getActivity(), R.layout.item_row, menuItemsList); }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
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
