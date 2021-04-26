package com.example.symphonia.Entities;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

/**
 * class that holds track info.
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class Track implements Serializable {
    private boolean isPlaying = false;
    /**
     * id of album to which track belongs
     */
    private  String mAlbumId;
    /**
     * url of image
     */
    private String imageUrl;
    /**
     * title of tack
     */
    private String mTitle;
    /**
     * Description of tack
     */
    private String mDescription;

    /**
     * playlist  name  of tack
     */
    private String playlistName;
    /**
     * album name of tack
     */
    private String mAlbum;

    /**
     * artist name of tack
     */
    private String mArtist;
    /**
     * Uri of tack
     */
    private Uri uri;

    /**
     * indicates if track is hidden
     */
    private boolean isHidden;

    /**
     * @return Uri of track
     */
    public Uri getUri() {
        return uri;
    }

    /**
     * indicates if track is favourite
     */
    private boolean isLiked;
    /**
     * image resource of track
     */
    private int mImageResources = -1;
    /**
     * Duration of Track
     */
    private int mDuration;

    /**
     * indicates whether track is locked or not
     */
    private boolean locked;


    private String playListId;

    public void setPlayListId(String playListId) {
        this.playListId = playListId;
    }

    public String getPlayListId() {
        return playListId;
    }

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mArtist         artist name
     * @param mAlbum          album name
     * @param uri             Uri of track
     */
    public Track(String mTitle,String id, String mArtist, String playlistName, String mAlbum, int mImageResources, Uri uri, boolean locked) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.playlistName = playlistName;
        this.mImageResources = mImageResources;
        this.uri = uri;
        this.locked = locked;
        this.id = id;
    }

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param mArtist         name of artist
     * @param playlistName    name of playlsit
     * @param mAlbum          name of album
     * @param mImageResources image resources of track
     */
    public Track(String mTitle, String mArtist, String playlistName, String mAlbum, int mImageResources) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.playlistName = playlistName;
        this.mImageResources = mImageResources;
    }

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mDescription    description of track
     * @param mURL            string value of the URL of track
     * @param isHidden        is track hidden
     * @param isLiked         is track favourite
     * @param mDuration       duration of track
     */
    public Track(String mTitle, String mDescription, String playlistName, String mURL, boolean isHidden, boolean isLiked, int mImageResources, int mDuration) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.playlistName = playlistName;
        this.isHidden = isHidden;
        this.isLiked = isLiked;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
    }

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mArtist         description of track
     * @param isLocked        is track locked
     * @param mDuration       duration of track
     */
    public Track(String mTitle, String mArtist, String playlistName, String id, boolean isLocked, int mImageResources, int mDuration, String imageUrl) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.locked = isLocked;
        this.id = id;
        this.imageUrl = imageUrl;
        this.playlistName = playlistName;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
    }
    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mArtist         description of track
     * @param isLocked        is track locked
     * @param mDuration       duration of track
     * @param albumId         id of album to which track belongs
     */
    public Track(String mTitle, String mArtist, String playlistName, String id, boolean isLocked, int mImageResources, int mDuration, String imageUrl,String albumId) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.locked = isLocked;
        this.id = id;
        this.imageUrl = imageUrl;
        this.playlistName = playlistName;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
        this.mAlbumId = albumId;
    }
    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mArtist         description of track
     * @param isLocked        is track locked
     * @param mDuration       duration of track
     * @param albumId         id of album to which track belongs
     * @param playListId
     */
    public Track(String mTitle, String mArtist, String playlistName, String id, boolean isLocked, int mImageResources, int mDuration, String imageUrl,String albumId, String playListId) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.locked = isLocked;
        this.id = id;
        this.imageUrl = imageUrl;
        this.playlistName = playlistName;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
        this.mAlbumId = albumId;
        this.playListId = playListId;
    }

    public Track(String id){
        this.id = id;
    }

    public Track(String id, String mTitle){
        this.id = id;
        this.mTitle = mTitle;
    }

    /**
     * getter fo album id of track
     *
     * @return url of album id  of track
     */
    public String getmAlbumId() {
        return mAlbumId;
    }

    /**
     * getter fo image url of track
     *
     * @return url of image of track
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * getter fo album of track
     *
     * @return album of track
     */
    public String getmAlbum() {
        return mAlbum;
    }

    /**
     * getter of duration of track
     *
     * @return duration of track
     */
    public int getmDuration() {
        return mDuration;
    }

    /**
     * getter for title
     *
     * @return title of track
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * getter for description
     *
     * @return description
     */
    public String getmDescription() {
        return mDescription;
    }

    /**
     * getter for playlist name
     *
     * @return playlist name
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * setter for locked
     *
     * @param mLocked if locked
     */
    public void setLocked(boolean mLocked) {
        this.locked = mLocked;
    }

    /**
     * getter if track is locked
     *
     * @return if track is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * getter for artist name
     *
     * @return artist name
     */
    public String getmArtist() {
        return mArtist;
    }

    /**
     * getter for if locked
     *
     * @return is hidden
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * setter for if track is hidden
     *
     * @param hidden if track is hidden
     */
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * getter for if track is favourite
     *
     * @return if is favourite
     */
    public boolean isLiked() {
        return isLiked;
    }

    /**
     * setter for if track is favourite
     *
     * @param liked if track is favourite
     */
    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    /**
     * getter for track image
     *
     * @return track image resources
     */
    public int getmImageResources() {
        return mImageResources;
    }

    /**
     * setter of track duration
     *
     * @param mDuration duration of track
     */
    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    /**
     * setter for track id
     *
     * @param id id of track
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * setter for uri of track
     *
     * @param uri uri of track
     */
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * id of track
     */
    private String id;

    /**
     * getter of track id
     *
     * @return track id
     */
    public String getId() {
        return id;
    }

    public void setImageResources(int no_image) {
        mImageResources = no_image;
    }

    public boolean isPlaying() {
    return isPlaying;
    }

    public void isPlaying(boolean b) {
        isPlaying = b;
    }

    private Bitmap imageBitmap;
    public void setImageBitmap(Bitmap bitmap) {
        imageBitmap = bitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
}
