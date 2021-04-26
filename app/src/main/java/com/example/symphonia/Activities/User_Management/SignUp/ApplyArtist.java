package com.example.symphonia.Activities.User_Management.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

/**
 * Activity that handles received token from email url link to promote premium
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 10-06-2020
 */
public class ApplyArtist extends AppCompatActivity implements RestApi.updateUIApplyArtist {

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_artist);
        //handles sending user to correspond page
        handleIntent();
    }

    /**
     * handles the new intent coming from url link
     * @param intent holds activity to go to
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    /**
     * handles received url link of apply artist to parse its data
     */
    private void handleIntent() {

        //get url of email message
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        //checks if url is not empty
        if(appLinkData!=null){

            //retrieve token from data
            String newToken = appLinkData.getLastPathSegment();

            //send API request to check token validity
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.applyArtist(this, newToken);
        } else{
            //if url is not valid, then return to start page
            Intent i = new Intent(this, StartActivity.class);
            startActivity(i);
        }
    }

    /**
     * holds updating ui after checking token validity
     */
    @Override
    public void updateUIApplyArtistSuccess() {
        // Creates object of SharedPreferences.
        SharedPreferences sharedPref= getSharedPreferences("LoginPref", 0);
        //new Editor
        SharedPreferences.Editor editor= sharedPref.edit();
        //set user's params if REST API mode

        editor.putString("token", Constants.currentToken);
        editor.putString("name", Constants.currentUser.getmName());
        editor.putString("email", Constants.currentUser.getmEmail());
        editor.putString("id",Constants.currentUser.get_id());
        editor.putBoolean("type", Constants.currentUser.isListenerType());
        editor.putBoolean("premium", Constants.currentUser.isPremuim());
        editor.putString("image",Constants.currentUser.getImageUrl());

        //commits edits
        editor.apply();

        //sends to AddArtist activity to suggest artists for user
        Intent i = new Intent(this, AddArtistsActivity.class);
        i.putExtra("newUser", "true");
        startActivity(i);
    }

    /**
     * holds updating ui if token is expired
     */
    @Override
    public void updateUIApplyArtistFailed() {
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
    }
}
