package com.example.symphonia.Activities.User_Interface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Management.ForgetPassword.NewPassword;
import com.example.symphonia.Activities.User_Management.WelcomeActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.User;
import com.example.symphonia.R;

import java.security.MessageDigest;

/**
 * Activity that handles Start page with animations
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class StartActivity extends AppCompatActivity {

    /**
     * time to enter fade of animation
     */
    private int mEnterTime = 2000;
    /**
     * time to exit fade of animation
     */
    private int mExitTime = 2000;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //generates the hash key of app
        printHashKey(this);

        //checks incoming link of Reset password to handle
        handleIntent();


        //open log in shared preferences which contains user's info
        SharedPreferences sharedPref = getSharedPreferences("LoginPref", 0);


        //check if it's mock mode, then login with local account
        if (Constants.DEBUG_STATUS) {
            String token = sharedPref.getString("token", "");

            if (!(token.equals(""))) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }


        //if REST API mode, so load more info from shared preferences
        String token = sharedPref.getString("token", "");
        String id = sharedPref.getString("id", "");
        String name = sharedPref.getString("name", "");
        String email = sharedPref.getString("email", "");
        String image = sharedPref.getString("image", "");
        boolean type = sharedPref.getBoolean("type", true);
        boolean premium = sharedPref.getBoolean("premium", true);

        //if token is not empty, it means there was a user logged before
        if (!(token.equals(""))) {
            Constants.currentToken = token;
            Constants.currentUser = new User(email, id, name, type, premium);
            Constants.currentUser.setImageUrl(image);
            Toast.makeText(this, getString(R.string.welcome) + Constants.currentUser.getmEmail(), Toast.LENGTH_SHORT).show();

            //after set last user data, then go to main activity directly
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        //object from RelativeLayout that holds many layouts for animation
        //which is linked with background of this layout
        RelativeLayout relativeLayout = findViewById(R.id.layout);
        //get background of main layout(gradient_List)
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        //time for appearing one background by ms
        animationDrawable.setEnterFadeDuration(mEnterTime);
        //time for disappearing one background by ms
        animationDrawable.setExitFadeDuration(mExitTime);

        animationDrawable.start();
        // ATTENTION: This was auto-generated to handle app links.

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
     * handles received url link of Reset password to parse its data
     */
    private void handleIntent() {
        //receiving url
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        //checks if url has parameters in
        if(appLinkData!=null){
            //get last parameter in url
            String newToken = appLinkData.getLastPathSegment();
            //send the token for reset password page
            Intent i = new Intent(this, NewPassword.class);
            i.putExtra("token",newToken);
            startActivity(i);
        }
    }


    /**
     * opens welcome page as user
     * @param view holds clicked button
     */
    public void openListener(View view) {
        //opens welcome page as listener type
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Listener");
        startActivity(i);
    }

    /**
     * opens welcome page as artist
     * @param view holds clicked button
     */
    public void openArtist(View view) {
        //opens welcome page as artist type
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Artist");
        startActivity(i);
    }

    /**
     * handles back press in activity to exit app
     */
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    /**
     * handles generating unique hash key for app to be
     * used in firebase and facebook developer
     * @param context holds current activity context to be shown in
     * @auther Majid Laissi from stash overflow
     */
    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName()
                    , PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }
}
