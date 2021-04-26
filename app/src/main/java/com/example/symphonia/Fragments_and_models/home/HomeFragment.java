package com.example.symphonia.Fragments_and_models.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Management.Notifications.NotificationsHistoryActivity;
import com.example.symphonia.Adapters.RvPlaylistsHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Context;
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * fragment that represent playlist to user
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class HomeFragment extends Fragment {


    RecyclerView.LayoutManager layoutManager;
    RvPlaylistsHomeAdapter rvPlaylistsHomeAdapter;
    private HomeViewModel homeViewModel;
    private RecyclerView rvRecentlyPlayed;
    private RecyclerView rvMadeForYou;
    private RecyclerView rvHeavyPlaylist;
    private RecyclerView rvPopularPlaylist;
    private RecyclerView rvBasedOnYourRecentlyPlayed;

    private View root;

    private ArrayList<Context> popularPlaylists;
    private ArrayList<Context> recentPlaylists;
    private ArrayList<Context> madeForYouPlaylists;
    private TextView playlistTitle;

    /**
     * random playlists
     */
    private ArrayList<Context> playlists;

    /**
     * inflate view of fragment
     *
     * @param inflater           inflate the fragment
     * @param container          viewgroup of the fragment
     * @param savedInstanceState saved data
     * @return fragment view
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        progressPar = root.findViewById(R.id.progress_bar_home);
        progressPar.setVisibility(View.VISIBLE);
        if (Constants.DEBUG_STATUS) {
            initViews();
            progressPar.setVisibility(View.GONE);
        } else {
            hideViews();
            loadAllPlaylists();
        }
        final ImageView ivSettings = root.findViewById(R.id.iv_setting_home);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        final ImageView notification_button = root.findViewById(R.id.notifications);
        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationsHistoryActivity.class);
                startActivity(intent);
            }
        });
        firstRand = true;

        final FrameLayout frameLayout = root.findViewById(R.id.frame_home_fragment);
        final ScrollView scrollView = root.findViewById(R.id.sv_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    // get distance from padding and normalize it
                    float alpha = (float) ((scrollView.getPaddingTop() - scrollY) / (scrollView.getPaddingTop() * 1.0));
                    frameLayout.setAlpha(1 - alpha);
                    if (scrollView.getPaddingTop() > scrollY)
                        ivSettings.setAlpha(alpha);
                    if (alpha <= 0) {
                        ivSettings.setVisibility(View.GONE);
                    } else
                        ivSettings.setVisibility(View.VISIBLE);
                }
            });
        }

        return root;

    }

    /**
     * this function hides views untill data is loaded
     */
    private void hideViews() {
        View view = root.findViewById(R.id.recently_played_playlist);
        rvRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.GONE);
        view = root.findViewById(R.id.your_heavy_rotation_playlist);
        rvHeavyPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.GONE);
        view = root.findViewById(R.id.based_on_your_recently_listening_playlist);
        rvBasedOnYourRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.GONE);
        view = root.findViewById(R.id.made_for_you_playlist);
        rvMadeForYou = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.GONE);


    }

  /*  private void showViews() {
        View view = root.findViewById(R.id.recently_played_playlist);
        rvRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        view = root.findViewById(R.id.your_heavy_rotation_playlist);
        rvHeavyPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        view = root.findViewById(R.id.based_on_your_recently_listening_playlist);
        rvBasedOnYourRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        view = root.findViewById(R.id.made_for_you_playlist);
        rvMadeForYou = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
    }
*/

    /**
     * this function initialize views for fragment
     */
    private void initViews() {

        //----------------------
        loadAllPlaylists();
        updateRecentPlaylists();
        //  updateMadeForYouPlaylists();
        //  updatePopularPlaylists();
        updateRandomPlaylists();
    }

    /**
     * this function hides progress bar
     */
    public void hideProgressBar() {
        progressPar.setVisibility(View.GONE);
    }

    /**
     * this view holds progress bar
     */
    private View progressPar;

    /**
     * load data from service
     */
    public void loadAllPlaylists() {

        //   popularPlaylists = SController.getPopularPlaylists(getContext(), Constants.currentToken);
        //   madeForYouPlaylists = SController.getMadeForYoutPlaylists(getContext(), Constants.currentToken);
        ServiceController SController = ServiceController.getInstance();
        playlists = SController.getRandomPlaylists(getContext(), this);
        SController.getRandomPlaylists(getContext(), this);
        recentPlaylists = SController.getRecentPlaylists(getContext(), this);

        //hide data of playlists commented
        View view = root.findViewById(R.id.popular_playlist_playlist);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.GONE);
        view = root.findViewById(R.id.made_for_you_playlist);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.GONE);

    }

    /**
     * this function updates ui of recent playlists
     */
    public void updateRecentPlaylists() {
        Log.e("recent", "update");
        if (!Constants.DEBUG_STATUS)
            recentPlaylists = Utils.LoadedPlaylists.recentPlaylists;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        View view = root.findViewById(R.id.recently_played_playlist);
        rvRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        playlistTitle.setText(R.string.recently_played);
        rvRecentlyPlayed.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), recentPlaylists);
        rvRecentlyPlayed.setAdapter(rvPlaylistsHomeAdapter);
    }

    /**
     * inidcates if first random playlist
     */
    private static
    boolean firstRand = true;
    /**
     * this function updates ui of random playlists
     */
    public void updateRandomPlaylists() {


        //heavy playlist
        if (!Constants.DEBUG_STATUS)
            playlists = Utils.LoadedPlaylists.randomPlaylists;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        View view = root.findViewById(R.id.your_heavy_rotation_playlist);
        if(firstRand) {
            rvHeavyPlaylist = view.findViewById(R.id.rv_sample_home);
            playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
            playlistTitle.setText(R.string.heavy_playlist);
            playlistTitle.setVisibility(View.VISIBLE);

            rvHeavyPlaylist.setLayoutManager(layoutManager);
            rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), playlists);
            rvHeavyPlaylist.setAdapter(rvPlaylistsHomeAdapter);
            firstRand = false;
            return;
        }
        // based on your recently played
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.based_on_your_recently_listening_playlist);
        rvBasedOnYourRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        playlistTitle.setText(R.string.based_on_your_recently_played);
        rvBasedOnYourRecentlyPlayed.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), playlists);
        rvBasedOnYourRecentlyPlayed.setAdapter(rvPlaylistsHomeAdapter);

    }

    /**
     * this function updates ui of popular playlists
     */
    public void updatePopularPlaylists() {
        //popular playlist
        if (!Constants.DEBUG_STATUS)
            popularPlaylists = Utils.LoadedPlaylists.popularPlaylists;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        View view = root.findViewById(R.id.popular_playlist_playlist);
        rvPopularPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        playlistTitle.setText(R.string.popular_playlist);
        rvPopularPlaylist.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), popularPlaylists);
        rvPopularPlaylist.setAdapter(rvPlaylistsHomeAdapter);

    }


    /**
     * this function updates ui of made-for-you playlists
     */
    public void updateMadeForYouPlaylists() {
        // made for you playlist;
        if (!Constants.DEBUG_STATUS)
            madeForYouPlaylists = Utils.LoadedPlaylists.madeForYouPlaylists;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        View view = root.findViewById(R.id.made_for_you_playlist);
        rvMadeForYou = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setVisibility(View.VISIBLE);
        playlistTitle.setText(R.string.made_for_you);
        rvMadeForYou.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), madeForYouPlaylists);
        rvMadeForYou.setAdapter(rvPlaylistsHomeAdapter);

    }

}