package com.example.symphonia.Fragments_and_models.playlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

/**
 * class that holds bottom sheet of settings of track.
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class BottomSheetDialogSettings extends BottomSheetDialogFragment {

    /**
     *  track in playlist
     */
    Track track;
    /**
     * listener of sheet
     */
    BottomSheetListener listener;

    /**
     * non empty  constructor
     *
     * @param context context of current activity
     * @param track     position of track in playlist
     */
    public BottomSheetDialogSettings(Track track, Context context) {
        this.track = track;
        listener = (BottomSheetListener) context;
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
        if (container != null)
            container.setBackground(getContext().getResources().getDrawable(R.drawable.track_settings_background));
        View view = inflater.inflate(R.layout.setting_track, container, false);
        final TextView like = view.findViewById(R.id.tv_track_liked_settings);
        final TextView hide = view.findViewById(R.id.tv_track_hide_settings);
        TextView share = view.findViewById(R.id.tv_track_share_settings);
        TextView report = view.findViewById(R.id.tv_track_report_settings);
        TextView credits = view.findViewById(R.id.tv_track_show_credits_settings);
        TextView showArtist = view.findViewById(R.id.tv_track_view_artist_settings);

        showArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onViewArtistClicked(Utils.getPosInPlaying(track.getId()));
            }
        });
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCreditsClicked(Utils.getPosInPlaying(track.getId()));
                dismiss();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReportClicked(Utils.getPosInPlaying(track.getId()));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShareClicked(Utils.getPosInPlaying(track.getId()));
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!track.isHidden() && !track.isLocked()) {
                    hide.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.ic_do_not_disturb_on_red_24dp), null, null, null);
                } else if (!track.isLocked()) {
                    hide.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.ic_do_not_disturb_on_black_24dp), null, null, null);
                }
                listener.onHideClicked(Utils.getPosInPlaying(track.getId()));
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!track.isLiked() && !track.isLocked()) {
                    like.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.ic_favorite_black_24dp), null, null, null);
                } else if (!track.isLocked()) {
                    like.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.ic_favorite_border_black_24dp), null, null, null);
                }
                listener.onLikeClicked(Utils.getPosInPlaying(track.getId()));
            }
        });

        if (track.isLiked()) {
            like.setText(R.string.liked);
            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_black_24dp, 0, 0, 0);
        } else {
            like.setText(R.string.like);
            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border_black_24dp, 0, 0, 0);

        }
        if (track.isHidden()) {
            hide.setText(R.string.hidden);
            hide.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_do_not_disturb_on_red_24dp, 0, 0, 0);

        } else {
            hide.setText(R.string.hide_this_song);
            hide.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_do_not_disturb_on_black_24dp, 0, 0, 0);

        }
        ImageView trackImage = view.findViewById(R.id.iv_track_image_settings);
        TextView trackTitle = view.findViewById(R.id.tv_track_title_settings);
        TextView trackArtist = view.findViewById(R.id.tv_track_artist_settings);
        Picasso.get()
                .load(track.getImageUrl())
                .centerCrop()
                .fit()
                .into(trackImage);
        trackTitle.setText(track.getmTitle());
        trackArtist.setText(track.getmDescription());

        return view;
    }

    /**
     * this interface contains listeners of fragment
     */
    public interface BottomSheetListener {
        void onLikeClicked(int pos);

        void onHideClicked(int pos);

        void onReportClicked(int pos);

        void onShareClicked(int pos);

        void onCreditsClicked(int pos);

        void onViewArtistClicked(int pos);


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
