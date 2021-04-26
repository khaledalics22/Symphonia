package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Class that contain user's information
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 22-3-2020
 */
public class User {

    /**
     * phone number of user
     */
    private String mPhone;
    /**
     * image url of user
     */
    private String mImageUrl;
    /**
     * holds user type
     */
    private String mUserType;
    /**
     * holds user id
     */
    private String _id;
    /**
     * holds email
     */
    private String mEmail;
    /**
     * holds user password, just for mock service
     */
    private String mPassword;
    /**
     * holds type of user, true for listener and false for artist
     */
    private boolean mType;
    /**
     * user's profile image
     */
    private Bitmap mUserImage;
    /**
     * user's name
     */
    private String mName;
    /**
     * user's birth day
     */
    private String mDOB;
    /**
     * user's gender
     */
    private String mGender;
    /**
     * if user is premium or not
     */
    private boolean mIsPremuim;
    /**
     * holds number of followers
     */
    private int mNumOfFollowers;
    /**
     * holds number of following
     */
    private int mNumOfFollowings;
    /**
     * holds array of following users
     */
    private ArrayList<User> mFollowings;
    /**
     * holds array of followers users
     */
    private ArrayList<User> mFollowers;
    /**
     * holds array of favourite play lists
     */
    private ArrayList<Playlist> mFavPlaylists;
    /**
     * holds array of playlist that made for user
     */
    private ArrayList<Playlist> mMadePlaylists;
    /**
     * holds array of following artists
     */
    private ArrayList<Artist> mFollowingArtists;
    /**
     * holds array of saved albums
     */
    private ArrayList<Album> mSavedAlbums;
    /**
     * holds list of liked songs
     */
    private ArrayList<Track> mLikedSongs;

    /**
     * Default constructor for user
     */
    public User() {
    }

    /**
     * constructor of user, initializes parameters
     *
     * @param mName name of user
     * @param mType type of user
     */
    public User(String mName, boolean mType) {
        this.mName = mName;
        this.mType = mType;
    }

    /**
     * override constructor for user
     *
     * @param email    email of user
     * @param password password of user
     * @param mType    type of user
     */
    public User(String email, String password, boolean mType) {
        this.mEmail = email;
        this.mPassword = password;
        this.mType = mType;
    }

    /**
     * override constructor for user
     *
     * @param mEmail            email of user
     * @param mType             type of user
     * @param mUserImage        image of user
     * @param mName             name of user
     * @param mDOB              date of birth of user
     * @param mGender           gender of user
     * @param mIsPremuim        if user is premium
     * @param mNumOfFollowers   number of followers of user
     * @param mNumOfFollowings  number of following of user
     * @param mFollowings       array of following users
     * @param mFollowers        array of followers users
     * @param mFavPlaylists     array of favourite playlists
     * @param mMadePlaylists    array of playlist that made for user
     * @param mFollowingArtists array of following artists
     * @param mSavedAlbums      array of saved albums
     * @param mLikedSongs     array of saved tracks
     */
    public User(String mEmail, String id, boolean mType, Bitmap mUserImage, String mName, String mDOB
            , String mGender, boolean mIsPremuim, int mNumOfFollowers, int mNumOfFollowings
            , ArrayList<User> mFollowings, ArrayList<User> mFollowers
            , ArrayList<Playlist> mFavPlaylists, ArrayList<Playlist> mMadePlaylists
            , ArrayList<Artist> mFollowingArtists, ArrayList<Album> mSavedAlbums, ArrayList<Track> mLikedSongs) {

        this.mEmail = mEmail;
        this._id = id;
        this.mType = mType;
        this.mUserImage = mUserImage;
        this.mName = mName;
        this.mDOB = mDOB;
        this.mGender = mGender;
        this.mIsPremuim = mIsPremuim;
        this.mNumOfFollowers = mNumOfFollowers;
        this.mNumOfFollowings = mNumOfFollowings;
        this.mFollowings = mFollowings;
        this.mFollowers = mFollowers;
        this.mFavPlaylists = mFavPlaylists;
        this.mMadePlaylists = mMadePlaylists;
        this.mFollowingArtists = mFollowingArtists;
        this.mLikedSongs = mLikedSongs;
        this.mSavedAlbums = mSavedAlbums;
    }

    /**
     * override constructor for user
     *
     * @param email      email of user
     * @param password   password of user
     * @param mType      type of user
     * @param mIsPremium if user is premium
     */
    public User(String email, String password, boolean mType, boolean mIsPremium) {
        this.mEmail = email;
        this.mPassword = password;
        this.mType = mType;
        this.mIsPremuim = mIsPremium;
    }


