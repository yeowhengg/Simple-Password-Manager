package com.yeowheng.simplepasswordmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PasswordScreenActivity extends AppCompatActivity {

GridLayout applicationPasswordDetailsLayout;
Button addPassword;
Context context;

private DBManager dbManager;


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

        dbManager = new DBManager(this);
        dbManager.open();



        Map<String, String> map = new HashMap<String, String>();
        map = dbManager.RetrieveData();

        if (map != null) {
            Log.i("hashmap", map.toString());

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                TextView applicationTV = new TextView(this);
                TextView passwordTV = new TextView(this);
                applicationTV.setText(key);
                passwordTV.setText(val);
                applicationPasswordDetailsLayout.addView(applicationTV);
                applicationPasswordDetailsLayout.addView(passwordTV);
            }
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        addPassword.setOnClickListener(new View.OnClickListener() {
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
                            return;
                        }

                        TextView applicationNameTV = new TextView(context);
                        TextView applicationPasswordTV = new TextView(context);
                        applicationPasswordTV.setText(applicationPassword);
                        applicationNameTV.setText(applicationName);

                        long result = dbManager.InsertData(applicationName, applicationPassword);
                        if (result == -1){
                            Toast.makeText(context, "something went wrong..", Toast.LENGTH_SHORT).show();
                        }

                        applicationPasswordDetailsLayout.addView(applicationNameTV);
                        applicationPasswordDetailsLayout.addView(applicationPasswordTV);

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}