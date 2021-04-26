package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.asList;

public class BottomSheetDialogAlbumFragment extends BottomSheetDialogFragment implements RestApi.UpdateAlbum {
    /**
     * Final variable to get the album id
     */
    private static final String ALBUM_ID = "ALBUM_ID";
    /**
     * the first y when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstY = 0;
    /**
     * listener object from the bottomsheet interface
     */
    private BottomSheetListener mListener;

    /**
     * @param mListener object from the bottom sheet listener
     */
    public BottomSheetDialogAlbumFragment(BottomSheetListener mListener) {
        this.mListener = mListener;
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
        final BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_album_fragment_properties, null);
        bottomSheet.setContentView(view);

        final ServiceController serviceController = ServiceController.getInstance();

        Bundle arguments = getArguments();
        assert arguments != null;
        String albumId = arguments.getString(ALBUM_ID);

        final Album mAlbum = serviceController.getAlbum(this, albumId);

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

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

       /* TextView albumName = view.findViewById(R.id.text_album_name);
        albumName.setText(mAlbum.getAlbumName());
        ImageView albumImage = view.findViewById(R.id.image_album);
        albumImage.setImageBitmap(mAlbum.getAlbumImage());

        ConstraintLayout imageFrame = view.findViewById(R.id.image_frame);
        FrameLayout imageColor = view.findViewById(R.id.image_color);
        ImageView symphoniaImage = view.findViewById(R.id.image_symphonia);
        ImageView soundWave = view.findViewById(R.id.image_sound_wave);

        int dominantColor = Utils.getDominantColor(mAlbum.getAlbumImage());
        imageFrame.setBackgroundColor(dominantColor);
        if(!Utils.isColorDark(dominantColor)){
            symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
            soundWave.setColorFilter(Color.rgb(0, 0, 0));
        }
        else{
            symphoniaImage.setColorFilter(Color.rgb(255, 255, 255));
            soundWave.setColorFilter(Color.rgb(255, 255, 255));
        }

        Palette palette = Palette.from(mAlbum.getAlbumImage()).generate();
        imageColor.setBackgroundColor(palette.getVibrantColor(0));
*/

        final LinearLayout liked = view.findViewById(R.id.layout_liked);
        final LinearLayout viewArtist = view.findViewById(R.id.layout_view_artist);
        final LinearLayout likeAllSongs = view.findViewById(R.id.layout_like_all_songs);
        final LinearLayout share = view.findViewById(R.id.layout_share);

        ArrayList<LinearLayout> layouts = new ArrayList<>(asList(liked, viewArtist, likeAllSongs, share));

        for (LinearLayout layout: layouts) {
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
                            if((currentY >= 0) && (currentY <= v.getHeight())){
                                v.performClick();
                            }
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            if(Math.abs(currentY - firstY) > 3){
                                Utils.cancelTouchAnimation(v);
                            }
                            return true;

                    }

                    return false;
                }
            });
        }

        final TextView likeText = view.findViewById(R.id.text_like);
        final ImageView likeIcon = view.findViewById(R.id.like_icon);

        if(serviceController.checkUserSavedAlbums(getContext(),
                new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId()))).get(0)) {
            likeIcon.setImageResource(R.drawable.ic_favorite_green_24dp);
            likeText.setText(R.string.liked);
        }

        else {
            likeIcon.setImageResource(R.drawable.ic_favorite_border_transparent_white_24dp);
            likeText.setText(R.string.like);
        }

        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likeText.getText().equals(getString(R.string.liked))){
                    serviceController.removeAlbumsForUser(getContext(),
                            new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId())));
                    dismiss();
                } else {
                    serviceController.saveAlbumsForUser(getContext(),
                            new ArrayList<String>(Collections.singletonList(mAlbum.getAlbumId())));
                    dismiss();
                }
                mListener.onLikedLayoutClicked();
            }
        });

        return bottomSheet;
    }

    @Override
    public void updateAlbum(Album album) {

    }

    /**
     * Interface to the clicks of the bottomsheet layouts
     */
    public interface BottomSheetListener{
        void onLikedLayoutClicked();
    }

}