    /**
     * getter for saved albums
     *
     * @return return saved albums
     */
    public ArrayList<Album> getSavedAlbums() {
        return mSavedAlbums;
    }

    /**
     * setter of saved albums
     *
     * @param mSavedAlbums saved albums of user
     */
    public void setSavedAlbums(ArrayList<Album> mSavedAlbums) {
        this.mSavedAlbums = mSavedAlbums;
    }

    /**
     * getter of password
     *
     * @return returns password
     */
    public String getmPassword() {
        return mPassword;
    }

    /**
     * setter of password
     *
     * @param mPassword password of user (just in mock service)
     */
    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    /**
     * getter for email
     *
     * @return returns email of user
     */
    public String getmEmail() {
        return mEmail;
    }

    /**
     * setter for email
     *
     * @param mEmail email of user
     */
    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    /**
     * getter of user image
     *
     * @return image of user
     */
    public Bitmap getUserImage() {
        return mUserImage;
    }

    /**
     * setter for user image
     *
     * @param userImage user image
     */
    public void setUserImage(Bitmap userImage) {
        this.mUserImage = userImage;
    }

    /**
     * getter for user's name
     *
     * @return returns user's name
     */
    public String getmName() {
        return mName;
    }

    /**
     * setter for user's name
     *
     * @param mName user's name
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * getter for birth date
     *
     * @return return date of birth
     */
    public String getmDOB() {
        return mDOB;
    }

    /**
     * setter for birth date
     *
     * @param mDOB birth date
     */
    public void setmDOB(String mDOB) {
        this.mDOB = mDOB;
    }

    /**
     * getter for gender
     *
     * @return returns gender
     */
    public String getmGender() {
        return mGender;
    }

    /**
     * setter for gender
     *
     * @param mGender gender
     */
    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    /**
     * getter for if user is premium
     *
     * @return return true if user is premium
     */
    public boolean isPremuim() {
        return mIsPremuim;
    }

    /**
     * setter for premium
     *
     * @param premuim if user is premium
     */
    public void setPremuim(boolean premuim) {
        mIsPremuim = premuim;
    }

    /**
     * getter for number of followers
     *
     * @return return number of followers
     */
    public int getNumOfFollowers() {
        return mNumOfFollowers;
    }

    /**
     * setter for number of followers
     *
     * @param numOfFollowers number of followers
     */
    public void setNumOfFollowers(int numOfFollowers) {
        this.mNumOfFollowers = numOfFollowers;
    }

    /**
     * getter for number of followings
     *
     * @return return number of followings
     */
    public int getNumOfFollowings() {
        return mNumOfFollowings;
    }

    /**
     * setter for number of followings
     *
     * @param numOfFollowings number of followings
     */
    public void setNumOfFollowings(int numOfFollowings) {
        this.mNumOfFollowings = numOfFollowings;
    }

    /**
     * getter for number of followings
     *
     * @return return number of followings
     */
    public ArrayList<User> getmFollowings() {
        return mFollowings;
    }

    /**
     * setter for array of followings
     *
     * @param mFollowings array of followings
     */
    public void setmFollowings(ArrayList<User> mFollowings) {
        this.mFollowings = mFollowings;
    }

    /**
     * getter for array of followers
     *
     * @return return array of followers
     */
    public ArrayList<User> getmFollowers() {
        return mFollowers;
    }

    /**
     * setter for array of followers
     *
     * @param mFollowers array of followers
     */
    public void setmFollowers(ArrayList<User> mFollowers) {
        this.mFollowers = mFollowers;
    }

    /**
     * getter for array of favourite playlists
     *
     * @return return array of favourite playlists
     */
    public ArrayList<Playlist> getFavPlaylists() {
        return mFavPlaylists;
    }

    /**
     * setter for array of favourite playlists
     *
     * @param favPlaylists array of favourite playlists
     */
    public void setFavPlaylists(ArrayList<Playlist> favPlaylists) {
        this.mFavPlaylists = favPlaylists;
    }

    /**
     * getter for array of playlists made for user
     *
     * @return returns array of playlists made for user
     */
    public ArrayList<Playlist> getMadePlaylists() {
        return mMadePlaylists;
    }

    /**
     * setter for array of playlists made for user
     *
     * @param madePlaylists array of playlists made for user
     */
    public void setMadePlaylists(ArrayList<Playlist> madePlaylists) {
        this.mMadePlaylists = madePlaylists;
    }

