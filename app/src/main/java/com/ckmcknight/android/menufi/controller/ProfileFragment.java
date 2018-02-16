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

import java.util.Collection;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.edit_preferences_button) Button editPrefButton;
    @BindView(R.id.edit_allergies_button) Button editAllergiesButton;
    private DietaryPreferenceStore dietaryPreferenceStore;
    private RemoteMenuDataRetriever preferenceDataRetriever;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        editAllergiesButton.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("My Profile");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        super.onStart();
        preferenceDataRetriever = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().dataRetriever();
        dietaryPreferenceStore = ((MenuFiApplication) getActivity().getApplication()).getMenuFiComponent().getDietaryPreferenceStore();

        TextView text1 = getView().findViewById(R.id.profile_preferences_textview);

        /**
        Collection<DietaryPreference> collection = dietaryPreferenceStore.getDietaryPreferences();
        for (Iterator<DietaryPreference> iterator = collection.iterator(); iterator.hasNext();) {
            String text = text1.getText() + iterator.next().getName() + ", ";
            text1.setText(text);
        }
         */
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
