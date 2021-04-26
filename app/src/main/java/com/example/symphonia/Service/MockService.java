package com.example.symphonia.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Category;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Copyright;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Entities.User;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbumTracks;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbums;
import com.example.symphonia.Fragments_and_models.profile.BottomSheetDialogProfile;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;
import com.example.symphonia.Fragments_and_models.profile.ProfilePlaylistsFragment;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Class that holds all functions to be used to fill metadata of application
 * using dependency injection, which implements APIs interface
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
 */

public class MockService implements APIs {

    private Boolean firstTimeToGetRecentSearches;
    private ArrayList<com.example.symphonia.Entities.Context> mRandomContext;
    private ArrayList<com.example.symphonia.Entities.Context> mRecentContext;
    private ArrayList<com.example.symphonia.Entities.Context> mContexts;
    private ArrayList<com.example.symphonia.Entities.Context> mPopularContext;

    private ArrayList<Playlist> mRandomPlaylists;
    private ArrayList<Playlist> mRecentPlaylists;
    private ArrayList<Playlist> mPlaylists;
    private ArrayList<Playlist> mPopularPlaylists;
    private ArrayList<Artist> mArtists;
    private ArrayList<Album> mAlbums;
    private ArrayList<Track> mTracks;
    private ArrayList<Track> mLikedSongs;
    private ArrayList<Container> mData;
    private ArrayList<Container> mRecentSearches;
    private int artistsCount = 0;
    private int albumssCount = 0;
    private int songsCount = 0;
    private int profilesCount = 0;
    private int genresCount = 0;
    private int playlistsCount = 0;
    private ArrayList<Container> mArtistAlbums;
    private ArrayList<Container> mArtistTracks;

    /**
     * Array that holds some users of type listener to be mimic
     */
    private ArrayList<User> mListenerArrayList;
    /**
     * Array that holds some users of type artist to be mimic
     */
    private ArrayList<User> mArtistArrayList;

