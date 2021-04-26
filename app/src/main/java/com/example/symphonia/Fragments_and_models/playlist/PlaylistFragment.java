package com.example.symphonia.Fragments_and_models.playlist;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvTracksHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * fragment that represent playlist tracks to user
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class PlaylistFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RvTracksHomeAdapter rvTracksHomeAdapter;
    private AppBarLayout appBarLayout;
    private ImageView playlistImage;
    private TextView playlistTitle;
    private TextView madeForUser;
    private RecyclerView rvTracks;
    private Button playBtn;
    private View progressPar;

    /**
     * this is required empty constructor
     */
    public PlaylistFragment() {
        // Required empty public constructor

    }

    /**
     * hide progress bar when data feched
     */
    public void hideProgressBar() {
        progressPar.setVisibility(View.GONE);
    }

    /**
     * background layout
     */
    RelativeLayout backgroundLayout;

    /**
     * inflate view of fragment
     *
     * @param inflater           inflate the fragment
     * @param container          viewgroup of the fragment
     * @param savedInstanceState saved data
     * @return fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        rvTracks = view.findViewById(R.id.rv_playlist_tracks);
        madeForUser = view.findViewById(R.id.tv_made_for_you_tracks);
        playlistTitle = view.findViewById(R.id.tv_playlist_title_tracks);
        playlistImage = view.findViewById(R.id.tv_playlist_image_tracks);
        appBarLayout = view.findViewById(R.id.appBarLayout2);
        playBtn = view.findViewById(R.id.btn_play);
        backgroundLayout = view.findViewById(R.id.rl_background_frag_playlist);
        progressPar = view.findViewById(R.id.progress_bar);
        progressPar.setVisibility(View.VISIBLE);
        appBarLayout.setScrollbarFadingEnabled(true);

        playlistTitle.setText(Utils.displayedContext.getmContextTitle());
        madeForUser.setText(R.string.made_for_you_by_spotify);

        // transition drawable controls the animation ov changing background


        final FrameLayout frameLayout = view.findViewById(R.id.frame_playlist_fragment);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) (
                        (appBarLayout.getScrollBarSize() - verticalOffset / 40) / (appBarLayout.getScrollBarSize() * 1.0));
                //          frameLayout.setAlpha(1-alpha);

                if (alpha < 1.2) {
                    playlistImage.setScaleX(1 / (alpha));
                    playlistImage.setScaleY(1 / (alpha));
                }
                frameLayout.setAlpha((long) 2 * (alpha - 1));
            }
        });
      /*  rvTracks.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Utils.CurrTrackInfo.TrackPosInPlaylist != -1) {
                    if (rvTracks.getChildAt(
                            Utils.CurrTrackInfo.TrackPosInPlaylist) == null) return;
                    TextView title = (TextView) rvTracks.getChildAt(
                            Utils.CurrTrackInfo.TrackPosInPlaylist).findViewById(R.id.tv_track_title_item);
                    if (((String) title.getText()).matches(Utils.CurrTrackInfo.track.getmTitle())&&
                            Utils.CurrPlaylist.playlist.getmPlaylistTitle().matches(Utils.CurrTrackInfo.track.getPlaylistName()))
                        title.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
                }
                // unregister listener (this is important)
                rvTracks.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });*/
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < Utils.displayedContext.getTracks().size(); i++)
                    if (!Utils.displayedContext.getTracks().get(i).isLocked() && !Utils.displayedContext.getTracks().get(i).isHidden()) {
                        Utils.currTrack = Utils.displayedContext.getTracks().get(i);
                        Utils.currContextType = "playlist";
                        Utils.currContextId = Utils.displayedContext.getId();
                        break;
                    }
                Utils.playingContext = Utils.displayedContext;
                changeSelected();
                ((MainActivity) getActivity()).startTrack();
            }
        });

        return view;
    }

    /**
     * back ground drawable
     */
    Drawable background;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ServiceController serviceController = ServiceController.getInstance();
        serviceController.getTracksOfPlaylist(getContext(), Utils.displayedContext.getId(), this);
        RestApi restApi = new RestApi();
        if (Utils.displayedContext.getOwnerId() != null && !Utils.displayedContext.getOwnerId().matches(""))
            restApi.getCurrentUserProfile(getContext(), Utils.displayedContext.getOwnerId(), this);
        else
            updateMadeByView("Symphonia");
    }

    /**
     * this function is called to update recycler view of tracks
     */
    public void updateTracks() {
        layoutManager = new LinearLayoutManager(getContext());
        rvTracks.setHasFixedSize(true);
        rvTracks.setLayoutManager(layoutManager);
        rvTracksHomeAdapter = new RvTracksHomeAdapter(getContext(), Utils.displayedContext.getTracks());
        rvTracks.setAdapter(rvTracksHomeAdapter);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Utils.displayedContext.setPlaylistImage(bitmap);
                background = Utils.createBackground(getContext(), Utils.displayedContext.getmContextImage());
                playlistImage.setImageBitmap(Utils.displayedContext.getmContextImage());
                TransitionDrawable td = new TransitionDrawable(
                        new Drawable[]{getResources().getDrawable(R.drawable.background), background});
                backgroundLayout.setBackground(td);
                td.startTransition(500);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        if (!Constants.DEBUG_STATUS)
            Picasso.get()
                    .load(Utils.displayedContext.getImageUrl())
                    .into(target);

        if (Constants.DEBUG_STATUS) {
            playlistImage.setImageBitmap(Utils.displayedContext.getmContextImage());
            background = Utils.createBackground(getContext(), Utils.displayedContext.getmContextImage());
            TransitionDrawable td = new TransitionDrawable(
                    new Drawable[]{getResources().getDrawable(R.drawable.background), background});
            backgroundLayout.setBackground(td);
            td.startTransition(500);
        }


    }

    /**
     * this function change item image view favourite according to its data
     *
     * @param id      id of item
     * @param isLiked if item favourite
     */
    public void changeLikedItemAtPos(String id, boolean isLiked) {
        rvTracksHomeAdapter.selectLiked(id, isLiked);
        rvTracksHomeAdapter.notifyDataSetChanged();
    }

    /**
     * change selected track
     */
    public void changeSelected() {
        //   rvTracksHomeAdapter.selectPlaying(Utils.currTrack.getId());
        rvTracksHomeAdapter.changeTracks(Utils.displayedContext.getTracks());
        rvTracksHomeAdapter.notifyDataSetChanged();
    }

    /**
     * change hidden track
     *
     * @param pos      positon of track
     * @param isHidden is hidden
     */
    public void changeHidden(int pos, boolean isHidden) {
        rvTracksHomeAdapter.selectHidden(pos, isHidden);
        rvTracksHomeAdapter.notifyDataSetChanged();
    }

    /**
     * update made by text view
     */
    public void updateMadeByView(String name) {
        madeForUser.setText(getString(R.string.made_for_you) + " by " + name);
    }

}
