package com.example.symphonia.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.R;

/**
 * Class that handles showing custom dialog if user is signing up
 * and tried to skip from choosing suggested artists
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class CustomSkipDialog {
    /**
     * function that shows and initializes dialog
     *
     * @param activity activity that calls this dialog
     */
    public void showDialog(final Activity activity) {

        //sets dialog activity to be shown in
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //sets background with layout file
        dialog.setContentView(R.layout.dialog_custom_skip);

        //gets text views and buttons by ids in layout file to be shown
        TextView text_view_header = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text_view_paragraph = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button btn_skip = (Button) dialog.findViewById(R.id.btn_skip);
        Button btn_continue = (Button) dialog.findViewById(R.id.btn_cont);

        //set listeners for buttons

        //if button continue is pressed, then close the dialog
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //if button skip is pressed, then send user to main activity
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MainActivity.class);
                i.putExtra("newuser","true");
                activity.startActivity(i);
            }
        });
        dialog.show();
    }
}