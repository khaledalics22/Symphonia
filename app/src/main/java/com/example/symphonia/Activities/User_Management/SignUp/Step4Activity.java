package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

/**
 * Activity that handles sign up for step4 when user enters his gender
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class Step4Activity extends AppCompatActivity {

    /**
     * Holds user type
     */
    private String mUser;
    /**
     * holds user password
     */
    private String mPassword;
    /**
     * holds user email
     */
    private String mEmail;
    /**
     * Holds user Date of birth
     */
    private String mDOB;
    /**
     * holds gender of user
     */
    private String mGender;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        //get data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mPassword = b.getString("password");
        mEmail = b.getString("email");
        mDOB = b.getString("DOB");
    }

    /**
     * Makes male button marked as chosen, sends to next step
     * @param view holds button clicked(male)
     */
    public void setMale(View view){
        //makes male button marked as pressed, then go for next activity
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        mGender = "male";
        //makes female button as unpressed
        Button btn_female = (Button) findViewById(R.id.female);
        btn_female.setBackgroundResource(R.drawable.btn_curved_border);

        openNext();
    }

    /**
     * Makes female button marked as chosen, sends to next step
     * @param view holds button clicked(female)
     */
    public void setFemale(View view) {
        //makes female button marked as pressed, then go for next activity
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        //makes male button as unpressed
        Button btn_male = (Button) findViewById(R.id.male);
        btn_male.setBackgroundResource(R.drawable.btn_curved_border);
        mGender = "female";

        openNext();
    }

    /**
     * opens next page of sign up
     */
    public void openNext() {
        //goes for next step with all user's data
        Intent i = new Intent(this, Step5Activity.class);
        i.putExtra("user", mUser);
        i.putExtra("email", mEmail);
        i.putExtra("password", mPassword);
        i.putExtra("DOB", mDOB);
        i.putExtra("gender", mGender);
        startActivity(i);
    }
}
