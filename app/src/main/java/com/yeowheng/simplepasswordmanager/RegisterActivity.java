package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    Button registerViewBtn;
    EditText emailAddress;
    EditText masterPassword;

    private DBManager dbManager;
    private PasswordHelper passwordHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailAddress = (EditText) findViewById(R.id.teEmailAddress);
        masterPassword = (EditText) findViewById(R.id.teMasterPassword);
        registerViewBtn = findViewById(R.id.registerViewBtn);

        dbManager = new DBManager(this);
        dbManager.open();

        passwordHelper = new PasswordHelper();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerViewBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String getEmail = emailAddress.getText().toString();
                String getMasterPw = masterPassword.getText().toString();

                if (getEmail.equals("") || getMasterPw.equals("")) {
                    Toast.makeText(RegisterActivity.this, "please fill up everything", Toast.LENGTH_SHORT).show();
                    return;
                }

                String salt = passwordHelper.GenerateSalt();

                String hashedPassword = passwordHelper.HashPassword(getMasterPw, salt);

                long result = dbManager.insert(getEmail, hashedPassword, salt);

                if (result == -1) {
                    Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbManager.close();
                Toast.makeText(v.getContext(), "Registered!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }

        });
    }


}