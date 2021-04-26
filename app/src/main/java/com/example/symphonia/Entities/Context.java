package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * class that holds playlist info.
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class Context {
    private String id;
    /**
     * playlist name
     */
    private String mContextTitle;
    /**
     * playlist description
     */
    private String mContextDescription;

    private String mOwnerName;

    private String contextType;
    /**
     * playlist image
     */
    private Bitmap mContextImage;
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
     * owner id
     */
    private String ownerId;

    /**
     * empty constructor
     */
    public Context() {
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle       playlist title
     * @param mContextDescription playlist description
     * @param mContextImage       playlist image
     * @param tracks              playlist tracks
     */
    public Context(String mContextTitle, String id, String mContextDescription, Bitmap mContextImage, ArrayList<Track> tracks) {
        this.mContextTitle = mContextTitle;
        this.mContextDescription = mContextDescription;
        this.mContextImage = mContextImage;
        this.tracks = tracks;
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle playlist title
     * @param mContextImage playlist image
     * @param id            id of track
     * @param mOwnerName    owner name
     */
    public Context(String id, String mContextTitle, Bitmap mContextImage, String mOwnerName) {
        this.id = id;
        this.mContextTitle = mContextTitle;
        this.mContextImage = mContextImage;
        this.mOwnerName = mOwnerName;
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle playlist title
     * @param imageUrl      playlist image
     * @param id            id of track
     * @param mOwnerName    owner name
     */
    public Context(String id, String mContextTitle, String imageUrl, String mOwnerName) {
        this.id = id;
        this.mContextTitle = mContextTitle;
        this.imageUrl = imageUrl;
        this.mOwnerName = mOwnerName;
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle playlist title
     * @param id            id of track
     * @param mOwnerName    owner name
     */
    public Context(String id, String mContextTitle, String mOwnerName) {
        this.id = id;
        this.mContextTitle = mContextTitle;
        this.imageUrl = imageUrl;
        this.mOwnerName = mOwnerName;
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle       playlist title
     * @param mContextDescription playlist description
     * @param imageUrl            playlist image's url
     * @param tracks              playlist tracks
     * @param tracksURL           tracks url
     * @param contextType         context type
     * @param id                  id of context
     */
    public Context(String mContextTitle, String id, String mContextDescription, String imageUrl, ArrayList<Track> tracks, String tracksURL, String contextType, String owneerId) {
        this.mContextTitle = mContextTitle;
        this.id = id;
        this.mContextDescription = mContextDescription;
        this.imageUrl = imageUrl;
        this.tracks = tracks;
        this.tracksURL = tracksURL;
        this.contextType = contextType;
        this.ownerId = owneerId;
    }

    /**
     * setter of track liked
     *
     * @param pos   position of liked track
     * @param liked is liked
     */
    public void setTrackLiked(int pos, boolean liked) {
        tracks.get(pos).setLiked(liked);
    }

    /**
     * set track hidden
     *
     * @param pos    positio of track
     * @param hidden is hidden
     */
    public void setTrackHidden(int pos, boolean hidden) {
        tracks.get(pos).setHidden(hidden);
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle       playlist title
     * @param id                  id of context
     * @param type                type of context
     * @param mContextDescription description of context
     * @param mContextImage       context image
     * @param tracks              tracks of context
     */
    public Context(String mContextTitle, String id, String mContextDescription, Bitmap mContextImage, ArrayList<Track> tracks, String type) {
        this.mContextTitle = mContextTitle;
        this.mContextDescription = mContextDescription;
        this.mContextImage = mContextImage;
        this.tracks = tracks;
        contextType = type;
        this.id = id;
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
     * @param mContextTitle       playlist title
     * @param mContextDescription playlist description
     * @param imageUrl            playlist image's url
     * @param tracks              playlist tracks
     * @param tracksURL           tracks url
     */
    public Context(String mContextTitle, String id, String mContextDescription, String imageUrl, ArrayList<Track> tracks, String tracksURL) {
        this.mContextTitle = mContextTitle;
        this.id = id;
        this.mContextDescription = mContextDescription;
        this.imageUrl = imageUrl;
        this.tracks = tracks;
        this.tracksURL = tracksURL;
    }

    /**
     * non empty constructor
     *
     * @param mContextTitle       playlist title
     * @param mContextDescription playlist description
     * @param imageUrl            playlist image's url
     * @param tracks              playlist tracks
     * @param tracksURL           tracks url
     * @param contextType         context type
     * @param id                  id of context
     */
    public Context(String mContextTitle, String id, String mContextDescription, String imageUrl, ArrayList<Track> tracks, String tracksURL, String contextType) {
        this.mContextTitle = mContextTitle;
        this.id = id;
        this.mContextDescription = mContextDescription;
        this.imageUrl = imageUrl;
        this.tracks = tracks;
        this.tracksURL = tracksURL;
        this.contextType = contextType;
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
    public String getmContextTitle() {
        return mContextTitle;
    }

    /**
     * getter for playlist description
     *
     * @return playlist description
     */
    public String getmContextDescription() {
        return mContextDescription;
    }

    /**
     * getter of owner name
     *
     * @return owner name
     */
    public String getmOwnerName() {
        return mOwnerName;
    }

    /**
     * setter of context type
     *
     * @param contextType context typ e
     */
    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    /**
     * getter of context type
     *
     * @return context type
     */
    public String getContextType() {
        return contextType;
    }

    /**
     * getter for playlist image
     *
     * @return Bitmap of playlist image
     */
    public Bitmap getmContextImage() {
        return mContextImage;
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

    /**
     * setter for owner name
     *
     * @param mOwnerName owner name
     */
    public void setmOwnerName(String mOwnerName) {
        this.mOwnerName = mOwnerName;
    }

    /**
     * setter for playlist image
     *
     * @param bitmap image of playlist
     */
    public void setPlaylistImage(Bitmap bitmap) {
        mContextImage = bitmap;
    }

    /**
     * set playlist to context
     *
     * @param playlist playlist of context
     */
    public void setContext(Playlist playlist) {
        setmOwnerName(playlist.getOwnerName());
        setPlaylistImage(playlist.getmPlaylistImage());
        setTracks(playlist.getTracks());
        setId(playlist.getId());
        setImageUrl(playlist.getImageUrl());
        setmContextDescription(playlist.getmPlaylistDescription());
        setTracksURL(playlist.getTracksURL());
        setmContextTitle(playlist.getmPlaylistTitle());
    }


    /**
     * settero for id
     *
     * @param id id of context
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * setter for context title
     *
     * @param mContextTitle context title
     */
    public void setmContextTitle(String mContextTitle) {
        this.mContextTitle = mContextTitle;
    }

    /**
     * setter for context decsription
     *
     * @param mContextDescription context description
     */
    public void setmContextDescription(String mContextDescription) {
        this.mContextDescription = mContextDescription;
    }

    public void setmContextImage(Bitmap mContextImage) {
        this.mContextImage = mContextImage;
    }

    /**
     * set track urls
     *
     * @param tracksURL track urls
     */
    public void setTracksURL(String tracksURL) {
        this.tracksURL = tracksURL;
    }

    /**
     * setter of image url
     *
     * @param imageUrl image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * getter  of owner id
     *
     * @return
     */
    public String getOwnerId() {
        return this.ownerId;
    }

}