    /**
     * getter for array of following artists
     *
     * @return return array of following artists
     */
    public ArrayList<Artist> getFollowingArtists() {
        return mFollowingArtists;
    }

    /**
     * setter for array of following artists
     *
     * @param mFollowingArtists array of following artists
     */
    public void setmFollowingArtists(ArrayList<Artist> mFollowingArtists) {
        this.mFollowingArtists = mFollowingArtists;
    }

    /**
     * handles following an artist
     *
     * @param artist artist to be followed
     */
    public void followArtist(Artist artist) {
        for (Artist a: mFollowingArtists) {
            if(a.getId().equals(artist.getId())) return;
        }
        mFollowingArtists.add(artist);
    }

    /**
     * handles unfollowing for artist
     *
     * @param artist artist to be unfollowed
     */
    public void unFollowArtist(Artist artist) {
        for (Artist a: mFollowingArtists) {
            if(a.getId().equals(artist.getId())){
                mFollowingArtists.remove(a);
                return;
            }
        }
    }

    /**
     * handles saving album for user
     *
     * @param album album to be added to list
     */

    public void saveAlbum(Album album){
        for (Album a: mSavedAlbums) {
            if(a.getAlbumId().equals(album.getAlbumId())) return;
        }
        mSavedAlbums.add(album);
    };

    /**
     * handles removing album from user's list
     * @param album album to be removed
     */
    public void removeAlbum(Album album){
        for (Album a: mSavedAlbums) {
        if(a.getAlbumId().equals(album.getAlbumId())){
            mSavedAlbums.remove(a);
            return;
        }
    }};

    /**
     * checks if albums is saved or not
     *
     * @param checkedAlbum album to be checked
     * @return returns true if exists
     */
    public Boolean checkSavedAlbum(Album checkedAlbum){
        for (Album album: mSavedAlbums) {
            if(album.getAlbumId().equals(checkedAlbum.getAlbumId())) return true;
        }
        return false;
    }

    public boolean checkLikedSong(Track checkedTrack){
        for (Track track: mLikedSongs) {
            if(track.getId().equals(checkedTrack.getId())) return true;
        }
        return false;
    }

    /**
     * checks if following an artist
     *
     * @param checkedArtist artist to be checked
     * @return returns true if followed
     */
    public Boolean checkFollowing(Artist checkedArtist){
        for (Artist artist: mFollowingArtists) {
            if(artist.getId().equals(checkedArtist.getId())) return true;
        }
        return false;
    }

    /**
     * getter for playlists
     * @return returns user's playlists
     */
    public ArrayList<Playlist> getAllPlaylists(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        playlists.addAll(mFavPlaylists);
        playlists.addAll(mMadePlaylists);
        return playlists;
    }

    /**
     * getter for array of played tracks
     *
     * @return returns array of played tracks
     */
    public ArrayList<Track> getLikedSongs() {
        return mLikedSongs;
    }

    /**
     * setter for array of played tracks
     *
     * @param mLikedSongs array of saved tracks
     */
    public void setLikedSongs(ArrayList<Track> mLikedSongs) {
        this.mLikedSongs = mLikedSongs;
    }

    /**
     * getter for type of user
     *
     * @return return true if user is listener, false for artist
     */
    public boolean isListenerType() {
        return mType;
    }

    /**
     * setter for user type
     *
     * @param mType type of user, true for listener, false for artist
     */
    public void setType(boolean mType) {
        this.mType = mType;
    }

    /**
     * Constructor for user
     *
     * @param email   holds email
     * @param id      holds id
     * @param name    holds name
     * @param type    holds type
     * @param premium holds if premium
     */
    public User(String email, String id, String name, boolean type, boolean premium) {
        this.mEmail = email;
        this._id = id;
        this.mName = name;
        this.mType = type;
        this.mIsPremuim = premium;
    }

    /**
     * getter for user id
     *
     * @return returns user id
     */
    public String get_id() {
        return _id;
    }

    /**
     * setter for user id
     *
     * @param _id holds user id
     */
    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * getter for user type (user/artist)
     *
     * @return return user's type
     */
    public String getUserType() {
        return mUserType;
    }

    /**
     * setter for user type
     *
     * @param mUserType holds user type
     */
    public void setUserType(String mUserType) {
        this.mUserType = mUserType;
    }

    /**
     * getter for user phone
     *
     * @return returns user's phone
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * setter fpr user phone number
     *
     * @param mPhone holds user's phone number
     */
    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     * getter for user's image url
     *
     * @return returns image url of user
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * setter for image url
     *
     * @param mImageUrl holds image url
     */
    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
