package com.example.symphonia.Fragments_and_models.library;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvListAlbumsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Helpers.SnackbarHelper;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * responsible for all the interaction with albums
 * including deleting, searching and showing
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryAlbumsFragment extends Fragment implements RvListAlbumsAdapter.ListItemClickListener,
        RvListAlbumsAdapter.ListItemLongClickListener, BottomSheetDialogAlbum.BottomSheetListener ,
        RestApi.UpdateAlbumsLibrary {


    /**
     * final variable to send the clicked index to the bottomsheet
     */
    private static final String CLICKED_INDEX = "CLICKED_INDEX";

    /**
     * recyclerview to show the data of the albums
     */
    private RecyclerView mAlbumsList;

    /**
     * Text view that is shown when there is no data
     */
    private TextView albumsEmptyState;
    /**
     * adapter to control the items in recyclerview
     */
    private RvListAlbumsAdapter mAdapter;
    /**
     * user's saved albums
     */
    private ArrayList<Album> mLikedAlbums;
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


    /**
     * empty constructor
     */
    public LibraryAlbumsFragment() {
        // Required empty public constructor
    }


    /**
     * create the albums recyclerview to show
     * the list of saved albums
     *
     * @param inflater inflate fragment layout
     * @param container fragment viewgroup
     * @param savedInstanceState saved data from previous calls
     * @return fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_albums, container, false);
        mServiceController = ServiceController.getInstance();
        albumsEmptyState = rootView.findViewById(R.id.text_albums_empty_state);
        mAlbumsList = rootView.findViewById(R.id.rv_albums);
        progressBar = rootView.findViewById(R.id.progress_bar);
        mAlbumsList.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        mLikedAlbums = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAlbumsList.setLayoutManager(layoutManager);
        mAdapter = new RvListAlbumsAdapter(new ArrayList<Album>(), this, this);
        mAlbumsList.setAdapter(mAdapter);

        mServiceController.getUserSavedAlbums(this,0, 65535);




        mAlbumsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mAlbumsList.findChildViewUnder(currentX, currentY);
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
     * handles the clicking on the recyclerview item
     * replaces the current fragment with clicked album framgnet
     *
     * @param clickedItemIndex index of the clicked view
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        AlbumFragment fragment = new AlbumFragment();
        Bundle arguments = new Bundle();
        arguments.putString("ALBUM_ID" , mLikedAlbums.get(clickedItemIndex).getAlbumId());
        fragment.setArguments(arguments);
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();

    }

    /**
     * called when user perform a long click to an item
     *
     * @param clickedItemIndex the index of the clicked item
     */
    @Override
    public void onListItemLongClick(int clickedItemIndex) {
        BottomSheetDialogAlbum bottomSheet = new BottomSheetDialogAlbum(this, mLikedAlbums.get(clickedItemIndex));
        Bundle arguments = new Bundle();
        arguments.putInt(CLICKED_INDEX , clickedItemIndex);
        bottomSheet.setArguments(arguments);
        bottomSheet.show(((MainActivity)getActivity()).getSupportFragmentManager(), bottomSheet.getTag());
    }

    /**
     * called when user clicks on the liked layout in bottomsheet
     *
     * @param id id of the album
     * @param clickedItemIndex index of the album in recyclerview
     */
    @Override
    public void onLikedLayoutClicked(final String id, final int clickedItemIndex) {

        mServiceController.removeAlbumsForUser(getContext(),
                new ArrayList<String>(Collections.singletonList(id)));

        mLikedAlbums.remove(clickedItemIndex);
        mAdapter.notifyItemRemoved(clickedItemIndex);
        if(mLikedAlbums.size() == 0) albumsEmptyState.setVisibility(View.VISIBLE);

        Snackbar snack = Snackbar.make(mAlbumsList, R.string.remove_album_snackbar_text, Snackbar.LENGTH_LONG);
        SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
        snack.show();
    }

    /**
     * called when user clicks on the go to full album in bottomsheet
     *
     * @param mAlbum
     */
    @Override
    public void onGoFullAlbumLayoutClicked(Album mAlbum) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle arguments = new Bundle();
        arguments.putString("ALBUM_ID" , mAlbum.getAlbumId());
        fragment.setArguments(arguments);
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void updateAlbums(ArrayList<Album> returnedAlbums) {
        mAlbumsList.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        mLikedAlbums.clear();
        mLikedAlbums.addAll(returnedAlbums);

        mAdapter.clear();
        mAdapter.addAll(mLikedAlbums);
        mAdapter.notifyDataSetChanged();


        if(mLikedAlbums.size() != 0)
            albumsEmptyState.setVisibility(View.GONE);
        else
            albumsEmptyState.setVisibility(View.VISIBLE);
    }
}
