package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    private DBManager dbManager;
    String[] userData;

    EditText masterPassword;

    PasswordHelper passwordHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginViewBtn);
        masterPassword = (EditText) findViewById(R.id.teMasterPassword);
        dbManager = new DBManager(this);
        dbManager.open();
        passwordHelper = new PasswordHelper();

    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getMasterPassword = masterPassword.getText().toString();

                if (getMasterPassword.equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter your master password", Toast.LENGTH_SHORT).show();
                    return;
                }

                userData = dbManager.fetch(getMasterPassword);
                if (userData == null) {
                    Toast.makeText(LoginActivity.this, "Invalid master password or Master Password not found. Please create an account if you have not done so.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String dbHashedPassword = userData[0];
                String dbSaltValue = userData[1];

                Boolean validatePassword = passwordHelper.PasswordValidator(getMasterPassword, dbHashedPassword, dbSaltValue);

                if (!validatePassword){
                    Toast.makeText(LoginActivity.this, "Invalid master password", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(LoginActivity.this, PasswordScreenActivity.class));

            }
        });
    }
}