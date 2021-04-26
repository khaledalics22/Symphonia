package com.example.symphonia.Activities.User_Interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.symphonia.Adapters.RvListAddSongsAdapter;
import com.example.symphonia.Adapters.RvListPlaylistsAdapter;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.SnackbarHelper;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AddSongsActivity extends AppCompatActivity implements RvListAddSongsAdapter.AddClickListener,
        RvListAddSongsAdapter.ListItemClickListener,
        RvListAddSongsAdapter.ListItemLongClickListener,
        RestApi.UpdateExtraSongs{

    private ServiceController mServiceController;
    private RecyclerView mSongsList;
    private RvListAddSongsAdapter mAdapter;
    private ArrayList<Track> mSuggestedSongs;
    private String playlistId;
    private String playlistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_songs);

        Intent intent = getIntent();
        playlistId = intent.getExtras().getString("PLAYLIST_ID");
        playlistName = intent.getExtras().getString("PLAYLIST_NAME");

        mServiceController = ServiceController.getInstance();
        mServiceController.getRecommendedTracks(this,0, 13);

        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSuggestedSongs = new ArrayList<>();
        mSongsList = findViewById(R.id.rv_suggested_songs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mSongsList.setLayoutManager(layoutManager);
        mAdapter = new RvListAddSongsAdapter(mSuggestedSongs, this, this, this);
        mSongsList.setAdapter(mAdapter);

    }

    @Override
    public void onAddClick(int clickedItemIndex) {
        mServiceController.addTrackToPlaylist(AddSongsActivity.this, playlistId, mSuggestedSongs.get(clickedItemIndex).getId());
        mSuggestedSongs.remove(clickedItemIndex);
        mAdapter.notifyItemRemoved(clickedItemIndex);
        Snackbar snack = Snackbar.make(mSongsList, "added to " + playlistName, Snackbar.LENGTH_LONG);
        SnackbarHelper.configSnackbar(AddSongsActivity.this, snack, R.drawable.custom_snackbar, Color.BLACK);
        snack.show();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    @Override
    public void onListItemLongClick(int clickedItemIndex) {

    }

    @Override
    public void updateExtra(ArrayList<Track> returnedTracks) {
        mSuggestedSongs.clear();
        mSuggestedSongs.addAll(returnedTracks);
        mAdapter.notifyDataSetChanged();
    }
}