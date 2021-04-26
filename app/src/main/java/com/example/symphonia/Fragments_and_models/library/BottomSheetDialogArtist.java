
package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class BottomSheetDialogArtist extends BottomSheetDialogFragment {

    /**
     * final variable to get the artist id
     */
    private static final String ARTIST_ID = "ARTIST_ID";
    /**
     * index of the clicked item
     */
    private static final String CLICKED_INDEX = "CLICKED_INDEX";

    /**
     * listener object from the bottomsheet
     */
    private BottomSheetListener mListener;

    /**
     * the first y when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstY = 0;

    private Artist mArtist;

    /**
     * @param mListener object from the bottom sheet listener
     */
    public BottomSheetDialogArtist(BottomSheetListener mListener, Artist mArtist) {
        this.mListener = mListener;
        this.mArtist = mArtist;
    }
    /**
     * creating the bottomsheet dialog and initializing the views
     *
     * @param savedInstanceState saved data from the previous calls
     * @return the view of the dialog
     */
    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_artist_properties, null);
        bottomSheet.setContentView(view);

        ServiceController serviceController = ServiceController.getInstance();

        Bundle arguments = getArguments();
        assert arguments != null;
        final int clickedIndex = arguments.getInt(CLICKED_INDEX);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        bottomSheet.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(R.drawable.background_bottom_sheet);


        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        //this prevents dragging behavior
        View content = bottomSheet.getWindow().findViewById(R.id.design_bottom_sheet);
        content.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT));
        ((CoordinatorLayout.LayoutParams) content.getLayoutParams()).setBehavior(null);

        TextView artistName = view.findViewById(R.id.text_artist_name);
        artistName.setText(mArtist.getArtistName());


        final ConstraintLayout imageFrame = view.findViewById(R.id.image_frame);
        final ImageView symphoniaImage = view.findViewById(R.id.image_symphonia);
        final ImageView soundWave = view.findViewById(R.id.image_sound_wave);
        final ImageView artistImage = view.findViewById(R.id.image_artist);


        if(mArtist.getImage() == -1){
            Picasso.get()
                    .load(mArtist.getImageUrl())
                    .placeholder(R.drawable.placeholder_artist)
                    .into(artistImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            BitmapDrawable drawable = (BitmapDrawable) artistImage.getDrawable();
                            int dominantColor = Utils.getDominantColor(drawable.getBitmap());
                            imageFrame.setBackgroundColor(dominantColor);
                            if (!Utils.isColorDark(dominantColor)) {
                                symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
                                soundWave.setColorFilter(Color.rgb(0, 0, 0));
                            } else {
                                symphoniaImage.setColorFilter(Color.rgb(255, 255, 255));
                                soundWave.setColorFilter(Color.rgb(255, 255, 255));
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
        } else {
            Picasso.get().load(mArtist.getImage()).placeholder(R.drawable.placeholder_artist).into(artistImage);
            int dominantColor = Utils.getDominantColor(Utils.convertToBitmap(mArtist.getImage()));
            imageFrame.setBackgroundColor(dominantColor);
            if (!Utils.isColorDark(dominantColor)) {
                symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
                soundWave.setColorFilter(Color.rgb(0, 0, 0));
            } else {
                symphoniaImage.setColorFilter(Color.rgb(255, 255, 255));
                soundWave.setColorFilter(Color.rgb(255, 255, 255));
            }
        }


        final LinearLayout following = view.findViewById(R.id.layout_following);
        final LinearLayout removeArtist = view.findViewById(R.id.layout_remove_artist);
        final LinearLayout viewArtist = view.findViewById(R.id.layout_view_artist);
        final LinearLayout share = view.findViewById(R.id.layout_share);
        final LinearLayout homeScreen = view.findViewById(R.id.layout_home_screen);

        ArrayList<LinearLayout> layouts = new ArrayList<>(asList(following, removeArtist, viewArtist, share, homeScreen));

        for (LinearLayout layout : layouts) {
            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View v, MotionEvent event) {
                    float currentY = event.getY();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Utils.startTouchAnimation(v, 0.99f, 0.5f);
                            firstY = currentY;
                            return true;
                        case MotionEvent.ACTION_UP:
                            Utils.cancelTouchAnimation(v);
                            if ((currentY >= 0) && (currentY <= v.getHeight())) {
                                v.performClick();
                            }
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            if (Math.abs(currentY - firstY) > 3) {
                                Utils.cancelTouchAnimation(v);
                            }
                            return true;

                    }

                    return false;
                }
            });
        }


        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onFollowingLayoutClicked(mArtist.getId(), clickedIndex);
            }
        });


        return bottomSheet;
    }

    /**
     * Interface to the clicks of the bottomsheet layouts
     */
    public interface BottomSheetListener {
        void onFollowingLayoutClicked(String id, int clickedItemIndex);
    }

}
