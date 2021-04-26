package com.example.symphonia.Service;

import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;

import java.util.ArrayList;

public interface updateUiPlaylists {
    void getCategoriesSuccess();

    void getCurrPlayingTrackSuccess(String id);

    void getCurrPlayingTrackFailed();

    void updateUiNoTracks(PlaylistFragment playlistFragment);

    void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment, ArrayList<Track> tracksList);

    void updateUicheckSaved(PlaylistFragment playlistFragment);

    void updateUiGetPopularPlaylistsSuccess();

    void updateUiGetPopularPlaylistsFail();

    void updateUiGetRandomPlaylistsSuccess(HomeFragment homeFragment);

    void updateUiGetRandomPlaylistsFail();

    void updateUiGetRecentPlaylistsSuccess(HomeFragment homeFragment);

    void updateUiGetRecentPlaylistsFail();

    void updateUiGetMadeForYouPlaylistsSuccess();

    void updateUiGetMadeForYouPlaylistsFail();

    void updateUiPlayTrack();

    void getTrackSuccess();

    void updateUiGetQueue();

    void getTrackOfQueue();
}
