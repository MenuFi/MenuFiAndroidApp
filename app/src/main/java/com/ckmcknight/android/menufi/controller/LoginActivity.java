package com.ckmcknight.android.menufi.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    private final static String INVALID_LOGIN_TEXT = "Invalid Username or Password";

    @BindView(R.id.logEmail) EditText logEmail;
    @BindView(R.id.logPassword) EditText logPassword;
    @BindView(R.id.LoginButton) Button loginButton;
    @BindView(R.id.regButton) Button regButton;

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        final Context context = this.getApplicationContext();

        accountManager = ((MenuFiApplication) getApplication()).getAccountComponent().accountManager();

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
                String email = logEmail.getText().toString();
                String password = logPassword.getText().toString();
                if (attemptLogin(email, password)){
                    Intent logIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(logIntent);
                }
            }
        });
    }


    private boolean attemptLogin(String email, String password) {
        if (accountManager.login(email, password)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), INVALID_LOGIN_TEXT, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
