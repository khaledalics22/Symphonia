package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * class that holds playlist info.
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class Playlist {
    private String id;
    /**
     * playlist name
     */
    private String mPlaylistTitle;
    /**
     * playlist description
     */
    private String mPlaylistDescription;

    private String mOwnerName;

    /**
     * playlist image
     */
    private Bitmap mPlaylistImage;
    /**
     * ArrayList of playlist tracks
     */
    private ArrayList<Track> tracks;

    /**
     * link of playlist tracks
     */
    private String tracksURL;

    /**
     * url of image of playlist
     */
    private String imageUrl;

    /**
     * non empty constructor
     *
     * @param mPlaylistTitle       playlist title
     * @param mPlaylistDescription playlist description
     * @param mPlaylistImage       playlist image
     * @param tracks               playlist tracks
     */
    public Playlist(String mPlaylistTitle, String mPlaylistDescription, Bitmap mPlaylistImage, ArrayList<Track> tracks) {
        this.mPlaylistTitle = mPlaylistTitle;
        this.mPlaylistDescription = mPlaylistDescription;
        this.mPlaylistImage = mPlaylistImage;
        this.tracks = tracks;
    }

    public Playlist(String id, String mPlaylistTitle, Bitmap mPlaylistImage, String mOwnerName){
        this.id = id;
        this.mPlaylistTitle = mPlaylistTitle;
        this.mPlaylistImage = mPlaylistImage;
        this.mOwnerName = mOwnerName;
    }

    public Playlist(String id, String mPlaylistTitle, String imageUrl, String mOwnerName){
        this.id = id;
        this.mPlaylistTitle = mPlaylistTitle;
        this.imageUrl = imageUrl;
        this.mOwnerName = mOwnerName;
    }

    public Playlist(String id, String mPlaylistTitle, String imageUrl, String mOwnerName, ArrayList<Track> tracks){
        this.id = id;
        this.mPlaylistTitle = mPlaylistTitle;
        this.imageUrl = imageUrl;
        this.mOwnerName = mOwnerName;
        this.tracks = tracks;
    }

    public Playlist(String id, String mPlaylistTitle, String mOwnerName){
        this.id = id;
        this.mPlaylistTitle = mPlaylistTitle;
        this.imageUrl = imageUrl;
        this.mOwnerName = mOwnerName;
    }

    /**
     * getter of playlist image's url
     *
     * @return url of image of playlist
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * getter of playlist id
     *
     * @return playlist id
     */
    public String getId() {
        return id;
    }

    /**
     * non empty constructor
     *
     * @param mPlaylistTitle       playlist title
     * @param mPlaylistDescription playlist description
     * @param imageUrl             playlist image's url
     * @param tracks               playlist tracks
     * @param tracksURL            tracks url
     */
    public Playlist(String mPlaylistTitle, String id, String mPlaylistDescription, String imageUrl, ArrayList<Track> tracks, String tracksURL) {
        this.mPlaylistTitle = mPlaylistTitle;
        this.id = id;
        this.mPlaylistDescription = mPlaylistDescription;
        this.imageUrl = imageUrl;
        this.tracks = tracks;
        this.tracksURL = tracksURL;
    }

    /**
     * getter for tracks url
     *
     * @return tracks url
     */

    public String getTracksURL() {
        return tracksURL;
    }

    /**
     * getter for playlist title
     *
     * @return playlist title
     */
    public String getmPlaylistTitle() {
        return mPlaylistTitle;
    }

    /**
     * getter for playlist description
     *
     * @return playlist description
     */
    public String getmPlaylistDescription() {
        return mPlaylistDescription;
    }

    /**
     * getter for playlist image
     *
     * @return Bitmap of playlist image
     */
    public Bitmap getmPlaylistImage() {
        return mPlaylistImage;
    }

    /**
     * getter for playlist tracks
     *
     * @return ArrayList of playlist tracks
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * setter for playlist tracks
     *
     * @param tracks playlsit tracks
     */
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setmOwnerName(String mOwnerName) {
        this.mOwnerName = mOwnerName;
    }
    /**
     * setter for playlist image
     *
     * @param bitmap image of playlist
     */
    public void setPlaylistImage(Bitmap bitmap) {
        mPlaylistImage = bitmap;
    }

    public String getOwnerName() {
        return mOwnerName;
    }
}
