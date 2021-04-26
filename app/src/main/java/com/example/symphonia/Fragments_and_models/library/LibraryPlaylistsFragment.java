package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.CreatePlaylistActivity;
import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvListPlaylistsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Context;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Helpers.SnackbarHelper;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * responsible for all the interaction with the playlists
 * including creating, deleting and showing
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryPlaylistsFragment extends Fragment implements RvListPlaylistsAdapter.ListItemClickListener,
        RvListPlaylistsAdapter.ListItemLongClickListener,
        RestApi.UpdatePlaylistsLibrary,
        RestApi.UpdateLikedSongsNumber{

    /**
     * final variable to send the clicked index to the bottomsheet
     */
    private static final String CLICKED_INDEX = "CLICKED_INDEX";

    /**
     * recyclerview to show the data of the albums
     */
    private RecyclerView mPlaylistsList;

    /**
     * adapter to control the items in recyclerview
     */
    private RvListPlaylistsAdapter mAdapter;
    /**
     * user's saved albums
     */
    private ArrayList<Playlist> mFollowedPlaylists;
    /**
     * instance to request data from mock services and API
     */
    private ServiceController mServiceController;
    /**
     * the last touched view
     */
    private View touchedView = null;
    /**
     * the first x when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstX = 0;
    /**
     * the first y when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstY = 0;

    private ProgressBar progressBar;
    private LinearLayout createPlaylist;
    private ConstraintLayout likedSongs;
    private TextView songsNumber;
    public LibraryPlaylistsFragment() {
        // Required empty public constructor
    }


    /**
     * Till now it just showing create playlist
     * and handle clicking on it
     *
     * @param inflater inflate fragment layout
     * @param container viewgroup of the fragment
     * @param savedInstanceState saved data from previous calls
     * @return fragment layout
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_playlists, container, false);
        mServiceController = ServiceController.getInstance();
        songsNumber = rootView.findViewById(R.id.text_songs_number);
        mServiceController.getNumberOfLikedSongs(this);
        createPlaylist = rootView.findViewById(R.id.create_playlist);
        likedSongs = rootView.findViewById(R.id.liked_songs);

        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).startCreatePlaylist();
            }
        });

        likedSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, new LikedSongsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        mPlaylistsList = rootView.findViewById(R.id.rv_playlists);
        progressBar = rootView.findViewById(R.id.progress_bar);
        mPlaylistsList.setVisibility(View.INVISIBLE);
        createPlaylist.setVisibility(View.INVISIBLE);
        likedSongs.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        mFollowedPlaylists = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mPlaylistsList.setLayoutManager(layoutManager);
        mAdapter = new RvListPlaylistsAdapter(mFollowedPlaylists, this, this);
        mPlaylistsList.setAdapter(mAdapter);

        mServiceController.getCurrentUserPlaylists(this,0, 65535);


        createPlaylist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.98f, 0.5f);
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

        likedSongs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentX = event.getX();
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.98f, 0.5f);
                        firstX = currentX;
                        firstY = currentY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if ((currentY >= 0) && (currentY <= v.getHeight())) {
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if ((currentX != firstX) || (Math.abs(currentY - firstY) > 3)) {
                            Utils.cancelTouchAnimation(v);
                        }
                        return true;

                }

                return false;
            }
        });


        mPlaylistsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mPlaylistsList.findChildViewUnder(currentX, currentY);
                if(touchedView != null && touchedView != newTouchedView){
                    Utils.cancelTouchAnimation(touchedView);
                }

                touchedView = newTouchedView;
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(touchedView, 0.98f, 0.5f);
                        firstX = currentX;
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(touchedView);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if((currentX != firstX) || (Math.abs(currentY - firstY) > 3)){
                            Utils.cancelTouchAnimation(touchedView);
                            Utils.cancelTouchAnimation(createPlaylist);
                            Utils.cancelTouchAnimation(likedSongs);
                        }
                        break;
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Playlist playlist = mFollowedPlaylists.get(clickedItemIndex);
        if(playlist.getTracks().size() == 0 && playlist.getOwnerName().equals(Constants.currentUser.getmName())){
            EmptyPlaylistFragment fragment = new EmptyPlaylistFragment();
            Bundle arguments = new Bundle();
            arguments.putString("PLAYLIST_ID" , playlist.getId());
            fragment.setArguments(arguments);
            ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                    R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            if(playlist.getOwnerName().equals(Constants.currentUser.getmName())){
                CreatedPlaylistFragment fragment = new CreatedPlaylistFragment();
                Bundle arguments = new Bundle();
                arguments.putString("PLAYLIST_ID" , playlist.getId());
                fragment.setArguments(arguments);
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit();

            } else {
                Utils.displayedContext = new Context();
                Utils.displayedContext.setContext(playlist);
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, new PlaylistFragment())
                        .addToBackStack(null)
                        .commit();
            }
        }

    }

    @Override
    public void onListItemLongClick(int clickedItemIndex) {
        if(mFollowedPlaylists.get(clickedItemIndex).getOwnerName().equals(Constants.currentUser.getmName())){
            Snackbar snack = Snackbar.make(mPlaylistsList, "Want to delete playlist?", Snackbar.LENGTH_LONG);

            snack.setAction("DELETE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mServiceController.deletePlaylist(getContext(), mFollowedPlaylists.get(clickedItemIndex).getId());
                    Snackbar snack1 = Snackbar.make(mPlaylistsList, "deleted successfully", Snackbar.LENGTH_SHORT);
                    SnackbarHelper.configSnackbar(getContext(), snack1, R.drawable.custom_snackbar, Color.BLACK);
                    snack1.show();
                    mFollowedPlaylists.remove(clickedItemIndex);
                    mAdapter.notifyItemRemoved(clickedItemIndex);
                }
            });

            SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
            snack.show();
        }
    }

    @Override
    public void updatePlaylists(ArrayList<Playlist> returnedPlaylists) {
        mPlaylistsList.setVisibility(View.VISIBLE);
        createPlaylist.setVisibility(View.VISIBLE);
        likedSongs.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        mFollowedPlaylists.clear();
        mFollowedPlaylists.addAll(returnedPlaylists);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateNumber(int noOfTracks) {
        songsNumber.setText(MessageFormat.format("{0} songs", noOfTracks));
    }
}
