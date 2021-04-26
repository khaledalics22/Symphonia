package com.example.symphonia.Activities.User_Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.symphonia.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Activity that handles Splash screen animation
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class SplashScreenActivity extends AppCompatActivity {

    /**
     * splash screen time to appear
     */
    private int mSplashTime =1500;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Toast.makeText(this, "Got notification",Toast.LENGTH_SHORT).show();
            String data = bundle.getString("data");
            JsonObject object = new JsonParser().parse(data).getAsJsonObject();
            String from = object.get("from").getAsString();
            String to = object.get("to").getAsString();
        }
        //creates an object of SplashScreen Library
        //makes it full screen
        //next step is start activity
        //with time assigned in mSplashTime variable
        //with background from file (background3)
        //with logo from file (ic_splash)
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(StartActivity.class)
                .withSplashTimeOut(mSplashTime)
                .withBackgroundResource(R.drawable.gradient)
                .withLogo(R.drawable.ic_splash);

        //creates view of splash screen
        View easySplashScreen = config.create();
        //set layout file
        setContentView(easySplashScreen);
    }
}
