package com.ckmcknight.android.menufi.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ckmcknight.android.menufi.R;
import com.ckmcknight.android.menufi.model.accountvalidation.AccountService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private final static String INVALID_LOGIN_TEXT = "Invalid Username or Password";
    private final static String VALID_LOGIN_TEXT = "Login Successful";

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
                startLogin(email, password);
            }
        });

        registerReceiver(new LoginBroadcastReceiver(), new IntentFilter(AccountService.BROADCAST_LOG_IN));
    }

    private void login() {
        Toast.makeText(getApplicationContext(), VALID_LOGIN_TEXT, Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(loginIntent);
    }

    private void handleFailedLogin() {
        Toast.makeText(getApplicationContext(), INVALID_LOGIN_TEXT, Toast.LENGTH_SHORT).show();
    }

    private void startLogin(String email, String password) {
        Intent loginIntent = new Intent(this, AccountService.class);
        loginIntent.setAction(AccountService.LOGIN_ACTION);
        loginIntent.putExtra(AccountService.EMAIL_EXTRA, email);
        loginIntent.putExtra(AccountService.PASSWORD_EXTRA, password);
        startService(loginIntent);
    }

    private class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successful = intent.getBooleanExtra(AccountService.BROADCAST_STATUS, false);
            if (successful) {
                login();
            } else {
                handleFailedLogin();
            }
        }
    }

}
