package com.example.symphonia.Activities.User_Management.ForgetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.User;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.RetrofitApi;
import com.example.symphonia.Service.ServiceController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity that handles Entering new password after forgetting
 *
 * @author Hossam Alaa
 * @since 10-06-2020
 * @version 1.0
 */
public class NewPassword extends AppCompatActivity implements RestApi.updateUIResetPassword {

    /**
     * represents Edit text that holds password input
     */
    private EditText mPassword;
    /**
     * holds token received from email
     */
    private String token;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passsword);

        //holds params received from previous activity which is token
        Bundle b = getIntent().getExtras();
        token="";
        try {
            assert b != null;
            token = b.getString("token");
        }catch(NullPointerException e){
            e.printStackTrace();
            token="";
        }

        //get password input text by id, then set listeners for changing text
        mPassword = findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //checks if text is more then 7 chars, then enable next button
                if (s.length() >= 8) enableButton();
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
     * enables (Next button) to go for next step of sign up
     */
    public void enableButton() {
        //gets (Next button) by id then makes it enabled
        Button login = findViewById(R.id.save);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    /**
     * disables (Next button) to prevent user to go next step
     */
    public void lockButton() {
        //gets (Next button) by id then makes it disabled
        Button login = findViewById(R.id.save);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    /**
     * holds if back button is pressed, then kill app
     */
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    /**
     * holds sending password in request to check validity and save
     * @param view holds save button
     */
    public void goHome(View view) {

        //gets password entered from editText view
        Button btn_save = (Button) findViewById(R.id.save);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btn_save.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        //sending API request to new check password
        ServiceController serviceController = ServiceController.getInstance();
        serviceController.resetPassword(this,mPassword.getText().toString(), token);

    }

    /**
     * handles if response of request is failed
     */
    public void failedReset(){
        //opens save button to retry and turn off progress bar
        Button btn_save = (Button) findViewById(R.id.save);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        btn_save.setVisibility(View.VISIBLE);
    }

    /**
     * handles if response of request is success
     */
    public void successReset(){
        //turns off save button to retry and progress bar
        Button btn_save = (Button) findViewById(R.id.save);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        btn_save.setVisibility(View.VISIBLE);
        // Creates object of SharedPreferences.
        SharedPreferences sharedPref= getSharedPreferences("LoginPref", 0);
        //new Editor
        SharedPreferences.Editor editor= sharedPref.edit();

        //put user's params if REST API mode
            editor.putString("token", Constants.currentToken);
            editor.putString("name", Constants.currentUser.getmName());
            editor.putString("email", Constants.currentUser.getmEmail());
            editor.putString("id",Constants.currentUser.get_id());
            editor.putBoolean("type", Constants.currentUser.isListenerType());
            editor.putBoolean("premium", Constants.currentUser.isPremuim());
            editor.putString("image",Constants.currentUser.getImageUrl());

        editor.apply();

        //sends user to home page
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * handles request api success function in volley
     */
    @Override
    public void updateUIResetSuccess() {
        successReset();
    }

    /**
     * handles request api failure function in volley
     */
    @Override
    public void updateUIResetFailed() {
        failedReset();
    }
}
