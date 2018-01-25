package com.ckmcknight.android.menufi.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ckmcknight.android.menufi.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText logEmail = (EditText) findViewById(R.id.logEmail);
        EditText logPassword = (EditText) findViewById(R.id.logPassword);
        Button loginButton = (Button) findViewById(R.id.LoginButton);
        Button regButton = (Button) findViewById(R.id.regButton);

        String strEmail = logEmail.getText().toString();
        String strPassword = logPassword.getText().toString();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(regIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(logIntent);
            }
        });
    }

}
