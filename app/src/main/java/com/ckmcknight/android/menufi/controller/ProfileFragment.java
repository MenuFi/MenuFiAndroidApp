package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.containers.DietaryPreference;
import com.ckmcknight.android.menufi.model.datafetchers.RemoteMenuDataRetriever;
import com.ckmcknight.android.menufi.model.datastores.DietaryPreferenceStore;
import com.ckmcknight.android.menufi.model.datastores.UserSharedPreferences;
import com.google.common.base.Joiner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.edit_preferences_button) Button editPrefButton;
    @BindView(R.id.edit_allergies_button) Button editAllergiesButton;
    @BindView(R.id.profile_name_textview) TextView profileNameTextView;
    @BindView(R.id.profile_allergies_textview) TextView profileAllergiesTextView;
    @BindView(R.id.profile_preferences_textview) TextView profilePreferencesTextView;
    private UserSharedPreferences userSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        editPrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditPreferenceFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentLocLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        editAllergiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditAllergiesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentLocLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        getActivity().setTitle("My Profile");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        userSharedPreferences = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getUserSharedPreferences();

        Collection<DietaryPreference> allergies = userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.ALLERGY);
        Collection<DietaryPreference> preferences = userSharedPreferences.getUserDietaryPreferences(DietaryPreference.Type.PREFERENCE);
        profileAllergiesTextView.setText("Allergies: " + Joiner.on(", ").join(allergies));
        profilePreferencesTextView.setText("Preferences: " + Joiner.on(", ").join(preferences));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
