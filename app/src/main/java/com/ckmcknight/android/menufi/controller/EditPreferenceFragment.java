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


/**
 * Created by Connor Wyckoff on 2/14/2018.
 */

public class EditPreferenceFragment extends Fragment{
    private RemoteMenuDataRetriever preferenceDataRetriever;
    private DietaryPreferenceStore dietaryPreferenceStore;
    private List<String> mPreferences = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_preferences, container, false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        preferenceDataRetriever = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().dataRetriever();
        dietaryPreferenceStore = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getDietaryPreferenceStore();

        Collection<DietaryPreference> availiblePreferences = dietaryPreferenceStore.getDietaryPreferences();
        for (DietaryPreference preference: availiblePreferences) {
            if (preference.getType() == 1) {
                mPreferences.add(preference.getName());
            }
        }
        ListView preferencesListView = getView().findViewById(R.id.check_list);
        preferencesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.checkbox_layout, R.id.text, mPreferences);
        preferencesListView.setAdapter(adapter);
    }
}
