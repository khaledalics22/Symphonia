package com.example.symphonia.Activities.User_Management.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

/**
 * Activity that handles Email check page which appears after forget password
 *
 * @author Hossam Alaa
 * @since 4-10-2020
 * @version 1.0
 */
public class EmailCheck extends AppCompatActivity {

    /**
     * holds user name
     */
    private String userName;

    /**
     * text view which holds message for user concatenating his email with
     */
    private TextView message;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_check);

        //receives user type and email from previous activity
        Bundle b = getIntent().getExtras();
        userName = b.getString("user");

        //get text view by id and set message+user name
        message = findViewById(R.id.message);
        message.append(" " + userName);
    }

    /**
     * Holds listener for open email button
     * @param view which contains pressed view
     */
    public void openGmail(View view) {
        //send user to GMail application on mobile
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        startActivity(intent);
    }
}
