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
import android.widget.Toast;

import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.AccountManagement.AccountManager;

public class RegisterActivity extends AppCompatActivity {
    private static String INVALID_REGISTER_TEXT = "Invalid Username or Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regEmail = findViewById(R.id.regEmail);
        final EditText regPassword = findViewById(R.id.regPassword);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptRegister(regEmail.getText().toString(), regPassword.getText().toString())) {
                    Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(regIntent);
                }
            }
        });

    }


    private boolean attemptRegister(String email, String password) {
        AccountManager manager = AccountManager.getAccountManager();
        if (manager.register(email, password)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), INVALID_REGISTER_TEXT, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
