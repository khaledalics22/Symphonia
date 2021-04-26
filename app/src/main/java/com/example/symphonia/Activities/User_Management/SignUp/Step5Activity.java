package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

/**
 * Activity that handles sign up for last step when user enters his name with some validations
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class Step5Activity extends AppCompatActivity implements RestApi.updateUiSignUp {

    /**
     * holds overriding interface of success request
     */
    @Override
    public void updateUiSignUpSuccess() {
        //create mail if request is success
        createMail();
    }

    /**
     * holds overriding interface of failed request
     */
    @Override
    public void updateUiSignUpFailed() {
        //Don't create mail if request is failed and show button to try again
        showButton();
    }

    /**
     * sign up button
     */
    private Button btn_signUp;
    /**
     * progress bar which is shown when loading sign up
     */
    private ProgressBar progressBar;
    /**
     * Holds EditText for user's name input
     */
    private EditText edit_text_name;
    /**
     * holds type of user
     */
    private String mUser;
    /**
     * holds user's password
     */
    private String mPassword;
    /**
     * holds user's email
     */
    private String mEmail;
    /**
     * holds user's date of birth
     */
    private String mDOB;
    /**
     * holds user's gender
     */
    private String mGender;
    /**
     * holds user's name
     */
    private String mName;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up5);

        //makes all anchors in text views clickable
        TextView text_view_link1 = findViewById(R.id.t1);
        text_view_link1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text_view_link2 = findViewById(R.id.t2);
        text_view_link2.setMovementMethod(LinkMovementMethod.getInstance());

        //set progress bar with id in layout file
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btn_signUp = (Button) findViewById(R.id.next5);

        //receives user's data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mPassword = b.getString("password");
        mEmail = b.getString("email");
        mDOB = b.getString("DOB");
        mGender = b.getString("gender");

        //gets input text of user name by id
        edit_text_name = findViewById(R.id.name_input);

        //sets input text with extracted name from email entered, then enables (next button)
        edit_text_name.setText(Utils.getNameFromEmail(mEmail));
        if(edit_text_name.getText().toString().length()>=3)
            enableButton();
        else
            lockButton();

        //set listeners for name input
        edit_text_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //checks if length is more than 1 char, then enables sign up button
                if (s.length() >= 3) enableButton();
                else lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        //set listeners for keyboard (Done key)
        edit_text_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //checks validity of input, then calls open next method
                    if(edit_text_name.getText().toString().length()>=3) openNext(v);
                }
                return false;
            }
        });
    }

    /**
     * opens next page of sign up
     */
    public void openNext(View view) {

        //checks if not online
        if(!isOnline()){
            //shows offline dialog to prevent user from continuing sign up
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        //makes button is invisible and progress is visible
        btn_signUp.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        //creates object of service controller class
        ServiceController serviceController = ServiceController.getInstance();

        //stores user' name and type
        mName = edit_text_name.getText().toString();
        boolean userType;
        userType = mUser.equals("Listener");

//        Toast.makeText(this,mName,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,mEmail,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,mPassword,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,mDOB,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,mGender,Toast.LENGTH_SHORT).show();
//        if(userType)
//            Toast.makeText(this,"user",Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"artist",Toast.LENGTH_SHORT).show();

        //calls sign up function to make a new account
        serviceController.signUp(this, userType, mEmail, mPassword, mDOB, mGender, mName);

        //if current mode is Mock, then call functions synchronously
        if(Constants.DEBUG_STATUS) createMail();
    }

    /**
     * enables (Next button) to go for next step of sign up
     */
    public void enableButton() {
        //gets button by id, then makes it enabled
        btn_signUp.setEnabled(true);
        btn_signUp.setBackgroundResource(R.drawable.btn_curved_white);
    }

    /**
     * disables (Next button) to prevent user to go next step
     */
    public void lockButton() {
        //gets button by id, then makes it disabled
        btn_signUp.setEnabled(false);
        btn_signUp.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    /**
     * Checks if internet is connected
     * @return returns true if internet is connected
     */
    public boolean isOnline() {
        //accesses connection service of mobile
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            //return true if internet is connected
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * makes sign up button visible
     */
    public void showButton(){
        progressBar.setVisibility(View.GONE);
        btn_signUp.setVisibility(View.VISIBLE);
    }

    /**
     * creates mail and sends user to next step
     */
    public void createMail(){

        // Creates object of SharedPreferences.
        SharedPreferences sharedPref= getSharedPreferences("LoginPref", 0);
        //new Editor
        SharedPreferences.Editor editor= sharedPref.edit();
        //set user's params if REST API mode
        if(!(Constants.DEBUG_STATUS)){
            editor.putString("token", Constants.currentToken);
            editor.putString("name", Constants.currentUser.getmName());
            editor.putString("email", Constants.currentUser.getmEmail());
            editor.putString("id",Constants.currentUser.get_id());
            editor.putBoolean("type", Constants.currentUser.isListenerType());
            editor.putBoolean("premium", Constants.currentUser.isPremuim());
            editor.putString("image",Constants.currentUser.getImageUrl());
         } else{
            //if Mock mode, don't save all params
            editor.putString("token", Constants.currentToken);
            editor.putString("email", Constants.currentUser.getmEmail());
            editor.putBoolean("type", Constants.currentUser.isListenerType());
        }
        //commits edits
        editor.apply();

//        btn_signUp.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);

        //sends to AddArtist activity to suggest artists for user
        Intent i = new Intent(this, AddArtistsActivity.class);
        i.putExtra("newUser", "true");
        startActivity(i);
    }

}
