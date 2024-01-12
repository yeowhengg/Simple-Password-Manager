package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    Button registerViewBtn;
    EditText emailAddress;
    EditText masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailAddress = (EditText) findViewById(R.id.teEmailAddress);
        masterPassword = (EditText) findViewById(R.id.teMasterPassword);
        registerViewBtn = findViewById(R.id.registerViewBtn);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerViewBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String getEmail = emailAddress.getText().toString();
                String getMasterPw = masterPassword.getText().toString();
                Toast.makeText(v.getContext(), "Registered!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("email_key", getEmail);
                intent.putExtra("pw_key", getMasterPw);
                startActivity(intent);

            }
        });
    }


}