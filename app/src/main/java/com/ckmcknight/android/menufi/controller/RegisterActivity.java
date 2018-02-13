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
import com.ckmcknight.android.menufi.model.AccountManagement.AccountService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    private static String INVALID_REGISTER_TEXT = "Invalid Username or Password";

    @BindView(R.id.regEmail) EditText regEmail;
    @BindView(R.id.regPassword) EditText regPassword;
    @BindView(R.id.registerButton) Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                startRegistration(email, password);
            }
        });

        registerReceiver(new RegisterBroadcastReceiver(), new IntentFilter(AccountService.BROADCAST_REGISTERED));
    }

    private void startRegistration(String email, String password) {
        Intent registerIntent = new Intent(this, AccountService.class);
        registerIntent.setAction(AccountService.REGISTER_ACTION);
        registerIntent.putExtra(AccountService.EMAIL_EXTRA, email);
        registerIntent.putExtra(AccountService.PASSWORD_EXTRA, password);
        startService(registerIntent);
    }

    private void registerFailed() {
        Toast.makeText(getApplicationContext(), INVALID_REGISTER_TEXT, Toast.LENGTH_SHORT).show();
    }

    private void registerSuccessful() {
        Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(regIntent);
    }

    private class RegisterBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successful = intent.getBooleanExtra(AccountService.BROADCAST_STATUS, false);
            if (successful) {
                registerSuccessful();
            } else {
                registerFailed();
            }
        }
    }

}
