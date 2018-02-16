package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.dagger.components.MenuFiComponent;
import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.containers.Restaurant;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;
import com.ckmcknight.android.menufi.model.datastores.DietaryPreferenceStore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.ckmcknight.android.menufi.dagger.components.MenuFiComponent;
import com.ckmcknight.android.menufi.model.datastores.DietaryPreferenceStore;
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Connor Wyckoff on 2/14/2018.
 */

public class EditPreferenceFragment extends Fragment{
    private DietaryPreferenceStore dietaryPreferenceStore;
    private UserSharedPreferences sharedPreferences;
    @BindView(R.id.check_list) ListView preferencesListView;
    @BindView(R.id.save_button) Button saveButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_preferences, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
       super.onStart();
       dietaryPreferenceStore = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getDietaryPreferenceStore();
       sharedPreferences = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getUserSharedPreferences();

       List<DietaryPreference> availibleDietaryPreferences = new ArrayList<>(dietaryPreferenceStore.getDietaryPreferences());
       List<DietaryPreference> selectedIds = sharedPreferences.getUserDietaryPreferences();
       ArrayAdapter<DietaryPreference> adapter = new ArrayAdapter<>(getActivity(), R.layout.checkbox_layout, R.id.text, availibleDietaryPreferences);
       preferencesListView.setAdapter(adapter);
    }
}
