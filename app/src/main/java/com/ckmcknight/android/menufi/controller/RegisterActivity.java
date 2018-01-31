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

import com.ckmcknight.android.menufi.MenuFiApplication;
import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.AccountManagement.AccountManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    private static String INVALID_REGISTER_TEXT = "Invalid Username or Password";

    @BindView(R.id.regEmail) EditText regEmail;
    @BindView(R.id.regPassword) EditText regPassword;
    @BindView(R.id.registerButton) Button registerButton;

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        accountManager = ((MenuFiApplication) getApplication()).getAccountComponent().accountManager();


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
        if (accountManager.register(email, password)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), INVALID_REGISTER_TEXT, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
