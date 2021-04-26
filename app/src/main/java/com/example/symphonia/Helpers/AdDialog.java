package com.example.symphonia.Helpers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.R;

/**
 * Class that handles showing custom Ad for user
 *
 * @author Hossam Alaa
 * @since 11-4-2020
 * @version 1.0
 */
public class AdDialog extends AppCompatActivity {

    /**
     * holds delta x for moving Ad
     */
    private float dX;
    /**
     * holds delta y for moving Ad
     */
    private float dY;

    /**
     * holds initial x-position for Ad
     */
    private float InitX;
    /**
     * holds initial y-position for Ad
     */
    private float InitY;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window theme
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //set layout for activity
        setContentView(R.layout.dialog_custom_ad);

        //set window parameters
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        //prevent finish Ad on click outside
        setFinishOnTouchOutside(false);

        //get main view by id and store initial position
        View view = findViewById(R.id.main_view);
        InitX = view.getX();
        InitY = view.getY();

        //get buttons ids
        final Button btn_promote = (Button) findViewById(R.id.promote_premium);
        final Button btn_dismiss = (Button) findViewById(R.id.dismiss_ad);

        //set listeners for dragging Ad
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent event) {
                switch (event.getAction()) {

                    //if pressed, then store delta (x,y)
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;

                    //if moving, then add previous delta (x,y)
                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        //hides dismiss button
                        btn_dismiss.setVisibility(View.INVISIBLE);
                        break;

                    //if left finger, then return back to initial position
                    case MotionEvent.ACTION_UP:
                        view.animate()
                            .translationX(0)
                            .translationY(0)
                            .setDuration(200);
                        btn_dismiss.setVisibility(View.VISIBLE);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });



        //sets listener for button promote
        btn_promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdDialog.this, MainActivity.class);
                i.putExtra("go_to","premium" );
                startActivity(i);
            }
        });

        //sets listener for button promote
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //counter for dismish button to be enabled
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                btn_dismiss.setText(getResources().getString(R.string.dismiss)
                        +"("+ l/1000 + ")");
            }

            //if counter is finished, then make button enabled and remove counter text
            @Override
            public void onFinish() {
                btn_dismiss.setText(getResources().getString(R.string.dismiss));
                btn_dismiss.setEnabled(true);
                btn_dismiss.setBackgroundResource(R.drawable.btn_transparent);
            }
        }.start();

    }

    /**
     * makes Ad non-responsive for back press
     */
    @Override
    public void onBackPressed(){ }

}