    /**
     * constructor of mockService class that initializes all mimic data
     */
    public MockService() {
        firstTimeToGetRecentSearches = true;
        mRecentSearches = new ArrayList<>();
        mListenerArrayList = new ArrayList<>();
        mArtistArrayList = new ArrayList<>();

        //add 3 users of type listeners
        mListenerArrayList.add(new User("user1@symphonia.com", "12345678", true, false));
        mListenerArrayList.add(new User("user2@symphonia.com", "12345678", true, true));
        mListenerArrayList.add(new User("user3@symphonia.com", "12345678", true, true));

        //add 3 users of type artist
        mArtistArrayList.add(new User("artist1@symphonia.com", "12345678", false, false));
        mArtistArrayList.add(new User("artist2@symphonia.com", "12345678", false, true));
        mArtistArrayList.add(new User("artist3@symphonia.com", "12345678", false, true));

        mArtists = new ArrayList<>();
        mArtists.add(new Artist("1", R.drawable.ragheb, "Ragheb Alama"));
        mArtists.add(new Artist("2", R.drawable.elissa, "Elissa"));
        mArtists.add(new Artist("3", R.drawable.angham, "Angham"));
        mArtists.add(new Artist("4", R.drawable.wael, "Wael Kfoury"));
        mArtists.add(new Artist("5", R.drawable.wael_gassar, "Wael Jassar"));
        mArtists.add(new Artist("6", R.drawable.shawn, "Shawn Mendes"));
        mArtists.add(new Artist("7", R.drawable.bts, "BTS"));
        mArtists.add(new Artist("8", R.drawable.billie, "Billie Eilish"));
        mArtists.add(new Artist("9", R.drawable.alan, "Alan Walker"));
        mArtists.add(new Artist("10", R.drawable.saaad, "Saad Lamjarred"));
        mArtists.add(new Artist("11", R.drawable.ed, "Ed Sheeran"));
        mArtists.add(new Artist("12", R.drawable.halsey, "Halsey"));
        mArtists.add(new Artist("13", R.drawable.imagine, "Imagine Dragons"));
        mArtists.add(new Artist("14", R.drawable.ariana, "Ariana Grande"));
        mArtists.add(new Artist("15", R.drawable.aseel, "Aseel Hameem"));
        mArtists.add(new Artist("16", R.drawable.maroon, "Maroon 5"));
        mArtists.add(new Artist("17", R.drawable.ali, "Ali Gatie"));
        mArtists.add(new Artist("18", R.drawable.zayn, "ZAYN"));
        mArtists.add(new Artist("19", R.drawable.cairokee, "Cairokee"));
        mArtists.add(new Artist("20", R.drawable.rauf_fayk, "Rauf & Faik"));
        mArtists.add(new Artist("21", R.drawable.adham, "Adham Nabulsi"));
        mArtists.add(new Artist("22", R.drawable.abu, "Abu"));
        mArtists.add(new Artist("23", R.drawable.eminem, "Eminem"));
        mArtists.add(new Artist("24", R.drawable.hussien, "Hussain Al Jassmi"));
        mArtists.add(new Artist("25", R.drawable.nancy, "Nancy Ajram"));
        mArtists.add(new Artist("26", R.drawable.havana, "Camila Cabello"));
        mArtists.add(new Artist("27", R.drawable.selena, "Selena Gomez"));
        mArtists.add(new Artist("28", R.drawable.taylor, "Taylor Swift"));
        mArtists.add(new Artist("29", R.drawable.tamer_ashour, "Tamer Ashour"));
        mArtists.add(new Artist("30", R.drawable.assala, "Assala Nasri"));
        mArtists.add(new Artist("31", R.drawable.maher, "Maher Zain"));
        mArtists.add(new Artist("32", R.drawable.adele, "Adele"));
        mArtists.add(new Artist("33", R.drawable.hamza, "Hamza Namira"));
        mArtists.add(new Artist("34", R.drawable.justin, "Justin Bieber"));
        mArtists.add(new Artist("35", R.drawable.jannat, "Jannat"));
        mArtists.add(new Artist("36", R.drawable.samira, "Samira Said"));
        mArtists.add(new Artist("37", R.drawable.maryam, "Myriam Fares"));
        mArtists.add(new Artist("38", R.drawable.amr, "Amr Diab"));
        mArtists.add(new Artist("39", R.drawable.bahaa, "Bahaa Sultan"));
        mArtists.add(new Artist("40", R.drawable.loai, "Loai"));

        mArtistAlbums = new ArrayList<>();
        mArtistAlbums.add(new Container("album1", "3 songs", Utils.convertToBitmap(R.drawable.amr)));
        mArtistAlbums.add(new Container("album2", "3 songs", Utils.convertToBitmap(R.drawable.adele)));
        mArtistAlbums.add(new Container("album3", "3 songs", Utils.convertToBitmap(R.drawable.abu)));
        mArtistAlbums.add(new Container("album4", "3 songs", Utils.convertToBitmap(R.drawable.assala)));

        mArtistTracks = new ArrayList<>();
        mArtistTracks.add(new Container("track1", "3 minutes", Utils.convertToBitmap(R.drawable.amr)));
        mArtistTracks.add(new Container("track2", "2 minutes", Utils.convertToBitmap(R.drawable.adele)));
        mArtistTracks.add(new Container("track3", "1 minutes", Utils.convertToBitmap(R.drawable.abu)));
        mArtistTracks.add(new Container("track4", "5 minutes", Utils.convertToBitmap(R.drawable.assala)));


        mAlbums = new ArrayList<>();

        mAlbums.add(new Album("6qqNVTkY8uBg9cP3Jd7DAH",
                "single",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(7))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 Darkroom/Interscope Records", "C"),
                        new Copyright("2020 Darkroom/Interscope Records", "P"))),
                R.drawable.no_time_to_die,
                "No Time To Die",
                "2020-02-13",
                new ArrayList<Track>()));

        mAlbums.add(new Album("7eFyrxZRPqw8yvZXMUm88A",
                "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(37))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 Nay", "C"),
                        new Copyright("2018 Nay", "P"))),
                R.drawable.kol_hayaty,
                "Kol Hayaty",
                "2018-10-03",
                new ArrayList<Track>()));

        mAlbums.add(new Album("2D1nEskDzLz38JiUeVK5mh",
                "single",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(29))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 MuzicUp", "C"),
                        new Copyright("2020 MuzicUp", "P"))),
                R.drawable.shamekh,
                "Shamekh",
                "2020-01-12",
                new ArrayList<Track>()));

        mAlbums.add(new Album("0hZwt0aSEEiwUByVQuxntK",
                "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(30))),
                new ArrayList<Copyright>(Collections.singletonList(new Copyright("2012 Awakening Worldwide", "C"))),
                R.drawable.fogive_me,
                "Forgive Me",
                "2012-04-02",
                new ArrayList<Track>()));

        mAlbums.add(new Album("3JfSxDfmwS5OeHPwLSkrfr",
                "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(12))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 KIDinaKORNER/Interscope Records", "C"),
                        new Copyright("2018 KIDinaKORNER/Interscope Records", "P"))),
                R.drawable.origins,
                "Origins (Deluxe)",
                "2018-11-09",
                new ArrayList<Track>()));


        mPopularContext = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "sdflk;asfl;jsd[jrfd", "OneRepublic", "mood booster",
                "Rescue Me", R.drawable.rescue_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        tracks.add(new Track("Freaking Me Out", "sdlfknesdfalklsdlsd", "Ava Max",
                "mood booster", null, R.drawable.freaking_me_out, Settings.System.DEFAULT_RINGTONE_URI, true));
        tracks.add(new Track("You Can't Stop The Girl", "dlsfk;jl;esafdoilfd",
                "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl
                , Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        mPopularContext.add(new com.example.symphonia.Entities.Context("mood booster", "ewoirgwepdoed",
                "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks, "playlist"));
        mRecentContext = new ArrayList<com.example.symphonia.Entities.Context>();
        ArrayList<Track> rTracks = new ArrayList<Track>();
        rTracks.add(new Track("Little Do You Know", "srtyhtutyhgdgsfdgdsdf", "Alex & Sierra", "Rewind-the sound of 2014"
                , null, R.drawable.little_do_you_know, Settings.System.DEFAULT_RINGTONE_URI, true));
        rTracks.add(new Track("Wildest Dreams", "shtfgfmnbghguyghf", "Taylor Swift", "Rewind-the sound of 2014"
                , null, R.drawable.wildest_dreams, Uri.parse("http://stream.radiosai.net:8002/"), false));
        rTracks.add(new Track("One Last Time", "lfdgkm;dskvnlsd", "Ariana Grande", "Rewind-the sound of 2014"
                , null, R.drawable.one_last_time, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        mRecentContext.add(new com.example.symphonia.Entities.Context("Rewind-the sound of 2014", "sdfljdsfkdslkfv", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), rTracks, "playlist"));

        mRandomContext = new ArrayList<>();
        ArrayList<Track> ranTracks = new ArrayList<Track>();
        ranTracks.add(new Track("Intentions", "sdfirjkfdvkjdsc", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI, false));
        ranTracks.add(new Track("Stupid Love", "sldksldkfnlasdnc", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, Uri.parse("http://stream.radiosai.net:8002/"), false));
        ranTracks.add(new Track("Feel Me", "slkdjfslkfdkdsljf", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), true));
        mRandomContext.add(new com.example.symphonia.Entities.Context("Daily Left", "dslkfoeirefdds", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), ranTracks, "playlist"));

        mContexts = new ArrayList<com.example.symphonia.Entities.Context>();
        mContexts.addAll(mPopularContext);
        mContexts.addAll(mRecentContext);

        for (com.example.symphonia.Entities.Context playlist : mContexts) {
            playlist.setmOwnerName("Symphonia");
        }

/*        mTracks = new ArrayList<>();
        mTracks.addAll(tracks);
        mTracks.addAll(rTracks);
        mTracks.addAll(ranTracks);

        for (int i = 0; i < mTracks.size(); i++) {
            mTracks.get(i).setId(String.valueOf(i));
        }*/

        mLikedSongs = new ArrayList<>();
        mLikedSongs.addAll(tracks);
        mLikedSongs.addAll(rTracks);

    }

    /**
     * getter for made-for-you playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return made-for-you  playlist
     */
    @Override
    public ArrayList<com.example.symphonia.Entities.Context> getMadeForYouPlaylists(Context context, String mToken) {
        return mRandomContext;
    }

    /**
     * getter for recently-player playlist
     *
     * @param context  context of hosting activity
     * @param fragment token of user
     * @return recently-player  playlist
     */
    @Override
    public ArrayList<com.example.symphonia.Entities.Context> getRecentPlaylists(Context context, HomeFragment fragment) {
        return mRecentContext;
    }

    /**
     * this function gets tracks of a certain playlist
     *
     * @param context          context of current activity
     * @param id               id of playlist
     * @param playlistFragment instance of the fragment to make the update in
     * @return array list of tracks
     */
    @Override
    public ArrayList<Track> getTracksOfPlaylist(Context context, String id, PlaylistFragment playlistFragment) {
        ArrayList<Track> tracks = new ArrayList<>();
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        for (int i = 0; i < mPopularContext.size(); i++) {
            if (mPopularContext.get(i).getId().matches(id)) {
                Utils.playingContext.setTracks(mPopularContext.get(i).getTracks());
                tracks = mPopularContext.get(i).getTracks();
            }
        }
        for (int i = 0; i < mRecentContext.size(); i++) {
            if (mRecentContext.get(i).getId().matches(id)) {
                Utils.playingContext.setTracks(mRecentContext.get(i).getTracks());
                tracks = mRecentContext.get(i).getTracks();

            }
        }
        for (int i = 0; i < mRandomContext.size(); i++) {
            if (mRandomContext.get(i).getId().matches(id)) {
                Utils.playingContext.setTracks(mRandomContext.get(i).getTracks());
                tracks = mRandomContext.get(i).getTracks();

            }
        }
        if (listener != null)
            listener.updateUiGetTracksOfPlaylist(playlistFragment, Utils.playingContext.getTracks());
        return tracks; //mRecentPlaylists.get(0).getTracks();
    }

    @Override
    public ArrayList<Track> getPlaylistTracks(RestApi.updateTracksNames listener, String id) {
        return null;
    }


    /**
     * getter for Random playlist
     *
     * @param context      context of hosting activity
     * @param homeFragment token of user
     * @return Random  playlist
     */
    @Override
    public ArrayList<com.example.symphonia.Entities.Context> getRandomPlaylists(Context context, HomeFragment homeFragment) {
        return mRandomContext;
    }


    /**
     * getter for popular  playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return Random  playlist
     */
    @Override
    public ArrayList<com.example.symphonia.Entities.Context> getPopularPlaylists(Context context, String mToken) {
        return mPopularContext;
    }

    /**
     * holds facebook login api request
     *
     * @param context  holds context of requested page
     * @param fb_token holds facebook token
     * @param image    holds user's image
     * @return returns true if success
     */
    @Override
    public boolean facebookLogin(Context context, String fb_token, String image) {
        return false;
    }

    /**
     * holds logging user in, creation of user object and sets token
     *
     * @param context  holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType    type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    @Override
    public boolean logIn(final Context context, String username, String password, boolean mType) {
        //initial index of user in users list
        int userIndex = -1;

        //checks if user is listener
        if (mType) {

            //search in list of user's email & password
            for (int i = 0; i < mListenerArrayList.size(); i++) {
                if (username.equals(mListenerArrayList.get(i).getmEmail()) &&
                        password.equals(mListenerArrayList.get(i).getmPassword())) {
                    //if found, then store index and stop search
                    userIndex = i;
                    break;
                }
            }
        } else {

            //in list of artist user
            for (int i = 0; i < mArtistArrayList.size(); i++) {
                if (username.equals(mArtistArrayList.get(i).getmEmail()) &&
                        password.equals(mArtistArrayList.get(i).getmPassword())) {
                    userIndex = i;
                    break;
                }
            }
        }

        //checks if data isn't in list
        if (userIndex == -1)
            return false;

        //if user is artist
        if (!mType) {

            //set token
            Constants.currentToken = "token2";

            //add some artists
            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                followed.add(mArtists.get(i));
            }

            //creates object of user to store logged in user's data
            Constants.currentUser = new User(mArtistArrayList.get(userIndex).getmEmail(), "2030k", false
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Islam Ahmed", "1998/24/11", "male"
                    , mArtistArrayList.get(userIndex).isPremuim()
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), mPlaylists, new ArrayList<Playlist>()
                    , followed, new ArrayList<Album>(mAlbums), mLikedSongs);
            Constants.currentUser.setUserType("artist");
        } else {

            //for listener type, set different token
            Constants.currentToken = "token1";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                followed.add(mArtists.get(i));
            }

            Constants.currentUser = new User(mListenerArrayList.get(userIndex).getmEmail(), "2030k", true
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Hossam Alaa", "1999/04/06", "male"
                    , mListenerArrayList.get(userIndex).isPremuim()
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), mPlaylists, new ArrayList<Playlist>()
                    , followed, new ArrayList<Album>(mAlbums), mLikedSongs);
        }
        if (mType)
            Constants.currentUser.setUserType("user");
        else
            Constants.currentUser.setUserType("artist");

        return true;
    }

    /**
     * this function initialize the request to stream music
     *
     * @param context context of current activity
     */
    @Override
    public void playTrack(Context context) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        playingContextId = Utils.playingContext.getId();
        currTrackId = Utils.currTrack.getId();
        currContextType = Utils.playingContext.getContextType();
        listener.updateUiPlayTrack();

    }

    /**
     * handles that user is signing up, initializes new user object
     * fill database with new user
     *
     * @param context  holds context of activity that called this method
     * @param mType    type of user, true for listener and false for artist
     * @param email    email of user
     * @param password password of user
     * @param DOB      date of birth of user
     * @param gender   gender of user
     * @param name     name of user
     * @return returns true if sign up is done
     */
    @Override
    public boolean signUp(final Context context, boolean mType, String email, String password,
                          String DOB, String gender, String name) {

        //set new token for current new user
        Constants.currentToken = "newToken";

        //creates new object of user and fill data
        Constants.currentUser = new User(email, "2030k", mType, Utils.convertToBitmap(R.drawable.download)
                , name, DOB, gender, false, 0, 0, new ArrayList<User>()
                , new ArrayList<User>(), mPlaylists, new ArrayList<Playlist>()
                , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());
        if (mType)
            Constants.currentUser.setUserType("user");
        else
            Constants.currentUser.setUserType("artist");

        Constants.currentUser.setmPassword(password);
        return true;
    }

    /**
     * handles forget password API
     *
     * @param context holds context of requested page
     * @param email   holds user's email
     * @return returns true if success
     */
    @Override
    public boolean forgetPassword(Context context, String email) {
        return true;
    }

    /**
     * handles resetting password API
     *
     * @param context  holds context of requested page
     * @param password holds new password
     * @param token    holds user's token of email
     * @return returns true if success
     */
    @Override
    public boolean resetPassword(Context context, String password, final String token) {
        return false;
    }

    /**
     * handles apply artist API
     *
     * @param context holds context of requested page
     * @param token   holds user's token
     * @return returns true if success
     */
    @Override
    public boolean applyArtist(Context context, String token) {
        return false;
    }


    /**
     * checks if email is already signed in database or not
     *
     * @param context holds context of activity that called this method
     * @param email   email of user
     * @param mType   type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    @Override
    public boolean checkEmailAvailability(final Context context, String email, boolean mType) {
        if (mType) {
            //if user is listener, then check in listeners list
            for (int i = 0; i < mListenerArrayList.size(); i++)
                if (email.equals(mListenerArrayList.get(i).getmEmail())) return false;
        } else {
            //if user is artist, then check artists list
            for (int i = 0; i < mArtistArrayList.size(); i++)
                if (email.equals(mArtistArrayList.get(i).getmEmail())) return false;
        }
        return true;
    }


    /**
     * get the recent searches of the user
     *
     * @param context context of the activity
     * @return ArrayList of Container of recent searches
     */
    @Override
    public ArrayList<Container> getResentResult(Context context) {

        if (firstTimeToGetRecentSearches) {
            mRecentSearches = new ArrayList<>();
            mRecentSearches.add(new Container("Quran", context.getResources().getString(R.string.Playlist), Utils.convertToBitmap(R.drawable.images)));
            mRecentSearches.add(new Container("George Wassouf", context.getResources().getString(R.string.Artist), Utils.convertToBitmap(R.drawable.download)));
            mRecentSearches.add(new Container("Get Weird", context.getResources().getString(R.string.Album) + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
            mRecentSearches.add(new Container("Godzilla", context.getResources().getString(R.string.Song) + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
            mRecentSearches.add(new Container("Lollipop", context.getResources().getString(R.string.Album) + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
            mRecentSearches.add(new Container("Friction", context.getResources().getString(R.string.Playlist), Utils.convertToBitmap(R.drawable.images)));
            mRecentSearches.add(new Container("Playlist", context.getResources().getString(R.string.Playlist), Utils.convertToBitmap(R.drawable.alan)));
            mRecentSearches.add(new Container("Miley Cyrus", context.getResources().getString(R.string.Artist), Utils.convertToBitmap(R.drawable.download)));
        }
        return mRecentSearches;
    }


    /**
     * get all results of search
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of Container
     */
    public ArrayList<Container> getAllResultsOfSearch(Context context, String searchWord) {
        String song = context.getResources().getString(R.string.Song);
        String artist = context.getResources().getString(R.string.Artist);
        String album = context.getResources().getString(R.string.Album);
        String playlist = context.getResources().getString(R.string.Playlist);
        String profile = context.getResources().getString(R.string.Profile);
        String genre = context.getResources().getString(R.string.Genre);

        mData = new ArrayList<>();
        mData.add(new Container("Quran", playlist, Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("George Wassouf", artist, Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Get Weird", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Godzilla", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Believer", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Natural", song + ".Imagine Dragons", Utils.convertToBitmap(R.drawable.abu)));
        mData.add(new Container("Love Save Us Once", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Get Weird", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Mozart", genre, Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Get Weird", album + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Mine", playlist, Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("ALVXARO", playlist, Utils.convertToBitmap(R.drawable.adele)));

        mData.add(new Container("Amr Diab", artist, Utils.convertToBitmap(R.drawable.amr)));
        mData.add(new Container("Stop Noise", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Beautiful Song", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Lose Yourself", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Whatever It Takes", song + ".Imagine Dragons", Utils.convertToBitmap(R.drawable.tamer_ashour)));
        mData.add(new Container("Max Payne", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Rick And Morty", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Rock", genre, Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Elephant", album + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("End Of World", playlist, Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Crazy Love", playlist, Utils.convertToBitmap(R.drawable.angham)));

        mData.add(new Container("Van Diesel", artist, Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Wind", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("I Will Survive", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Beautiful Mind", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Sucker For Pain", song + ".Imagine Dragons", Utils.convertToBitmap(R.drawable.billie)));
        mData.add(new Container("Pain", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Quite Place", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Sleep", genre, Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Gamal", album + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Nerds", playlist, Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Smoke Grenades", playlist, Utils.convertToBitmap(R.drawable.jannat)));

        mData.add(new Container("Samir Abo Elnil", artist, Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Sun Rises", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Cat Sound", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Billie Jean", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Thunder", song + ".Imagine Dragons", Utils.convertToBitmap(R.drawable.loai)));
        mData.add(new Container("The Shadows", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Silence Of Lambs", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Afro", genre, Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Fancy", album + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Jungles", playlist, Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Oranges", playlist, Utils.convertToBitmap(R.drawable.hamza)));

        mData.add(new Container("Adele", artist, Utils.convertToBitmap(R.drawable.adele)));
        mData.add(new Container("Yellow Album", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Stressed Out", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("ThunderClouds", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Thunder", song + ".Imagine Dragons", Utils.convertToBitmap(R.drawable.halsey)));
        mData.add(new Container("High Waves", "Album.Little Mix", Utils.convertToBitmap(R.drawable.download)));
        mData.add(new Container("Nice Album", album + ".Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        mData.add(new Container("Pop", genre, Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Lollipop", album + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        mData.add(new Container("Friction", playlist, Utils.convertToBitmap(R.drawable.images)));
        mData.add(new Container("Playlist", playlist, Utils.convertToBitmap(R.drawable.wael)));
        mData.add(new Container("Miley Cyrus", artist, Utils.convertToBitmap(R.drawable.download)));

        mData.add((new Container("my profile", profile, Utils.convertToBitmap(R.drawable.assala))));
        mData.add((new Container("mohammed ahmed", profile, Utils.convertToBitmap(R.drawable.cairokee))));
        mData.add((new Container("ali saad", profile, Utils.convertToBitmap(R.drawable.samira))));


        LinkedList<Container> resultsLinked = new LinkedList<>();
        ArrayList<Container> results = new ArrayList<>();
        artistsCount = 0;
        albumssCount = 0;
        songsCount = 0;
        profilesCount = 0;
        genresCount = 0;
        playlistsCount = 0;
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getCat_Name().startsWith(searchWord)) {
                resultsLinked.addFirst(mData.get(i));
                if (mData.get(i).getCat_Name2().startsWith("Song")) songsCount++;
                if (mData.get(i).getCat_Name2().startsWith("Album")) albumssCount++;
                if (mData.get(i).getCat_Name2().startsWith("Artist")) artistsCount++;
                if (mData.get(i).getCat_Name2().startsWith("Genre")) genresCount++;
                if (mData.get(i).getCat_Name2().startsWith("Playlist")) playlistsCount++;
                if (mData.get(i).getCat_Name2().startsWith("Profile")) profilesCount++;
            } else if (mData.get(i).getCat_Name().contains(searchWord)) {
                resultsLinked.addLast(mData.get(i));
                if (mData.get(i).getCat_Name2().startsWith("Song")) songsCount++;
                if (mData.get(i).getCat_Name2().startsWith("Album")) albumssCount++;
                if (mData.get(i).getCat_Name2().startsWith("Artist")) artistsCount++;
                if (mData.get(i).getCat_Name2().startsWith("Genre")) genresCount++;
                if (mData.get(i).getCat_Name2().startsWith("Playlist")) playlistsCount++;
                if (mData.get(i).getCat_Name2().startsWith("Profile")) profilesCount++;
            }
        }
        results.addAll(resultsLinked);
        return results;
    }

    /**
     * get seven or less results of search
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of Container
     */
    @Override
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        ArrayList<Container> results = new ArrayList<>();
        ArrayList<Container> temp;
        temp = getAllResultsOfSearch(context, searchWord);
        int size = 7;
        if (temp.size() < 7) size = temp.size();
        for (int i = 0; i < size; i++) {
            results.add(temp.get(i));
        }
        return results;
    }

    /**
     * get a list of user categories
     *
     * @param context context of the activity
     * @return ArrayList of Category of categories
     */
    @Override
    public ArrayList<Category> getCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(context.getResources().getString(R.string.New_Releases), Utils.convertToBitmap(R.drawable.solid_image)));
        categories.add(new Category(context.getResources().getString(R.string.Charts), Utils.convertToBitmap(R.drawable.solid_image2)));
        categories.add(new Category(context.getResources().getString(R.string.Concerts), Utils.convertToBitmap(R.drawable.solid_image3)));
        categories.add(new Category(context.getResources().getString(R.string.Made_for_you), Utils.convertToBitmap(R.drawable.solid_image4)));
        categories.add(new Category(context.getResources().getString(R.string.Arabic), Utils.convertToBitmap(R.drawable.solid_image5)));
        categories.add(new Category(context.getResources().getString(R.string.Mood), Utils.convertToBitmap(R.drawable.blue_image)));
        categories.add(new Category(context.getResources().getString(R.string.Decades), Utils.convertToBitmap(R.drawable.solid_image8)));
        categories.add(new Category(context.getResources().getString(R.string.Gaming), Utils.convertToBitmap(R.drawable.solid_image7)));
        categories.add(new Category(context.getResources().getString(R.string.Workout), Utils.convertToBitmap(R.drawable.solid_red_image)));
        categories.add(new Category(context.getResources().getString(R.string.Focus), Utils.convertToBitmap(R.drawable.multicolor_image)));
        categories.add(new Category(context.getResources().getString(R.string.Party), Utils.convertToBitmap(R.drawable.solidcolor_image)));
        categories.add(new Category(context.getResources().getString(R.string.Dinner), Utils.convertToBitmap(R.drawable.purble_image)));
        categories.add(new Category(context.getResources().getString(R.string.Jazz), Utils.convertToBitmap(R.drawable.grey_image)));
        categories.add(new Category(context.getResources().getString(R.string.Soul), Utils.convertToBitmap(R.drawable.lightpurble_image)));
        categories.add(new Category(context.getResources().getString(R.string.Kids), Utils.convertToBitmap(R.drawable.lemongreen_image)));
        categories.add(new Category(context.getResources().getString(R.string.Blues), Utils.convertToBitmap(R.drawable.lightgrey_image)));
        categories.add(new Category(context.getResources().getString(R.string.Punk), Utils.convertToBitmap(R.drawable.lightbrown_image)));
        categories.add(new Category(context.getResources().getString(R.string.Travel), Utils.convertToBitmap(R.drawable.gradientgreen_image)));
        return categories;
    }

    /**
     * get a lsit of user genres
     *
     * @param context context of the activity
     * @return ArrayList of Category of genres
     */
    @Override
    public ArrayList<Category> getGenres(Context context) {
        ArrayList<Category> genres = new ArrayList<>();
        genres.add(new Category(context.getResources().getString(R.string.Pop), Utils.convertToBitmap(R.drawable.blue_image2)));
        genres.add(new Category(context.getResources().getString(R.string.HipHop), Utils.convertToBitmap(R.drawable.yellow_image)));
        genres.add(new Category(context.getResources().getString(R.string.Rock), Utils.convertToBitmap(R.drawable.image_pink)));
        genres.add(new Category(context.getResources().getString(R.string.Sleep), Utils.convertToBitmap(R.drawable.skyblue_image)));
        genres.add(new Category(context.getResources().getString(R.string.Chill), Utils.convertToBitmap(R.drawable.purble_image)));
        return genres;
    }

    /**
     * get a list of artists of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of artists
     */
    @Override
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> artists = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Artist)))
                artists.add(temp.get(i));
        }
        return artists;
    }

    /**
     * get a list of songs of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of songs
     */
    @Override
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> songs = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Song)))
                songs.add(temp.get(i));
        }
        return songs;
    }

    /**
     * get a list of albums of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of albums
     */
    @Override
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> albums = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Album)))
                albums.add(temp.get(i));
        }
        return albums;
    }

    /**
     * get a list of genres of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of genres
     */
    @Override
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> genres = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Genre)))
                genres.add(temp.get(i));
        }
        return genres;
    }

    /**
     * get a list of playlists of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of playlists
     */
    @Override
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> playlists = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Playlist)))
                playlists.add(temp.get(i));
        }
        return playlists;
    }

    /**
     * get a list of profiles of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of profiles
     */
    @Override
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> profiles = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Profile)))
                profiles.add(temp.get(i));
        }
        return profiles;
    }

    /**
     * ensure that the recent searches won't be returned again
     *
     * @param context  context of the activity
     * @param position position of the element which is deleted
     */
    @Override
    public void removeOneRecentSearch(Context context, int position) {
        //mRecentSearches.remove(position);
        firstTimeToGetRecentSearches = false;
    }

    /**
     * ensure to return empty list when recent searches is required
     *
     * @param context context of the activity
     */
    @Override
    public void removeAllRecentSearches(Context context) {

        firstTimeToGetRecentSearches = false;
        mRecentSearches = new ArrayList<Container>();
    }

    /**
     * return a list of popular playlists
     *
     * @param context context of the activity
     * @return a ArrayList of Container of Popular playlists
     */
    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        ArrayList<Container> playlists = new ArrayList<>();
        playlists.add(new Container("greate playlist", "2,700 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.adele)));
        playlists.add(new Container("Amr Diab", "5,200 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.amr)));
        playlists.add(new Container("beautiful", "3,300 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.imagine)));
        playlists.add(new Container("simple", "800 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.alan)));
        playlists.add(new Container("nice songs", "1200 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.ed)));
        playlists.add(new Container("araby", "3000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.assala)));
        playlists.add(new Container("Bahaa Sultan", "2,100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.bahaa)));
        playlists.add(new Container("anghami", "1,100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.angham)));
        playlists.add(new Container("Thunder", "2100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.halsey)));
        playlists.add(new Container("selena", "2800 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.selena)));
        playlists.add(new Container("Smoke Grenades", "500 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.jannat)));
        playlists.add(new Container("Playlist", "26 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.wael)));
        playlists.add(new Container("3 d2at", "773 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.abu)));
        playlists.add(new Container("Oranges", "470 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.hamza)));

        return playlists;
    }

    /**
     * return four popular playlists
     *
     * @param context context of the activity
     * @return return four popular playlists
     */
    @Override
    public ArrayList<Container> getFourPlaylists(Context context) {
        ArrayList<Container> data = new ArrayList<>();
        ArrayList<Container> playlists = getAllPopularPlaylists(context);
        int n = 4;
        if (playlists.size() < n) n = playlists.size();
        for (int i = 0; i < n; i++) {
            data.add(playlists.get(i));
        }
        return data;
    }

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param id      artist id
     * @return artist object
     */

    @Override
    public Artist getArtist(Context context, String id) {
        for (Artist artist : mArtists) {
            if (artist.getId().equals(id))
                return artist;
        }

        return null;
    }

    @Override
    public Playlist getPlaylist(RestApi.UpdatePlaylist listener, String id) {
        return null;
    }

    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id      artist id
     * @return Arraylist of similar artists
     */
    @Override
    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        ArrayList<Artist> related;
        if (id.equals("1") || id.equals("6"))
            related = new ArrayList<>(mArtists.subList(10, 16));
        else if (id.equals("2") || id.equals("7"))
            related = new ArrayList<>(mArtists.subList(16, 22));
        else if (id.equals("3") || id.equals("8"))
            related = new ArrayList<>(mArtists.subList(22, 28));
        else if (id.equals("4") || id.equals("9"))
            related = new ArrayList<>(mArtists.subList(28, 34));
        else if (id.equals("5") || id.equals("10"))
            related = new ArrayList<>(mArtists.subList(34, 40));
        else
            related = new ArrayList<>();
        return related;
    }

    /**
     * Get the current user’s followed artists
     *
     * @param listener
     * @param type     current type, can be artist or user
     * @param limit    he maximum number of items to return
     * @param after    the last artist ID retrieved from the previous request
     * @return list of followed artists
     */
    @Override
    public ArrayList<Artist> getFollowedArtists(RestApi.UpdateArtistsLibrary listener, String type, int limit, String after) {
        ArrayList<Artist> followedArtists = Constants.currentUser.getFollowingArtists();
        ArrayList<Artist> returnedArtists = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, followedArtists.size()); i++) {
            returnedArtists.add(followedArtists.get(i));
        }
        listener.updateArtists(returnedArtists);
        return returnedArtists;
    }

    @Override
    public ArrayList<Playlist> getCurrentUserPlaylists(RestApi.UpdatePlaylistsLibrary listener, int offset, int limit) {
        ArrayList<Playlist> followedPlaylists = Constants.currentUser.getAllPlaylists();
        ArrayList<Playlist> returnedPlaylists = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, followedPlaylists.size()); i++) {
            returnedPlaylists.add(followedPlaylists.get(i));
        }
        listener.updatePlaylists(returnedPlaylists);
        return returnedPlaylists;
    }

    @Override
    public ArrayList<Track> getUserSavedTracks(RestApi.UpdateSavedTracks listener, int offset, int limit) {
        ArrayList<Track> savedTracks = Constants.currentUser.getLikedSongs();
        ArrayList<Track> returnedTracks = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, savedTracks.size()); i++) {
            returnedTracks.add(savedTracks.get(i));
        }
        listener.updateTracks(returnedTracks);
        return returnedTracks;
    }

    @Override
    public ArrayList<Track> getRecommendedTracks(RestApi.UpdateExtraSongs listener, int offset, int limit) {
        ArrayList<Track> recommendedTracks = new ArrayList<>();
        ArrayList<Track> currentTracks = new ArrayList<>();
        for (Track track : mTracks) {
            if (!Constants.currentUser.checkLikedSong(track)) currentTracks.add(track);
        }

        for (int i = offset; i < Math.min(offset + limit, currentTracks.size()); i++) {
            recommendedTracks.add(currentTracks.get(i));
        }

        listener.updateExtra(recommendedTracks);

        return recommendedTracks;
    }

    @Override
    public int getOwnedPlaylistsNumber(Context context) {
        return 0;
    }

    /**
     * Add the current user as a followers of one or more artists or other users
     *
     * @param context activity context
     * @param type    the type of what will be followed, can be artist or user
     * @param ids     array of users or artists ids
     */
    @Override
    public void followArtistsOrUsers(Context context, String type, ArrayList<String> ids) {
        for (String id : ids) {
            Artist artist = getArtist(context, id);
            if (artist != null) Constants.currentUser.followArtist(artist);
        }
    }

    /**
     * Remove the current user as a follower of one or more artists or other users
     *
     * @param context activity context
     * @param type    the type of what will be unFollowed, can be artist or user
     * @param ids     array of users or artists ids
     */
    @Override
    public void unFollowArtistsOrUsers(Context context, String type, ArrayList<String> ids) {
        for (String id : ids) {
            Artist artist = getArtist(context, id);
            if (artist != null) Constants.currentUser.unFollowArtist(artist);
        }
    }

    @Override
    public void deletePlaylist(Context context, String id) {

    }

    @Override
    public void createPlaylist(Context context, String name) {

    }

    @Override
    public void addTrackToPlaylist(Context context, String playlistId, String trackId) {
    }

    /**
     * Check to see if the current user is following an artist or more or other users
     *
     * @param context activity context
     * @param type    the type of the checked objects, can be artist or user
     * @param ids     array of users or artists ids
     * @return array of boolean
     */
    @Override
    public ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids) {
        ArrayList<Boolean> checkArray = new ArrayList<>();
        for (String id : ids) {
            Artist artist = getArtist(context, id);
            if (artist != null) checkArray.add(Constants.currentUser.checkFollowing(artist));
        }
        return checkArray;
    }

    /**
     * Get a list of recommended artist for the current user
     *
     * @param context activity context
     * @param type    artist or user
     * @param offset  the beginning of the items
     * @param limit   the maximum number of items to return
     * @return list of recommended artists
     */
    @Override
    public ArrayList<Artist> getRecommendedArtists(Context context, String type, int offset, int limit) {
        ArrayList<Artist> recommendedArtists = new ArrayList<>();
        ArrayList<Artist> currentArtists = new ArrayList<>();
        for (Artist artist : mArtists) {
            if (!Constants.currentUser.checkFollowing(artist)) currentArtists.add(artist);
        }

        for (int i = offset; i < Math.min(offset + limit, currentArtists.size()); i++) {
            recommendedArtists.add(currentArtists.get(i));
        }
        try {
            RestApi.UpdateAddArtists listener = (RestApi.UpdateAddArtists) context;
            listener.updateGetRecommendedArtists(recommendedArtists);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return recommendedArtists;

    }

    /**
     * Get information for a single album.
     *
     * @param id album id
     * @return album object
     */
    @Override
    public Album getAlbum(RestApi.UpdateAlbum listener, String id) {
        for (Album album : mAlbums) {
            if (album.getAlbumId().equals(id))
                return album;
        }

        return null;
    }

    /**
     * Get information about an album’s tracks.
     * Optional parameters can be used to limit the number of tracks returned.
     *
     * @param context activity context
     * @param id      album id
     * @param offset  the beginning of the tracks list
     * @param limit   the maximum number of tracks to get
     * @return array of album tracks
     */
    @Override
    public ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit) {
        Album album = getAlbum(null, id);
        if (album != null) return album.getAlbumTracks();
        return null;
    }

    /**
     * Save one or more albums to the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     */
    @Override
    public void saveAlbumsForUser(Context context, ArrayList<String> ids) {
        for (String id : ids) {
            Album album = getAlbum(null, id);
            if (album != null) Constants.currentUser.saveAlbum(album);
        }
    }

    /**
     * Remove one or more albums from the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     */
    @Override
    public void removeAlbumsForUser(Context context, ArrayList<String> ids) {
        for (String id : ids) {
            Album album = getAlbum(null, id);
            if (album != null) Constants.currentUser.removeAlbum(album);
        }
    }

    /**
     * Check if one or more albums is already saved in the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     * @return array of booleans, true for found and false for not found
     */
    @Override
    public ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids) {
        ArrayList<Boolean> checkArray = new ArrayList<>();
        for (String id : ids) {
            Album album = getAlbum(null, id);
            if (album != null) checkArray.add(Constants.currentUser.checkSavedAlbum(album));
        }
        return checkArray;
    }

    /**
     * Search for a specific artist
     *
     * @param context Activity context
     * @param q       Query to search for
     * @param offset  The index of the first result to return
     * @param limit   Maximum number of results to return
     * @return List of search result artists
     */
    @Override
    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        ArrayList<Artist> found = new ArrayList<>();
        final RestApi.UpdateSearchArtists listener = (RestApi.UpdateSearchArtists) context;

        for (Artist artist : mArtists) {
            if (Pattern.compile(Pattern.quote(q), Pattern.CASE_INSENSITIVE).matcher(artist.getArtistName()).find())
                found.add(artist);
        }

        ArrayList<Artist> searchResult = new ArrayList<>();
        for (int i = offset; i < Math.min(found.size(), offset + limit); i++) {
            searchResult.add(found.get(i));
        }
        listener.updateSuccess(searchResult, q);
        return searchResult;
    }


    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param listener
     * @param offset   The index of the first object to return
     * @param limit    The maximum number of objects to return
     * @return List of saved albums
     */
    @Override
    public ArrayList<Album> getUserSavedAlbums(RestApi.UpdateAlbumsLibrary listener, int offset, int limit) {
        ArrayList<Album> savedAlbums = Constants.currentUser.getSavedAlbums();
        ArrayList<Album> returnedAlbums = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, savedAlbums.size()); i++) {
            returnedAlbums.add(savedAlbums.get(i));
        }
        listener.updateAlbums(returnedAlbums);
        return returnedAlbums;
    }

    /**
     * handles promoting user to premium
     *
     * @param context holds context of activity
     * @param root    holds root view of fragment
     * @param token   holds token of user
     * @return returns true if promoted
     */
    @Override
    public boolean promotePremium(final Context context, View root, String token) {
        Constants.currentUser.setPremuim(true);
        return true;
    }

    /**
     * get users followers
     *
     * @param context context of the activity
     * @param token
     * @return arraylist of container of followers
     */
    @Override
    public boolean checkPremiumToken(Context context, String token) {
        return false;
    }

    /**
     * handles sending device token to receive notifications
     *
     * @param context        holds context of activity
     * @param register_token holds device register token
     * @return returns true if success
     */
    @Override
    public boolean sendRegisterToken(Context context, String register_token) {
        return false;
    }

    /**
     * handles getting notifications history API
     *
     * @param context holds context of activity
     * @param token   holds user token
     * @return returns true if success
     */
    @Override
    public boolean getNotificationHistory(Context context, String token) {
        return false;
    }

    /**
     * get users followers
     *
     * @param context context of the activity
     * @return arraylist of container of followers
     */
    @Override
    public ArrayList<Container> getProfileFollowers(Context context) {
        return null;
    }

    /**
     * get users who current user follow them
     *
     * @param context context of the activity
     * @return arraylist of container of users who follow the current user
     */
    @Override
    public ArrayList<Container> getProfileFollowing(Context context) {
        ArrayList<Container> followers = new ArrayList<>();
        followers.add(new Container("Adele", "200,700 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.adele)));
        followers.add(new Container("Amr Diab", "500,200 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.amr)));
        followers.add(new Container("Imagine dragons", "300,300 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.imagine)));
        followers.add(new Container("Alan Walker", "80,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.alan)));
        followers.add(new Container("ED Sheeran", "12,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.ed)));
        followers.add(new Container("Asala ", "30,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.assala)));
        followers.add(new Container("Bahaa Sultan", "21,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.bahaa)));
        followers.add(new Container("angham", "11,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.angham)));
        followers.add(new Container("Thunder and Tomorrow", "2100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.halsey)));
        followers.add(new Container("Selena", "28,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.selena)));
        followers.add(new Container("Smoke Grenades", "5,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.jannat)));
        followers.add(new Container("Wael Jassar", "260 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.wael)));
        followers.add(new Container("Abo", "7,730 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.abu)));
        followers.add(new Container("Hamza Nemra", "4,700 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.hamza)));
        return followers;
    }

    /**
     * get current user profile
     *
     * @param context          context of the activity
     * @param settingsFragment the fragment which called this function
     * @return user profile
     */
    @Override
    public Profile getCurrentUserProfile(Context context, Fragment settingsFragment, String id) {
        return null;
    }

    @Override
    public int getNumberOfLikedSongs(RestApi.UpdateLikedSongsNumber listener) {
        listener.updateNumber(Constants.currentUser.getLikedSongs().size());
        return Constants.currentUser.getLikedSongs().size();
    }

    /**
     * get current user playlists
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment which called this function
     * @return ArrayList of Container of User's playlists
     */
    @Override
    public ArrayList<Container> getCurrentUserPlaylists(Context context, FragmentProfile fragmentProfile, String id) {
        return null;
    }

    /**
     * get users who the current user follow them
     *
     * @param context                  context of the activity
     * @param profileFollowersFragment the fragment which called this function
     * @return ArrayList of Container current user following
     */
    @Override
    public ArrayList<Container> getCurrentUserFollowing(Context context, ProfileFollowersFragment profileFollowersFragment, String id) {
        return null;
    }

    /**
     * get a list of current user followers
     *
     * @param context                  context of the activity
     * @param profileFollowersFragment the fragment the function is called from
     * @return ArrayList of Container of Followers
     */
    @Override
    public ArrayList<Container> getCurrentUserFollowers(Context context, ProfileFollowersFragment profileFollowersFragment, String id) {
        ArrayList<Container> followers = new ArrayList<>();
        followers.add(new Container("HuffPost", "2,700 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.images)));
        followers.add(new Container("Majeestic Casual", "5,200 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.images3)));
        followers.add(new Container("beautiful", "3,300 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.download)));
        followers.add(new Container("Anders Stab Nielson", "800 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.download1)));
        followers.add(new Container("Simon Hoffman", "1200 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.images2)));
        followers.add(new Container("HouseNation", "3000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.abu)));
        followers.add(new Container("Chan Jun Jie", "2,100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.halsey)));
        followers.add(new Container("Efal Sayed", "1,100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.bahaa)));
        followers.add(new Container("Michelle Choi", "2100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.halsey)));
        followers.add(new Container("Brandon Bay", "2800 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.selena)));
        followers.add(new Container("Ryan Jones", "500 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.hamza)));
        followers.add(new Container("MTV Brasil", "26 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.jannat)));
        followers.add(new Container("Sonny Lind", "773 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.wael)));
        followers.add(new Container("Oranges", "470 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.angham)));
        followers.add(new Container("greate Artist", "1,200 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.images)));
        followers.add(new Container("Kevin Cross", "1,523 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.images3)));
        followers.add(new Container("Craige Dewart", "4,444 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.download)));
        followers.add(new Container("Wes Ley", "832 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.download1)));
        followers.add(new Container("Jasper Oh", "1328 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.images2)));
        followers.add(new Container("Kevin Enhorn", "3034 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.abu)));
        followers.add(new Container("Mark Springer", "6,100 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.halsey)));
        followers.add(new Container("anghami", "11,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.bahaa)));
        followers.add(new Container("Ahmed Ali Sheikh", "110 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.halsey)));
        followers.add(new Container("selena", "28,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.selena)));
        followers.add(new Container("Martin kepic", "0 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.hamza)));
        followers.add(new Container("An ther", "26,000 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.jannat)));
        followers.add(new Container("Ketan Patel", "773 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.wael)));
        followers.add(new Container("Dani Hecht", "470 " + context.getResources().getString(R.string.Followers), Utils.convertToBitmap(R.drawable.angham)));

        return followers;
    }

    /**
     * get number of user followers
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of followers
     */
    @Override
    public String getNumbersoUserFollowers(Context context, FragmentProfile fragmentProfile, String id) {
        return null;
    }

    private static String currTrackId = "dlsfk;jl;esafdoilfd";
    private static String currContextType = "playlist";
    private static String playingContextId = "ewoirgwepdoed";

    @Override
    public void getCurrPlaying(Context context) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        listener.getCurrPlayingTrackSuccess(currTrackId);

    }

    /**
     * @param context        context of the activity
     * @param artistAlbums   instance of artistAlbums fragment
     * @param name           name of the album
     * @param image          string of the image
     * @param albumType      album or single
     * @param copyRights     copyright of the album
     * @param copyRightsType p or c
     * @param bitmap         bitmab of the image
     */
    @Override
    public void createAlbum(Context context, ArtistAlbums artistAlbums, String name, String image, String albumType, String copyRights, String copyRightsType, Bitmap bitmap) {
        RestApi.updateUiArtistAlbums listener = (RestApi.updateUiArtistAlbums) context;
        listener.onAddAlbumSuccess(artistAlbums, "", name, "mnk;m", bitmap);
    }

    /**
     * @param context      context of the activity
     * @param artistAlbums instance of ArtistAlbums
     * @param id           id of the album
     * @param pos          pos of the album in the adapter
     */
    @Override
    public void deleteAlbum(Context context, ArtistAlbums artistAlbums, String id, int pos) {
        RestApi.updateUiArtistAlbums listener = (RestApi.updateUiArtistAlbums) context;
        listener.onDelAlbumSuccess(artistAlbums, id, pos);
    }

    /**
     * @param context      context of the activity
     * @param artistAlbums instance of artistAlbums fragment
     * @param id           id of the album
     * @param pos          pos of the album in the adapter
     * @param name         new name of the album
     */
    @Override
    public void renameAlbum(Context context, ArtistAlbums artistAlbums, String id, int pos, String name) {
        RestApi.updateUiArtistAlbums listener = (RestApi.updateUiArtistAlbums) context;
        listener.onRenameAlbumSuccess(artistAlbums, pos, name);
    }

    /**
     * @param context         context of the activity
     * @param fragmentProfile instance of fragmentProfile
     * @param name            new name of user
     * @param image           string of new image of user
     */
    @Override
    public void editProfile(Context context, FragmentProfile fragmentProfile, String name, String image) {

    }

    /**
     * @param context      context of the activity
     * @param artistAlbums instance of ArtistAlbums
     * @param albumType    album or sinlge
     * @return ArrayList<Container> of current artist's albums
     */
    @Override
    public ArrayList<Container> getCurrentArtistAlbums(Context context, ArtistAlbums artistAlbums, String albumType) {
        return mArtistAlbums;
    }

    /**
     * @param context           context of the activity
     * @param artistAlbumTracks instance of ArtistAlbumTracks fragmnt
     * @param id                id of the track
     * @return ArrayList<Container> of album's tracks
     */
    @Override
    public ArrayList<Container> getAlbumTracks(Context context, ArtistAlbumTracks artistAlbumTracks, String id) {
        return mArtistTracks;
    }

    /**
     * @param context           context of the activity
     * @param artistAlbumTracks instance of ArtistAlbumTracks fragmnt
     * @param id                id of the track
     * @param pos               pos of the track in the adaper
     */
    @Override
    public void deleteTrack(Context context, ArtistAlbumTracks artistAlbumTracks, String id, int pos) {
        RestApi.updateUiArtistAlbumTracks listener = (RestApi.updateUiArtistAlbumTracks) context;
        listener.onDelTrackSuccess(artistAlbumTracks, id, pos);
    }

    /**
     * @param context           context of the activity
     * @param artistAlbumTracks instance of ArtistAlbumTracks fragmnt
     * @param id                id of the track
     * @param pos               pos of the track in the adaper
     * @param name              new name of the track
     */
    @Override
    public void renameTrack(Context context, ArtistAlbumTracks artistAlbumTracks, String id, int pos, String name) {
        RestApi.updateUiArtistAlbumTracks listener = (RestApi.updateUiArtistAlbumTracks) context;
        listener.onRenameTrackSuccess(artistAlbumTracks, pos, name);
    }

    /**
     * @param context     context of the activity
     * @param songEncoded string of encoded song
     * @param albumId     album id
     */
    @Override
    public void createTrack(Context context, String songEncoded, String albumId) {

    }

    private com.example.symphonia.Entities.Context playingContext;

    private Track getTrackOfId(String id) {
        for (int i = 0; i < mPopularContext.size(); i++) {
            for (int j = 0; j < mPopularContext.get(i).getTracks().size(); j++) {
                mPopularContext.get(i).getTracks().get(j).getId();
                if (mPopularContext.get(i).getTracks().get(j).getId().matches(id)) {
                    playingContext = mPopularContext.get(i);
                    return mPopularContext.get(i).getTracks().get(j);
                }
            }
        }
        for (int i = 0; i < mRecentContext.size(); i++) {
            for (int j = 0; j < mRecentContext.get(i).getTracks().size(); j++) {
                if (mRecentContext.get(i).getTracks().get(j).getId().matches(id)) {
                    playingContext = mRecentContext.get(i);
                    return mRecentContext.get(i).getTracks().get(j);
                }
            }
        }
        for (int i = 0; i < mRandomContext.size(); i++) {
            for (int j = 0; j < mRandomContext.get(i).getTracks().size(); j++) {
                if (mRandomContext.get(i).getTracks().get(j).getId().matches(id)) {
                    playingContext = mRandomContext.get(i);
                    return mRandomContext.get(i).getTracks().get(j);
                }
            }
        }
        return null;
    }

    @Override
    public void getTrack(Context context, String id) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        Utils.currContextType = currContextType;
        Utils.currTrack = getTrackOfId(id);
        Utils.playingContext = playingContext;
        listener.getTrackSuccess();
    }

    /**
     * get number of users that user follow
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of following
     */
    @Override
    public String getNumbersoUserFollowing(Context context, FragmentProfile fragmentProfile, String id) {
        return null;
    }

    /**
     * get number of playlists of current user
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of playlists
     */
    @Override
    public String getNumberofUserPlaylists(Context context, FragmentProfile fragmentProfile) {
        return null;
    }

    /**
     * get current user playlists
     *
     * @param context                  context of the activity
     * @param profilePlaylistsFragment the fragment the function is called from
     * @return current user playlists
     */
    @Override
    public ArrayList<Container> getAllCurrentUserPlaylists(Context context, ProfilePlaylistsFragment profilePlaylistsFragment, String id) {
        return null;
    }

    /**
     * follow playlist
     *
     * @param context                  context of the activity
     * @param bottomSheetDialogProfile the fragment the function is called from
     */
    @Override
    public void followPlaylist(Context context, BottomSheetDialogProfile bottomSheetDialogProfile) {

    }

    /**
     * get number of artists in search result
     *
     * @return int artistsCount
     */
    @Override
    public int getArtistsCount() {
        return artistsCount;
    }

    /**
     * get number of profiles in search result
     *
     * @return int profilesCount
     */
    @Override
    public int getProfilessCount() {
        return profilesCount;
    }

    /**
     * get number of playlists in search result
     *
     * @return int playlistsCount
     */
    @Override
    public int getPlaylistsCount() {
        return playlistsCount;
    }

    /**
     * get number of genres in search result
     *
     * @return int genresCount
     */
    @Override
    public int getGenresCount() {
        return genresCount;
    }

    /**
     * get number of songs in search result
     *
     * @return int songsCount
     */
    @Override
    public int getSongsCount() {
        return songsCount;
    }

    /**
     * get number of albums in search result
     *
     * @return int albumssCount
     */
    @Override
    public int getAlbumsCount() {

        return albumssCount;
    }

    @Override
    public void getQueue(Context context) {
        getTracksOfPlaylist(context, playingContextId, null);
    }

    @Override
    public void playNext(Context context) {
        int pos = Utils.getPosInPlaying(Utils.currTrack.getId());
        if (Utils.playingContext.getTracks().size() - 1 > pos)
            pos++;
        Utils.currTrack = Utils.playingContext.getTracks().get(pos);
        getCurrPlaying(context);
    }

    @Override
    public void playPrev(Context context) {
        int pos = Utils.getPosInPlaying(Utils.currTrack.getId());
        if (pos > 0)
            pos--;
        Utils.currTrack = Utils.playingContext.getTracks().get(pos);
        getCurrPlaying(context);
    }

    @Override
    public void checkSaved(Context context, String ids, PlaylistFragment playlistFragment) {
        final updateUiPlaylists listener = (updateUiPlaylists) context;
        if (Utils.displayedContext != null)
            Utils.displayedContext.getTracks().get(0).setLiked(false);
        Utils.playingContext.getTracks().get(0).setLiked(false);
        for (int i = 1; i < Utils.playingContext.getTracks().size(); i++) {
            if (Utils.displayedContext != null) {
                Utils.displayedContext.getTracks().get(i).setLiked(false);
            }
            Utils.playingContext.getTracks().get(i).setLiked(false);
        }
        listener.updateUicheckSaved(playlistFragment);

    }

    @Override
    public void saveTrack(Context context, String id) {

    }

    @Override
    public void removeFromSaved(Context context, String id) {

    }
}
