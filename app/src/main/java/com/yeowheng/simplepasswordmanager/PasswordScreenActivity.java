package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordScreenActivity extends AppCompatActivity {

GridLayout applicationPasswordDetailsLayout;
Button addPassword;
Context context;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = PasswordScreenActivity.this;
        setContentView(R.layout.activity_password_screen);
        TextView DEFAULT_APPLICATION_STRING = new TextView(this);
        TextView DEFAULT_PASSWORD_STRING = new TextView(this);
        addPassword = (Button) findViewById(R.id.btnAddPassword);

        DEFAULT_APPLICATION_STRING.setText(R.string.defaultapplication);
        DEFAULT_PASSWORD_STRING.setText(R.string.defaultpassword);

        applicationPasswordDetailsLayout = (GridLayout) findViewById(R.id.applicationPasswordDetailsID);
        applicationPasswordDetailsLayout.addView(DEFAULT_APPLICATION_STRING);
        applicationPasswordDetailsLayout.addView(DEFAULT_PASSWORD_STRING);

    }

    @Override
    protected void onStart() {
        super.onStart();
        addPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LinearLayout verticalLayout = new LinearLayout(context);
                verticalLayout.setOrientation(LinearLayout.VERTICAL);

                alert.setTitle("Add your application and password");
                alert.setMessage("Tap ok after you have filled in the details");

                final EditText inputApplication = new EditText(context);
                final EditText inputPassword = new EditText(context);
                inputApplication.setHint("Enter application name");
                inputPassword.setHint("Enter password");
                verticalLayout.addView(inputApplication);
                verticalLayout.addView(inputPassword);
                alert.setView(verticalLayout);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String applicationName = inputApplication.getText().toString();
                        String applicationPassword = inputPassword.getText().toString();
                        if (applicationName.equals("") && applicationPassword.equals("")){
                            Toast.makeText(context, "Please fill in both blanks", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            TextView applicationNameTV = new TextView(context);
                            TextView applicationPasswordTV = new TextView(context);
                            applicationPasswordTV.setText(applicationPassword);
                            applicationNameTV.setText(applicationName);
                            applicationPasswordDetailsLayout.addView(applicationNameTV);
                            applicationPasswordDetailsLayout.addView(applicationPasswordTV);

                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();

                    }
                });

                alert.show();
            }
        });
    }
}