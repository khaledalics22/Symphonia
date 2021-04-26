package com.example.symphonia.Activities.User_Management.Redirect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.AdDialog;
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
public class PaymentActivity extends AppCompatActivity implements RestApi.updateUIPromotePremium
        ,RestApi.updateUICheckPremium {

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //get data from previous activity
        Bundle b = getIntent().getExtras();
        //checks if page is used to send or receive
        String received = b.getString("request");
        if(received != null) {
            //sends request to promote
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.promotePremium(this, null, Constants.currentToken);
        }else{
            //receives token from email url link
            handleIntent();
        }
    }

    /**
     * handles received url link of promote premium to parse its data
     */
    private void handleIntent() {
        //get url link
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        //checks if url is not null
        if(appLinkData!=null){
            //gets token from url
            String newToken = appLinkData.getLastPathSegment();

            //checks if token is valid using API request
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.checkPremiumToken(this,newToken);
        } else{
            //if link is not valid, then continue in app
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
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
     * holds promote premium API request success
     */
    @Override
    public void updateUIPromotePremiumSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        //sends user to main page
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    /**
     * holds promote premium API request failure
     */
    @Override
    public void updateUIPromotePremiumFailed() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        //returns user back to main page
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    /**
     * holds check token validity API request success
     */
    @Override
    public void updateUICheckPremiumSuccess() {
        Toast.makeText(this, "Congratulations, you're premium user now",Toast.LENGTH_LONG).show();
        //returns user back to main activity
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    /**
     * holds check token validity API request failure
     */
    @Override
    public void updateUICheckPremiumFailed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }
}