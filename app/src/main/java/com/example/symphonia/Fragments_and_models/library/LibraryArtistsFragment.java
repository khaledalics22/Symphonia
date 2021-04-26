package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.SnackbarHelper;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.RvListArtistsAdapter;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * responsible for all the interaction with artists
 * including adding, deleting, searching and showing
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryArtistsFragment extends Fragment implements RvListArtistsAdapter.ListItemLongClickListener
        , RvListArtistsAdapter.ListItemClickListener
        , BottomSheetDialogArtist.BottomSheetListener
        , RestApi.UpdateArtistsLibrary {
    /**
     *
     */
    private static final String ARTIST_ID = "ARTIST_ID";
    /**
     *
     */
    private static final String CLICKED_INDEX = "CLICKED_INDEX";
    /**
     *
     */
    private LinearLayoutManager layoutManager;

    /**
     * holds the user's following artists
     */
    private ArrayList<Artist> mFollowedArtists;
    /**
     * Instance for requesting from mock services and Api
     */
    private ServiceController mServiceController;
    /**
     * adapter to control the items in artists recyclerview
     */
    private RvListArtistsAdapter mAdapter;

    /**
     * recyclerview to show the artists
     */
    private RecyclerView mArtistsList;

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
    private Snackbar snack;
    Snackbar.Callback snackCallback;

    private Artist unFollowedArtist;

    /**
     * empty constructor
     */
    public LibraryArtistsFragment() {
        // Required empty public constructor
    }

    /**
     * create the artists recyclerview to show the list of artists
     *
     * @param inflater inflate fragment layout
     * @param container fragment viewgroup
     * @param savedInstanceState saved data from previous calls
     * @return fragment view
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_artists, container, false);

        mServiceController = ServiceController.getInstance();
/*
        mServiceController.getFollowedArtists(this, Constants.currentUser.getUserType(), 65535, null);
*/

        mFollowedArtists = new ArrayList<>();
        mArtistsList = rootView.findViewById(R.id.rv_artists);
        progressBar = rootView.findViewById(R.id.progress_bar);
        mArtistsList.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(getActivity());
        mArtistsList.setLayoutManager(layoutManager);
        mAdapter = new RvListArtistsAdapter(mFollowedArtists, this, this);
        mArtistsList.setAdapter(mAdapter);

/*        final LibraryFragment frag = ((LibraryFragment)this.getParentFragment());
        assert frag != null;
        frag.changeScrollFlags(true);*/
        /*NestedScrollView scroller = (NestedScrollView) rootView.findViewById(R.id.scrollView);

        if (scroller != null) {

            scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    assert frag != null;
                    if(scrollY == 120)
                        frag.changeScrollFlags(true);


                }
            });
        }*/

        mArtistsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mArtistsList.findChildViewUnder(currentX, currentY);
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

    /**
     * called when it opens or when closing another fragment of activity that leads to it
     * updates the artists list
     */
    @Override
    public void onResume() {
        super.onResume();
        mServiceController.getFollowedArtists(this, Constants.currentUser.getUserType(), 65535, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(snack != null) snack.dismiss();
    }

    /**
     * called when the user perform long click to an item
     *
     * @param clickedItemIndex the index of the clicked item in the recyclerview
     */
    @Override
    public void onListItemLongClick(int clickedItemIndex) {
        BottomSheetDialogArtist bottomSheet = new BottomSheetDialogArtist(this, mFollowedArtists.get(clickedItemIndex));
        Bundle arguments = new Bundle();
        arguments.putInt(CLICKED_INDEX , clickedItemIndex);
        bottomSheet.setArguments(arguments);
        bottomSheet.show(((MainActivity)getActivity()).getSupportFragmentManager(), bottomSheet.getTag());
    }

    /**
     * called when the user click on the following layout in bottomsheet
     *
     * @param id clicked artist id
     * @param clickedItemIndex clicked artist index in recyclerview
     */
    @Override
    public void onFollowingLayoutClicked(final String id, final int clickedItemIndex) {
        final Artist removedArtist = mFollowedArtists.get(clickedItemIndex);
        unFollowedArtist = removedArtist;

        mFollowedArtists.remove(clickedItemIndex);
        mAdapter.notifyItemRemoved(clickedItemIndex);

        snackCallback = new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                switch (event) {
                    case Snackbar.BaseCallback.DISMISS_EVENT_TIMEOUT:
                    case Snackbar.BaseCallback.DISMISS_EVENT_MANUAL:
                    case Snackbar.BaseCallback.DISMISS_EVENT_CONSECUTIVE:
                    case Snackbar.BaseCallback.DISMISS_EVENT_SWIPE:
                        mServiceController.unFollowArtistsOrUsers
                                (getContext(), "artist", new ArrayList<String>(Collections.singletonList(id)));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                unFollowedArtist = null;
                            }
                        }, 2000);
                        break;
                }
                snack = null;
            }
        };

        snack = Snackbar.make(mArtistsList, "OK, got it.", Snackbar.LENGTH_LONG).addCallback(snackCallback);

        snack.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!(mFollowedArtists.contains(removedArtist))){
                            mFollowedArtists.add(clickedItemIndex, removedArtist);
                            mAdapter.notifyItemInserted(clickedItemIndex);
                            unFollowedArtist = null;
/*
                            mArtistsList.smoothScrollBy(0, -(int)Utils.convertDpToPixel(80f, getContext()));
*/
                        }

                    }
                });


        SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
        snack.show();
    }

    @Override
    public void updateArtists(ArrayList<Artist> returnedArtists) {
        if(unFollowedArtist != null) returnedArtists.remove(unFollowedArtist);

        progressBar.setVisibility(View.GONE);
        mArtistsList.setVisibility(View.VISIBLE);

        int oldSize = mFollowedArtists.size();
        mFollowedArtists.clear();
        mFollowedArtists.addAll(returnedArtists);

/*        mAdapter.clear();
        mAdapter.addAll(mFollowedArtists);*/
        mAdapter.notifyDataSetChanged();

        if(oldSize < mFollowedArtists.size() && oldSize != 0)
            mArtistsList.scrollToPosition(mFollowedArtists.size());

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(snack != null){
            snack.removeCallback(snackCallback);
            mServiceController.unFollowArtistsOrUsers
                    (getContext(), "artist", new ArrayList<String>(Collections.singletonList(unFollowedArtist.getId())));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    unFollowedArtist = null;
                }
            }, 1000);
            snack = null;
        }
        if(clickedItemIndex == mFollowedArtists.size())
        {
            Intent addArtistsIntent = new Intent(getContext(), AddArtistsActivity.class);
            getActivity().startActivity(addArtistsIntent);
        }
        else {
            ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                    R.id.nav_host_fragment, new ArtistFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
