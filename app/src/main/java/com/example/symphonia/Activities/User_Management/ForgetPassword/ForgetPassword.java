package com.example.symphonia.Activities.User_Management.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

/**
 * Activity that handles Forget password page
 *
 * @author Hossam Alaa
 * @since 4-10-2020
 * @version 1.0
 */
public class ForgetPassword extends AppCompatActivity implements RestApi.updateUIForgetPassword {

    /**
     * handles updating ui if request is succeeded
     */
    @Override
    public void updateUIForgetPasswordSuccess() {
        //go to nest step (confirmation page)
        goNext();
    }

    /**
     * handles updating ui if request is failed
     */
    @Override
    public void updateUIForgetPasswordFailed() {
        failForget();
    }

    /**
     * edit text that holds email of user
     */
    private EditText email;
    /**
     * holds email that was entered in login activity
     */
    private String userName;
    /**
     * holds user type
     */
    private String type;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //receives type of user from previous activity
        Bundle b = getIntent().getExtras();
        type = b.getString("type");

        //if user entered his mail in login, then take it
        try {
            userName = b.getString("user");
        }catch (NullPointerException e){
            //if not entered, set email with null
            userName = "";
        }

        //get email input text and set it with entered email
        email = findViewById(R.id.emailInput);
        email.setText(userName);

        //checks if it's valid email or not to open button for user
        if(Utils.isValidEmail(userName)) enableButton();
        else lockButton();

        //listener for edit text view
        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //if current text is valid email, then enable button
                if (Utils.isValidEmail(s.toString())) enableButton();
                //if not, then lock button
                else lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    /**
     * handles button enable for user
     */
    public void enableButton() {
        //get button id, then make it enabled and set background with white color(enable sign)
        Button login = findViewById(R.id.getlink);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    /**
     * handles button locking for user
     */
    public void lockButton() {
        //get button id, then make it locked and set background with gray color(lock sign)
        Button login = findViewById(R.id.getlink);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    /**
     * handles listener for send email button
     * @param view holds clicked view
     */
    public void sendMail(View view) {
        if(Constants.DEBUG_STATUS)
            goNext();
        else {
            //if current state is server, then send request
            email = findViewById(R.id.emailInput);
            Button btn_getLink = (Button) findViewById(R.id.getlink);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            btn_getLink.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.forgetPassword(this,email.getText().toString());
        }
    }

    public void goNext(){
        //turn of progress bar and unlock next button
        Button btn_getLink = (Button) findViewById(R.id.getlink);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        btn_getLink.setVisibility(View.VISIBLE);
        //get email from input text by id
        email = findViewById(R.id.emailInput);
        //then send it to Check Email page
        Intent i = new Intent(this, EmailCheck.class);
        i.putExtra("user", email.getText().toString());
        startActivity(i);
    }

    public void failForget(){
        //locks next button and hides progress bar
        Button btn_getLink = (Button) findViewById(R.id.getlink);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        btn_getLink.setVisibility(View.VISIBLE);
    }
}
