package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
private DBManager dbManager;
Button registerBtn;
Button loginBtn;
Button forgetPasswordBtn;
Boolean foundAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        dbManager.open();

        registerBtn = findViewById(R.id.btnRegister);
        loginBtn = findViewById(R.id.btnLogin);
        foundAccount = dbManager.CheckRegistered();

        if(foundAccount){
            forgetPasswordBtn = findViewById(R.id.btnForgetPassword);
            registerBtn.setVisibility(View.GONE);
            forgetPasswordBtn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        if (foundAccount){
            forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class));
                }
            });
        }







    }
}