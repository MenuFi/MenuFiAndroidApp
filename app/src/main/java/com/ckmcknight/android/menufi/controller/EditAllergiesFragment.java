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
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Connor Wyckoff on 2/24/2018.
 */

public class EditAllergiesFragment extends Fragment {
    @BindView(R.id.save_allergies) Button saveButton;

    private RemoteMenuDataRetriever preferenceDataRetriever;
    private DietaryPreferenceStore dietaryPreferenceStore;
    private UserSharedPreferences userSharedPreferences;
    private List<DietaryPreference> mAllergies = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_allergies, container, false);
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

        final Collection<DietaryPreference> availibleAllergies = dietaryPreferenceStore.getDietaryPreferences(DietaryPreference.Type.ALLERGY);
        mAllergies.clear();
        mAllergies.addAll(availibleAllergies);
        final ListView allergiesListView = getView().findViewById(R.id.check_list);
        //preferencesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        EditAllergiesFragment.MyListAdapter adapter = new MyListAdapter();
        allergiesListView.setAdapter(adapter);
        allergiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView nameText = (CheckedTextView) view.findViewById(R.id.text);
                nameText.toggle();

                DietaryPreference thisAllergy = mAllergies.get(position);
                List<DietaryPreference> currPref = userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.ALLERGY);
                if (currPref.contains(thisAllergy)) {
                    currPref.remove(thisAllergy);
                } else if (!currPref.contains(thisAllergy)) {
                    currPref.add(thisAllergy);
                }
                userSharedPreferences.setUserDietaryPreferences(DietaryPreference.Type.ALLERGY,currPref);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<DietaryPreference> {
        public MyListAdapter() {
            super(getActivity(), R.layout.checkbox_layout, mAllergies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.checkbox_layout, parent, false);
            }

            DietaryPreference thisAllerg = mAllergies.get(position);

            CheckedTextView nameText = itemView.findViewById(R.id.text);
            nameText.setText(thisAllerg.getName());

            if (userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.ALLERGY).contains(thisAllerg)) {
                nameText.setChecked(true);
            } else {
                nameText.setChecked(false);
            }
            return itemView;
        }
    }
}

