package com.example.symphonia.Activities.User_Management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Activities.User_Management.SignUp.Step1Activity;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Activity that Welcome page that user chooses
 * his action(login, sign up and connect with facebook)
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class WelcomeActivity extends AppCompatActivity implements RestApi.updateUIFacebook {

    /**
     * Holds user type(listener or artist)
     */
    private String mType;
    /**
     * holds progress bar to load until facebook login
     */
    private ProgressBar progressBar;
    /**
     * holds facebook login button
     */
    private LoginButton loginButton;
    /**
     * holds callback manager of facebook REST API
     */
    private CallbackManager callbackManager;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //receives user types from previous activity
        Bundle b = getIntent().getExtras();
        mType = b.getString("user");

        //checks if not mock mode, then check facebook login
        if(!Constants.DEBUG_STATUS) {

            //get views from their ids
            progressBar = (ProgressBar) findViewById(R.id.progress_welcome);
            loginButton = (LoginButton) findViewById(R.id.login_button);

            //sets facebook permissions of (profile, email)
            loginButton.setPermissions("public_profile", "email");

            //initializes callback manager to handle request
            callbackManager = CallbackManager.Factory.create();

            //start a new request if button is clicked
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                //handles if request is succeeded
                @Override
                public void onSuccess(LoginResult loginResult) {

                    //makes loading visible
                    progressBar.setVisibility(View.VISIBLE);

                    //checks facebook permissions are as required
                    Set<String> Permission = loginResult.getAccessToken().getPermissions();

                    //retrieves token, id and image
                    String token = loginResult.getAccessToken().getToken();
                    String id = loginResult.getAccessToken().getUserId();
                    String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large";

                    //sends data to server to validate
                    getUserInfo(token, imageUrl);
                }

                //handles if request is failed
                @Override
                public void onCancel() {
                    cancelLogin();
                }

                //handles if request has error
                @Override
                public void onError(FacebookException error) {
                    ErrorMessage();
                }
            });
        }
    }

    /**
     * send API request to server to validate data
     * @param token holds facebook token
     * @param ImageUrl holds facebook image url
     */
    public void getUserInfo(String token, String ImageUrl){
        ServiceController serviceController = ServiceController.getInstance();
        serviceController.facebookLogin(this,token, ImageUrl);
    }

    /**
     * handles if login with facebook is failed
     */
    public void cancelLogin(){
        Toast.makeText(this,"Failed login",Toast.LENGTH_SHORT).show();

        //sends user back to start activity
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    /**
     * handles if login with facebook has error
     */
    public void ErrorMessage(){
        Toast.makeText(this,"Error login",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    /**
     * holds showing facebook token for checking
     * @param token holds facebook token
     */
    public void showToken(String token){
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

    }

    /**
     * handles activity results from facebook request
     * @param requestCode holds request code to request
     * @param resultCode holds respond code from request
     * @param data holds data returned from request
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Opens login page
     * @param view holds pressed button(Login button)
     */
    public void openLogin(View view) {
        //open activity login with user type
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    /**
     * Opens sign up with first step
     * @param view holds pressed button(sign up button)
     */
    public void openSignUp1(View view) {
        //opens sign up with user type
        Intent i = new Intent(this, Step1Activity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    /**
     * handles updating ui after success in facebook login
     */
    @Override
    public void updateUIFacebookSuccess() {
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

        progressBar.setVisibility(View.GONE);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    /**
     * handles updating ui when request fails
     */
    @Override
    public void updateUIFacebookFail() {
        progressBar.setVisibility(View.GONE);
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
    }
}
