package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;

import com.ckmcknight.android.menufi.model.containers.DietaryPreference;

import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;
import com.ckmcknight.android.menufi.model.datastores.DietaryPreferenceStore;



import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Connor Wyckoff on 2/14/2018.
 */

public class EditPreferenceFragment extends Fragment{
    @BindView(R.id.save_preferences) Button saveButton;

    private RemoteMenuDataRetriever preferenceDataRetriever;
    private DietaryPreferenceStore dietaryPreferenceStore;
    private UserSharedPreferences userSharedPreferences;
    private List<DietaryPreference> mPreferences = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_preferences, container, false);
        ButterKnife.bind(this, v);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentLocLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
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
        userSharedPreferences = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getUserSharedPreferences();

        final Collection<DietaryPreference> availiblePreferences = dietaryPreferenceStore.getDietaryPreferences(DietaryPreference.Type.PREFERENCE);
        mPreferences.addAll(availiblePreferences);
        final ListView preferencesListView = getView().findViewById(R.id.check_list);
        //preferencesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        MyListAdapter adapter = new MyListAdapter();
        preferencesListView.setAdapter(adapter);
        preferencesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView nameText = (CheckedTextView) view.findViewById(R.id.text);
                nameText.toggle();

                DietaryPreference thisPref = mPreferences.get(position);
                List<DietaryPreference> currPref = userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.PREFERENCE);
                if (currPref.contains(thisPref)) {
                    currPref.remove(thisPref);
                } else if (!currPref.contains(thisPref)) {
                    currPref.add(thisPref);
                }
                userSharedPreferences.setUserDietaryPreferences(DietaryPreference.Type.PREFERENCE,currPref);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<DietaryPreference> {
        public MyListAdapter() {
            super(getActivity(), R.layout.checkbox_layout, mPreferences);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.checkbox_layout, parent, false);
            }

            DietaryPreference thisPref = mPreferences.get(position);

            CheckedTextView nameText = itemView.findViewById(R.id.text);
            nameText.setText(thisPref.getName());

            if (userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.PREFERENCE).contains(thisPref)) {
                nameText.setChecked(true);
            } else {
                nameText.setChecked(false);
            }
            return itemView;
        }
    }
}
