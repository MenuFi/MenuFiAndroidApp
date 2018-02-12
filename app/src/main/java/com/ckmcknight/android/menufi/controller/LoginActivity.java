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
import com.ckmcknight.android.menufi.model.AccountManagement.AccountService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private final static String INVALID_LOGIN_TEXT = "Invalid Username or Password";

    @BindView(R.id.logEmail) EditText logEmail;
    @BindView(R.id.logPassword) EditText logPassword;
    @BindView(R.id.LoginButton) Button loginButton;
    @BindView(R.id.regButton) Button regButton;

    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        final Context context = this.getApplicationContext();

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
        Intent loginIntent = new Intent(this, AccountService.class);
        loginIntent.putExtra(AccountService.EMAIL_EXTRA, email);
        loginIntent.putExtra(AccountService.PASSWORD_EXTRA, password);
        startService(loginIntent);
        return true;
    }

}
