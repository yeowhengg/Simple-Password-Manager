package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class ForgetPasswordActivity extends AppCompatActivity {
    DBManager dbManager;
    Context context;
    Button forgetPasswordBtn;
    EditText emailAddressET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        context = this;
        dbManager = new DBManager(context);
        dbManager.open();
    }

    @Override
    protected void onStart() {
        super.onStart();

        forgetPasswordBtn = (Button) findViewById(R.id.submitForgetPasswordBtn);
        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddressET = (EditText) findViewById(R.id.teForgetPwEmailAddress);

            }
        });
    }
}