package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Class that contain albums' information
 *
 * @author islamahmed1092
 * @version 1.0
 * @since 22-3-2020
 */
public class Album {
    /**
     * holds album id
     */
    private String mAlbumId;
    /**
     * holds album type
     */
    private String mAlbumType;
    /**
     * holds array of artists who made this albums
     */
    private ArrayList<Artist> mAlbumArtists;
    /**
     * holds array of copyrights for album
     */
    private ArrayList<Copyright> mCopyrights;
    /**
     * holds album image
     */
    private int mAlbumImage = -1;
    /**
     * holds album name
     */
    private String mAlbumName;
    /**
     * holds image url
     */
    private String mImageUrl;
    /**
     * holds releasing date of album
     */
    private String mReleaseDate;
    /**
     * holds array of tracks in album
     */
    private ArrayList<Track> mAlbumTracks;

    /**
     * Constructor for album to initialize attributes
     *
     * @param mAlbumId      album id
     * @param mAlbumType    album type
     * @param mAlbumArtists array of album's artists
     * @param mCopyrights   array of copyrights of album
     * @param mAlbumImage   image of album
     * @param mAlbumName    name of album
     * @param mReleaseDate  release date
     * @param mAlbumTracks  array of tracks in album
     */
    public Album(String mAlbumId, String mAlbumType, ArrayList<Artist> mAlbumArtists,
                 ArrayList<Copyright> mCopyrights, int mAlbumImage, String mAlbumName,
                 String mReleaseDate, ArrayList<Track> mAlbumTracks) {

        this.mAlbumId = mAlbumId;
        this.mAlbumType = mAlbumType;
        this.mAlbumArtists = mAlbumArtists;
        this.mCopyrights = mCopyrights;
        this.mAlbumImage = mAlbumImage;
        this.mAlbumName = mAlbumName;
        this.mReleaseDate = mReleaseDate;
        this.mAlbumTracks = mAlbumTracks;
    }

    /**
     * Constructor for album to initialize attributes
     *
     * @param mAlbumId album id
     * @param mAlbumType album type
     * @param mAlbumArtists array of album's artists
     * @param mCopyrights array of copyrights of album
     * @param mImageUrl image url
     * @param mAlbumName album name
     * @param mReleaseDate release date
     * @param mAlbumTracks list of albums tracks
     */
    public Album(String mAlbumId, String mAlbumType, ArrayList<Artist> mAlbumArtists,
                 ArrayList<Copyright> mCopyrights, String mImageUrl, String mAlbumName,
                 String mReleaseDate, ArrayList<Track> mAlbumTracks) {

        this.mAlbumId = mAlbumId;
        this.mAlbumType = mAlbumType;
        this.mAlbumArtists = mAlbumArtists;
        this.mCopyrights = mCopyrights;
        this.mImageUrl = mImageUrl;
        this.mAlbumName = mAlbumName;
        this.mReleaseDate = mReleaseDate;
        this.mAlbumTracks = mAlbumTracks;
    }

    /**
     * Constructor for album to initialize attributes
     *
     * @param mAlbumId album id
     * @param mAlbumArtists album artists
     * @param mImageUrl image url
     * @param mAlbumName album name
     */
    public Album(String mAlbumId, ArrayList<Artist> mAlbumArtists, String mImageUrl, String mAlbumName){
        this.mAlbumId = mAlbumId;
        this.mAlbumArtists = mAlbumArtists;
        this.mImageUrl = mImageUrl;
        this.mAlbumName = mAlbumName;

    }

    /**
     * sets album context
     * @param currContext holds context
     */
    public void setAlbum(Context currContext) {
    }

    /**
     * getter for album id
     *
     * @return returns album id
     */
    public String getAlbumId() {
        return mAlbumId;
    }

    /**
     * setter for album id
     *
     * @param mAlbumId album id
     */
    public void setAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    /**
     * getter for album type
     *
     * @return returns album type
     */
    public String getAlbumType() {
        return mAlbumType;
    }

    /**
     * setter for album type
     *
     * @param mAlbumType album type
     */
    public void setAlbumType(String mAlbumType) {
        this.mAlbumType = mAlbumType;
    }

    /**
     * getter for array of album's artists
     *
     * @return returns array of album's artists
     */
    public ArrayList<Artist> getAlbumArtists() {
        return mAlbumArtists;
    }

    /**
     * setter for array of album's artists
     *
     * @param mAlbumArtists array of album's artists
     */
    public void setAlbumArtists(ArrayList<Artist> mAlbumArtists) {
        this.mAlbumArtists = mAlbumArtists;
    }

    /**
     * getter for array of copyrights of album
     *
     * @return return array of copyrights of album
     */
    public ArrayList<Copyright> getCopyrights() {
        return mCopyrights;
    }

    /**
     * setter for array of copyrights of album
     *
     * @param mCopyrights array of copyrights of album
     */
    public void setCopyrights(ArrayList<Copyright> mCopyrights) {
        this.mCopyrights = mCopyrights;
    }

    /**
     * getter for album image
     *
     * @return return album image
     */
    public int getAlbumImage() {
        return mAlbumImage;
    }

    /**
     * setter for album image
     *
     * @param mAlbumImage album image
     */
    public void setAlbumImage(int mAlbumImage) {
        this.mAlbumImage = mAlbumImage;
    }

    /**
     * getter for album name
     *
     * @return returns album name
     */
    public String getAlbumName() {
        return mAlbumName;
    }

    /**
     * setter for album name
     *
     * @param mAlbumName album name
     */
    public void setAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    /**
     * getter for album release date
     *
     * @return return album release date
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * setter for album release date
     *
     * @param mReleaseDate album release date
     */
    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /**
     * getter for album's array of tracks
     *
     * @return returns album's array of tracks
     */
    public ArrayList<Track> getAlbumTracks() {
        return mAlbumTracks;
    }

    /**
     * setter for album's array of tracks
     *
     * @param mAlbumTracks album's array of tracks
     */
    public void setAlbumTracks(ArrayList<Track> mAlbumTracks) {
        this.mAlbumTracks = mAlbumTracks;
    }

    /**
     * getter for album image url
     * @return returns album image url
     */
    public String getImageUrl() {
        return mImageUrl;
    }
}
