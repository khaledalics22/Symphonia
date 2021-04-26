package com.example.symphonia.Fragments_and_models.playlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * class that holds bottom sheet of credits in settings  of track.
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class BottomSheetDialogSettingsCredits extends BottomSheetDialogFragment {

    /**
     * position of track in playlist
     */
    int pos;


    /**
     * non empty  constructor
     *
     * @param pos position of track in playlist
     */
    public BottomSheetDialogSettingsCredits(int pos) {
        this.pos = pos;
    }


    /**
     * this function set data of fragment
     *
     * @param inflater           inflater of fragment
     * @param container          container of fragment
     * @param savedInstanceState instance saved of states
     * @return view of fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_credits, container, false);
        TextView artist = view.findViewById(R.id.settings_credit_artist);
        TextView author = view.findViewById(R.id.settings_credit_author);
        TextView producer = view.findViewById(R.id.settings_credit_producer);
        TextView report = view.findViewById(R.id.settings_credit_report);
        TextView trackTitle = view.findViewById(R.id.settings_credit_track_title);
        TextView source = view.findViewById(R.id.settings_credit_source);
        final ImageButton close = view.findViewById(R.id.close_credits);

        artist.setText(Utils.displayedContext.getTracks().get(pos).getmArtist());
        //TODO add author
        author.setText(Utils.displayedContext.getTracks().get(pos).getmArtist());
        //TODO add producer
        producer.setText(Utils.displayedContext.getTracks().get(pos).getmArtist());

        trackTitle.setText(Utils.displayedContext.getTracks().get(pos).getmTitle());
        //TODO add resource
        source.setText(Utils.displayedContext.getTracks().get(pos).getmArtist());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "" + Utils.displayedContext.getTracks().get(pos).getUri());
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    /**
     * this function is called when dialog is being created
     *
     * @param savedInstanceState instance of saved states
     * @return instance of dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;
    }


    /**
     * this function is called to customize  bottom sheet to be full screen
     *
     * @param bottomSheetDialog instance of bottom sheet dialog
     */
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.drawable.track_settings_background);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING)
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * this function is called to get height of device's screen
     *
     * @return height of screen
     */
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
