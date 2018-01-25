package com.ckmcknight.android.menufi.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ckmcknight.android.menufi.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText regEmail = (EditText) findViewById(R.id.regEmail);
        EditText regPassword = (EditText) findViewById(R.id.regPassword);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        String strEmail = regEmail.getText().toString();
        String strPassword = regPassword.getText().toString();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(regIntent);
            }
        });

    }

}
