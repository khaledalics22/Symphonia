package com.example.symphonia.Activities.User_Interface;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.CustomSkipDialog;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.GridSpacingItemDecorationAdapter;
import com.example.symphonia.Adapters.RvGridArtistsAdapter;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity used in 2 cases:
 * When someone sign up
 * When a user click on add artist view in the library
 *
 * the user can add and search for any artist
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class AddArtistsActivity extends AppCompatActivity implements RvGridArtistsAdapter.ListItemClickListener
        , RestApi.UpdateAddArtists {

    /**
     * Static final variable for opened subActivity to get the result from it
     */
    private static final int STATIC_INTEGER_VALUE = 1;
    /**
     * Static final variable to get the result from the subActivity
     */
    private static final String SELECTED_ARTIST = "SelectedArtist";
    /**
     * Instance for requesting from mock services and Api
     */
    private ServiceController mServiceController;
    /**
     * A grid of the shown recommended artists for the current user to choose from
     */
    private ArrayList<Artist> mRecommendedArtists;

    /**
     * Array to store the artists that has been followed
     */
    private ArrayList<String> mClickedArtists;

    /**
     * Array to store all clicked before artists
     */
    private ArrayList<String> mClickedBeforeArtists;
    /**
     * An adapter to control the grid of artists
     */
    private RvGridArtistsAdapter mAdapter;
    /**
     * Recyclerview to show the grid of the recommended artists
     */
    private RecyclerView mArtistsList;
    /**
     * Item decoration attached to the recyclerview to control the spacing
     * between the items of the grid
     */
    private GridSpacingItemDecorationAdapter mItemDecoration;
    /**
     * Make a grid view containing 3 items in the row
     */
    private GridLayoutManager mLayoutManager;
    /**
     * A boolean to know if the user is new and just signed up
     * or an old user that comes to add artists
     */
    private boolean isNewUser = false;
    /**
     * Done button to be clicked when the user finish adding artists
     */
    private Button mButtonDone;

    /**
     * Keep the position of the clicked item from search activity
     */
    private int mSelectedArtistPosition = 0;

    /**
     * The current offset to send for requests
     * updated after each request
     */
    private int offset = 0;

    /**
     * The last clicked item index
     */
    private int clickedItemIndex = 0;

    private LinearLayout failState;


    private CoordinatorLayout coordinatorLayout;

    /**
     * initialize the ui of the activity
     *
     * @param savedInstanceState for saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check for internet connection
        if(!isOnline()) {
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(AddArtistsActivity.this, true);
            return;
        }

        setContentView(R.layout.activity_add_artists);
        mButtonDone = (Button) findViewById(R.id.button_done);
        Bundle b = getIntent().getExtras();

        coordinatorLayout = findViewById(R.id.view);
        failState = findViewById(R.id.fail_state);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        // determine whether the user is coming from signing up or from library
        try{
            assert b != null;
            String recvData = b.getString("newUser");
            isNewUser = true;
            mButtonDone.setVisibility(View.GONE);
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.choose_3_artists));

        } catch(Exception e) {
            isNewUser = false;
            mButtonDone.setVisibility(View.VISIBLE);
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_add_artists));
        }

        mServiceController = ServiceController.getInstance();

        // getting the recommended artists from the server and check that the user doesn't follow them
        mRecommendedArtists = new ArrayList<>();
        mClickedArtists = new ArrayList<>();
        mClickedBeforeArtists = new ArrayList<>();

        // preparing the recyclerview and its components
        mArtistsList = findViewById(R.id.rv_artists_grid);
        mItemDecoration = new GridSpacingItemDecorationAdapter(3,
                50,
                true);

        mArtistsList.addItemDecoration(mItemDecoration);
        mLayoutManager = new GridLayoutManager(this, 3);
        mArtistsList.setLayoutManager(mLayoutManager);
        mAdapter = new RvGridArtistsAdapter(mRecommendedArtists, mClickedArtists, this);
        mArtistsList.setAdapter(mAdapter);
        mArtistsList.setVisibility(View.INVISIBLE);

        ArrayList<Artist> returnedArtists = mServiceController.getRecommendedArtists
                (this, Constants.currentUser.getUserType(), offset, 8);

        if(!isOnline()) {
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(AddArtistsActivity.this, true);
        }


        mButtonDone = findViewById(R.id.button_done);
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNewUser){
                    Intent i = new Intent(AddArtistsActivity.this, MainActivity.class);
                    i.putExtra("newuser","true");
                    startActivity(i);
                    finish();
                }
                else
                    finish();
            }
        });

        View searchBar = findViewById(R.id.search_bar);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ArtistsSearchActivityIntent = new Intent(AddArtistsActivity.this,
                        ArtistsSearchActivity.class);

                startActivityForResult(ArtistsSearchActivityIntent, STATIC_INTEGER_VALUE);
            }
        });
    }

    /**
     * Get a result from its subActivity ArtistSearchActivity
     * which returns the selected artist from the search
     *
     * @param requestCode get the request from the right subActivity
     * @param resultCode check if the user send data or not
     * @param data the id of the clicked item in artist search activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATIC_INTEGER_VALUE) {
            if (resultCode == Activity.RESULT_OK) {

                // store the returned id and get the artist of this id
                assert data != null;
                Artist selectedArtist = (Artist) data.getSerializableExtra(SELECTED_ARTIST);

                // add the artist to recommended list if it's not in it
                int index = findArtist(selectedArtist.getId());
                if(index != -1){
                    mSelectedArtistPosition = index;
                }
                else {
                    mRecommendedArtists.add(0, selectedArtist);
                }
                clickedItemIndex = mSelectedArtistPosition;
                if(!mClickedArtists.contains(selectedArtist.getId())) {
                    mClickedArtists.add(selectedArtist.getId());
                    mServiceController.followArtistsOrUsers(this, "artist",
                            new ArrayList<String>(Collections.singletonList(selectedArtist.getId())));
/*
                    mAdapter.notifyItemChanged(mSelectedArtistPosition);
*/
                    if(mClickedArtists.size() >= 3) mButtonDone.setVisibility(View.VISIBLE);
                    if(!mClickedBeforeArtists.contains(selectedArtist.getId())){
                        mClickedBeforeArtists.add(selectedArtist.getId());
                        ArrayList<Artist> returnedArtists = mServiceController.getRecommendedArtists
                                (this, Constants.currentUser.getUserType(), offset, 6);
                        mLayoutManager.scrollToPositionWithOffset(clickedItemIndex, 0);
                    }
                } else {
                    mLayoutManager.scrollToPositionWithOffset(mSelectedArtistPosition, 0);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * handles the click of the items in the recyclerview
     * by following or unfollowing the clicked artists
     * and showing related artists
     *
     * @param itemView the clicked view layout
     * @param clickedItemIndex the index of the clicked view in the recyclerview
     */
    @Override
    public void onListItemClick(View itemView, int clickedItemIndex) {
        this.clickedItemIndex = clickedItemIndex;
        View checkImage = itemView.findViewById(R.id.image_check);
        Artist clickedArtist = mRecommendedArtists.get(clickedItemIndex);

        if(!mClickedArtists.contains(clickedArtist.getId())) {
            mServiceController.followArtistsOrUsers(this, "artist",
                            new ArrayList<String>(Collections.singletonList(clickedArtist.getId())));

            mClickedArtists.add(clickedArtist.getId());
            checkImage.setVisibility(View.VISIBLE);
            if(!mClickedBeforeArtists.contains(clickedArtist.getId())){
                mClickedBeforeArtists.add(clickedArtist.getId());
                ArrayList<Artist> returnedArtists = mServiceController.getRecommendedArtists
                        (this, Constants.currentUser.getUserType(), offset, 6);

            }
        }
        else {
            mClickedArtists.remove(clickedArtist.getId());

            mServiceController.unFollowArtistsOrUsers(this, "artist",
                    new ArrayList<String>(Collections.singletonList(clickedArtist.getId())));

            checkImage.setVisibility(View.GONE);
        }

        if(isNewUser){
            if(mClickedArtists.size() < 3)
                mButtonDone.setVisibility(View.GONE);
            else
                mButtonDone.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Listener for the More For You item click
     *
     * @param clickedItemIndex index of the clicked item
     */
    @Override
    public void onMoreForeYouClick(int clickedItemIndex) {
        this.clickedItemIndex = clickedItemIndex;
        ArrayList<Artist> returnedArtists = mServiceController.getRecommendedArtists
                (this, Constants.currentUser.getUserType(), offset, 6);
    }


    /**
     * handle the return from the activity in the two cases
     */
    @Override
    public void onBackPressed() {

        if(isNewUser){
            CustomSkipDialog custom_dialog = new CustomSkipDialog();
            custom_dialog.showDialog(this);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * @return if there is a connection to the internet or not
     */
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * Listener to update the data when sending a new request
     *
     * @param returnedArtists the returned artists from the request
     */
    @Override
    public void updateGetRecommendedArtists(ArrayList<Artist> returnedArtists) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        mArtistsList.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.VISIBLE);
        failState.setVisibility(View.GONE);
        boolean isMoreItem = clickedItemIndex == mRecommendedArtists.size();
        int change = (isMoreItem)? 0:1;

        if(returnedArtists.size() != 0) {
            offset += returnedArtists.size();
            if(!isMoreItem || clickedItemIndex == 0) mLayoutManager.scrollToPositionWithOffset(clickedItemIndex, 0);
            mRecommendedArtists.addAll(clickedItemIndex + change, returnedArtists);
            mAdapter.notifyItemRangeInserted(clickedItemIndex + change, returnedArtists.size());
        }
    }

    @Override
    public void updateFail(int offset, int limit) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.GONE);
        failState.setVisibility(View.VISIBLE);

        ArrayList<Artist> returnedArtists = mServiceController.getRecommendedArtists
                (this, Constants.currentUser.getUserType(), offset, limit);



    }

    int findArtist(String id){
        for (Artist artist : mRecommendedArtists){
            if(artist.getId().equals(id)) return mRecommendedArtists.indexOf(artist);
        }

        return -1;
    }
}