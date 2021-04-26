package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Activity that handles sign up for step3 when user enters his
 * Birth date with some validations
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class Step3Activity extends AppCompatActivity {

    /**
     * Holds textView that shows that user is under-age
     */
    private TextView mDateValidity;
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
     * holds datePicker that's shown to user
     */
    private DatePicker mDatePicker;


    /**
     * Represents the initialization of activity
     *
     * @param savedInstanceState represents received data from other activities
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        //gets data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mEmail = b.getString("email");
        mPassword = b.getString("password");

        //gets textView which indicates for user that DOB is valid or not
        mDateValidity = findViewById(R.id.validDate);

        //gets datePicker by id, then set listeners for changing
        mDatePicker = findViewById(R.id.datePicker1);
        mDatePicker.setMaxDate(new Date().getTime());

        //set initial date, then set listener for changing date pikcer
        mDatePicker.init(2020, 0, 1
                , new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //checks if year is older than 2000, then enable (Next button)
                        if (year <= 1999) enableButton();
                        else lockButton();
                    }
                });
    }

    /**
     * opens next page of sign up
     *
     * @param view holds clicked button
     */
    public void openNext(View view) {
        //goes to next step activity with data
        Intent i = new Intent(this, Step4Activity.class);
        i.putExtra("user", mUser);
        i.putExtra("email", mEmail);
        i.putExtra("password", mPassword);
        String dob = "";
        //concatinates DOB in one string
        int month = mDatePicker.getMonth() + 1;
        int day = mDatePicker.getDayOfMonth();

        dob += "" + mDatePicker.getYear();

        //make date in form of YYYY-MM-DD
        if (month < 10)
            dob += "-0" + month;
        else
            dob += "-" + month;

        if (day < 10)
            dob += "-0" + day;
        else
            dob += "-" + day;

//        Toast.makeText(this,dob,Toast.LENGTH_SHORT).show();
        i.putExtra("DOB", dob);
        startActivity(i);
    }

    /**
     * enables (Next button) to go for next step of sign up
     */
    public void enableButton() {
        //gets (Next button) by id then makes it enabled
        mDateValidity.setVisibility(View.INVISIBLE);
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(true);
        btn_login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    /**
     * disables (Next button) to prevent user to go next step
     */
    public void lockButton() {
        //gets (Next button) by id then makes it disabled
        mDateValidity.setVisibility(View.VISIBLE);
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.btn_curved_gray);
    }
}
