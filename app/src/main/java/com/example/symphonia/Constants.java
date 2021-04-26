package com.example.symphonia;

import com.example.symphonia.Entities.User;
import com.google.common.collect.FluentIterable;

/**
 * Class that holds all static variables to be used all application activities
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
 */

public class Constants {


    /**
     * debug status for app to decide which service mode will be used
     * true for MockService, false for REST APIs mode
     */
    public final static boolean DEBUG_STATUS = false;
    /**
     * holds base url
     */
    public final static String BASE_URL = "https://thesymphonia.ddns.net/";
    /**
     * holds login API
     */
    public final static String LOG_IN_URL = BASE_URL + "api/v1/users/login?";
    /**
     * holds sign up API
     */
    public final static String SIGN_UP_URL = BASE_URL + "api/v1/users/signup?";
    /**
     * holds check email exists API
     */
    public final static String EMAIL_EXISTS_URL = BASE_URL + "api/v1/users/email-exist?";
    /**
     * holds forget password API
     */
    public final static String FORGET_PASSWORD_URL = BASE_URL + "api/v1/users/forgotpassword?";
    /**
     * holds reset password API
     */
    public final static String RESET_PASSWORD_URL = BASE_URL + "api/v1/users/resetpassword/";
    /**
     * url of request to get recent playlists
     */
    public static final String GET_RECENT_PLAYLISTS = BASE_URL + "api/v1/me/recently-played";
    /**
     * url of request to get popular playlists
     */
    public static final String GET_POPULAR_PLAYLISTS = BASE_URL + "api/v1/browse/categories/party/playlists?";
    /**
     * url of request to get all categories
     */
    public static final String GET_ALL_CATEGORIES = BASE_URL + "api/v1/browse/categories?";
    public static final String Get_Current_User_Profile = BASE_URL + "api/v1/me";
    public static final String Get_Current_User_Profile_playlists = BASE_URL + "api/v1/me/playlists";
    public static final String Get_playlist = BASE_URL + "api/v1/playlists";
    public static final String Get_User_Following = BASE_URL + "api/v1/me/following";
    public static final String Get_User_Followers = BASE_URL + "api/v1/Artists/5e7f76abdb66b448b4356324/followers";
    public static final String ALBUMS_URL = BASE_URL + "api/v1/me/albums";
    public static final String SEARCH_URL = BASE_URL + "api/v1/search";
    public static final String OWNED_PLAYLIST = BASE_URL + "api/v1/me/playlists/owned";

    /**
     * made-for-you playlists link
     */
    public static final String GET_M_F_Y_PLAYLISTS = BASE_URL + "api/v1/browse/categories/party/playlists?";
    /**
     * url of request to get random playlists
     */
    public static final String GET_RANDOM_PLAYLISTS = BASE_URL + "api/v1/playlists/rand?";
    public static final String GET_RECOMMENDED_ARTISTS = BASE_URL + "api/v1/browse/artists";
    public static final String FOLLOW_ARTIST_URL = BASE_URL + "api/v1/me/following";
    public static final String SAVED_TRACKS = BASE_URL + "api/v1/me/tracks";
    public static final String RANDOM_RECOMMENDATIONS = BASE_URL + "api/v1/recommendations";
    /**
     * url of request to get tracks of a playlist
     */
    public static final String GET_PLAYLISTS_TRACKS = BASE_URL + "api/v1/playlists/";
    /**
     * url of request to play track
     */
    public static final String PLAY_TRACK = BASE_URL + "api/v1/me/player/tracks/";
    public static final String PLAY_NEXT = BASE_URL + "api/v1/me/player/next";
    public static final String PLAY_PREV = BASE_URL + "api/v1/me/player/previous";
    public static final String GET_CURR_PLAYING = BASE_URL + "api/v1/me/player/currently-playing";
    public static final String GET_TRACK = BASE_URL + "api/v1/users/track/";
    public static final String GET_QUEUE = BASE_URL + "api/v1/me/player/queue";
    public static final String CHECK_SAVED_TRACKS = BASE_URL + "api/v1/me/tracks/contains?ids=";
    public static final String SAVE_TRACK = BASE_URL + "api/v1/me/tracks?ids=";
    public static final String REMOVE_SAVED_TRACK = BASE_URL + "api/v1/me/tracks?ids=";
    public static final String GET_ALBUM_TRACKS = BASE_URL + "api/v1/albums/";
    /**
     * Token that would be used for current user
     */
    public static String currentToken = "";
    /**
     * object of the current user
     */
    public static User currentUser = null;

}

