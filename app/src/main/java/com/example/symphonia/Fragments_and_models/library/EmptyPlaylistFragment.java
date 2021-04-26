package com.example.symphonia.Fragments_and_models.library;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Activities.User_Interface.AddSongsActivity;
import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyPlaylistFragment extends Fragment implements RestApi.UpdatePlaylist {

    private Playlist mPlaylist;
    private ImageView playlistImage;
    private CoordinatorLayout viewContainer;
    private TextView ownerName;
    private TextView playlistName;
    private ServiceController mServiceController;
    private ProgressBar progressBar;
    private String playlistId;
    boolean paused = false;

    public EmptyPlaylistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_empty_playlist, container, false);
        mServiceController = ServiceController.getInstance();

        playlistImage = rootView.findViewById(R.id.image_playlist);
        playlistName = rootView.findViewById(R.id.text_playlist_name);
        ownerName = rootView.findViewById(R.id.text_owner_name);
        viewContainer = rootView.findViewById(R.id.container);
        progressBar = rootView.findViewById(R.id.progress_bar);
        ImageView backIcon = rootView.findViewById(R.id.back_icon);

        viewContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Bundle arguments = getArguments();
        assert arguments != null;
        playlistId = arguments.getString("PLAYLIST_ID");
        mServiceController.getPlaylist(this, playlistId);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Button addSongs = rootView.findViewById(R.id.button_add_songs);
        addSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSongsIntent = new Intent(getContext(), AddSongsActivity.class);
                addSongsIntent.putExtra("PLAYLIST_ID", playlistId);
                addSongsIntent.putExtra("PLAYLIST_NAME", playlistName.getText());
                getActivity().startActivity(addSongsIntent);
            }
        });

        return rootView;
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        if(playlist.getTracks().size() != 0){
            CreatedPlaylistFragment fragment = new CreatedPlaylistFragment();
            Bundle arguments = new Bundle();
            arguments.putString("PLAYLIST_ID" , playlist.getId());
            fragment.setArguments(arguments);
            ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                    R.id.nav_host_fragment, fragment)
                    .commit();
            return;
        }
        playlistName.setText(playlist.getmPlaylistTitle());
        ownerName.setText(String.format("by %s", playlist.getOwnerName()));
        if(!Constants.DEBUG_STATUS){
            Picasso.get()
                .load(playlist.getImageUrl())
                .placeholder(R.drawable.placeholder_playlist)
                .into(playlistImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Drawable drawable = Utils.createAlbumBackground(getContext(), ((BitmapDrawable) playlistImage.getDrawable()).getBitmap());
                        viewContainer.setBackground(drawable);
                        progressBar.setVisibility(View.INVISIBLE);
                        viewContainer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Drawable drawable = Utils.createAlbumBackground(getContext(), ((BitmapDrawable) playlistImage.getDrawable()).getBitmap());
                        viewContainer.setBackground(drawable);
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        int margin = (int)Utils.convertDpToPixel(32f, getContext());
                        lp.setMargins(margin, margin, margin, margin);
                        playlistImage.setLayoutParams(lp);
                        progressBar.setVisibility(View.INVISIBLE);
                        viewContainer.setVisibility(View.VISIBLE);
                    }
                });
        } else {
            playlistImage.setImageBitmap(playlist.getmPlaylistImage());
            Drawable drawable = Utils.createAlbumBackground(getContext(), playlist.getmPlaylistImage());
            viewContainer.setBackground(drawable);
            progressBar.setVisibility(View.INVISIBLE);
            viewContainer.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(paused)
            mServiceController.getPlaylist(this, playlistId);
    }

    @Override
    public void onPause() {
        super.onPause();
        paused = true;
    }
}
