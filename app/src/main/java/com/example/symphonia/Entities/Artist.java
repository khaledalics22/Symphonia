package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.io.Serializable;


/**
 * A class to hold the data of Artist object
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class Artist implements Serializable {
    /**
     * image of the artist
     */
    private int mArtistImage = -1;
    /**
     * holds image url of artist
     */
    private String mImageUrl = null;
    /**
     * name of the artist
     */
    private String mArtistName;
    /**
     * artist's special identifier
     */
    private String mArtistId;

    /**
     * constructor for the artist object in mock service case
     *
     * @param mArtistId   artist id
     * @param mImage      artist image
     * @param mArtistName artist name
     */
    public Artist(String mArtistId, int mImage, String mArtistName) {
        this.mArtistId = mArtistId;
        this.mArtistImage = mImage;
        this.mArtistName = mArtistName;
    }

    /**
     * constructor for the artist object in Rest Api case
     *
     * @param mArtistId   artist id
     * @param mImageUrl   artist image url
     * @param mArtistName artist name
     */
    public Artist(String mArtistId, String mImageUrl, String mArtistName) {
        this.mArtistId = mArtistId;
        this.mImageUrl = mImageUrl;
        this.mArtistName = mArtistName;
    }

    /**
     * @param mImageUrl image url
     */
    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    /**
     * @return image url
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * @return artist image
     */
    public int getImage() {
        return mArtistImage;
    }

    /**
     * @param mImage artist image
     */
    public void setImage(int mImage) {
        this.mArtistImage = mImage;
    }

    /**
     * @return artist name
     */
    public String getArtistName() {
        return mArtistName;
    }

    /**
     * @param mArtistName artist name
     */
    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    /**
     * @return artist id
     */
    public String getId() {
        return mArtistId;
    }
}
