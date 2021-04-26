package com.example.symphonia.Fragments_and_models.library;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CreatedPlaylistFragment extends Fragment implements RestApi.UpdatePlaylist, RestApi.updateTracksNames {

    private Playlist mPlaylist;
    private ImageView playlistImage;
    private ConstraintLayout viewContainer;
    private FrameLayout parent;
    private TextView ownerName;
    private TextView playlistName;
    private ServiceController mServiceController;
    private ProgressBar progressBar;
    private TextView playlistTracks;

    public CreatedPlaylistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_created_playlist, container, false);
        mServiceController = ServiceController.getInstance();

        parent = rootView.findViewById(R.id.parent);
        playlistImage = rootView.findViewById(R.id.image_playlist);
        playlistName = rootView.findViewById(R.id.text_playlist_name);
        ownerName = rootView.findViewById(R.id.text_owner_name);
        viewContainer = rootView.findViewById(R.id.container);
        progressBar = rootView.findViewById(R.id.progress_bar);
        ImageView backIcon = rootView.findViewById(R.id.arrow_back);
        playlistTracks = rootView.findViewById(R.id.text_playlist_tracks);

        parent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Bundle arguments = getArguments();
        assert arguments != null;
        String playlistId = arguments.getString("PLAYLIST_ID");
        mServiceController.getPlaylist(this, playlistId);

        mServiceController.getPlaylistTracks(this, playlistId);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
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
                            parent.setVisibility(View.INVISIBLE);
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
                            parent.setVisibility(View.INVISIBLE);
                        }
                    });
        } else {
            playlistImage.setImageBitmap(playlist.getmPlaylistImage());
            Drawable drawable = Utils.createAlbumBackground(getContext(), playlist.getmPlaylistImage());
            viewContainer.setBackground(drawable);
            progressBar.setVisibility(View.INVISIBLE);
            parent.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void updateTracks(ArrayList<Track> tracks) {
        StringBuilder names = new StringBuilder();

        for (int i = 0; i < tracks.size(); i++) {
            names.append(tracks.get(i).getmTitle());
            if(i != tracks.size() - 1)
                names.append(" • ");
        }

        playlistTracks.setText(names.toString());

    }
}