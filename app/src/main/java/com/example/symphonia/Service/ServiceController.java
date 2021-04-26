package com.example.symphonia.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Category;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbumTracks;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbums;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;

import java.util.ArrayList;

import static com.example.symphonia.Constants.DEBUG_STATUS;

/**
 * Class that Controls which service type will be used(REST APIs or MockService)
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
 */

public class ServiceController {
    /**
     * new object of controller class to decide which service to be used
     */
    private static final ServiceController REST_CLIENT = new ServiceController();
    /**
     * object of parent(abstract class) for supplying app with data
     */
    private final APIs mSupplier;

    /**
     * constructor of ServiceController that decides which type service to be used
     */
    private ServiceController() {
        //checks if mode is debugging, then use mockService
        if (DEBUG_STATUS)
            mSupplier = new MockService();
        else
            mSupplier = new RestApi();
    }

    /**
     * getter for instance of class object
     *
     * @return return instance of class object
     */
    public static ServiceController getInstance() {
        return REST_CLIENT;
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
    public boolean logIn(final Context context, String username, String password, boolean mType) {
        return mSupplier.logIn(context, username, password, mType);
    }

    /**
     * holds facebook login api request
     * @param context holds context of requested page
     * @param fb_token holds facebook token
     * @param image holds user's image
     * @return returns true if success
     */
    public boolean facebookLogin(Context context, String fb_token, String image) {
        return mSupplier.facebookLogin(context, fb_token, image);
    }

    /**
     * handles forget password API
     * @param context holds context of requested page
     * @param email holds user's email
     * @return returns true if success
     */
    public boolean forgetPassword(final Context context, String email) {
        return mSupplier.forgetPassword(context, email);
    }

    /**
     * handles resetting password API
     * @param context holds context of requested page
     * @param password holds new password
     * @param token holds user's token of email
     * @return returns true if success
     */
    public boolean resetPassword(final Context context, final String password, final String token) {
        return mSupplier.resetPassword(context, password, token);
    }

    /**
     * handles apply artist API
     * @param context holds context of requested page
     * @param token holds user's token
     * @return returns true if success
     */
    public boolean applyArtist(final Context context, final String token) {
        return mSupplier.applyArtist(context, token);
    }

    public void getCurrPlaying(Context context) {
        mSupplier.getCurrPlaying(context);
    }

    /**
     * checks if email is already signed in database or not
     *
     * @param context holds context of activity that called this method
     * @param email   email of user
     * @param mType   type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    public boolean checkEmailAvailability(final Context context, String email, boolean mType) {
        return mSupplier.checkEmailAvailability(context, email, mType);
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
    public boolean signUp(final Context context, boolean mType, String email, String password,
                          String DOB, String gender, String name) {
        return mSupplier.signUp(context, mType, email, password, DOB, gender, name);
    }


    /**
     * getter for recently-player playlist
     *
     * @param context  context of hosting activity
     * @param fragment fragment of user
     * @return recently-player  playlist
     */
    public ArrayList<com.example.symphonia.Entities.Context> getRecentPlaylists(Context context, HomeFragment fragment) {
        return mSupplier.getRecentPlaylists(context, fragment);
    }

    /**
     * getter for random playlist
     *
     * @param context      context of hosting activity
     * @param homeFragment token of user
     * @return random  playlist
     */
    public ArrayList<com.example.symphonia.Entities.Context> getRandomPlaylists(Context context, HomeFragment homeFragment) {
        return mSupplier.getRandomPlaylists(context, homeFragment);
    }

    /**
     * getter for made-for-you playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return made-for-you  playlist
     */
    public ArrayList<com.example.symphonia.Entities.Context> getMadeForYoutPlaylists(Context context, String mToken) {
        return mSupplier.getMadeForYouPlaylists(context, mToken);
    }

    /**
     * getter for popular playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return popular  playlist
     */
    public ArrayList<com.example.symphonia.Entities.Context> getPopularPlaylists(Context context, String mToken) {
        return mSupplier.getPopularPlaylists(context, mToken);
    }

    /**
     * get the recent searches of the user
     *
     * @param context context of the activity
     * @return ArrayList of Container of recent searches
     */
    public ArrayList<Container> getResentResult(Context context) {
        return mSupplier.getResentResult(context);
    }

    /**
     * get seven or less results of search
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of Container
     */
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        return mSupplier.getResultsOfSearch(context, searchWord);
    }

    /**
     * get a list of user categories
     *
     * @param context context of the activity
     * @return ArrayList of Category of categories
     */
    public ArrayList<Category> getCategories(Context context) {
        return mSupplier.getCategories(context);
    }

    /**
     * get a lsit of user genres
     *
     * @param context context of the activity
     * @return ArrayList of Category of genres
     */
    public ArrayList<Category> getGenres(Context context) {
        return mSupplier.getGenres(context);
    }

    /**
     * get a list of artists of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of artists
     */
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        return mSupplier.getArtists(context, searchWord);
    }

    /**
     * get a list of songs of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of songs
     */
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        return mSupplier.getSongs(context, searchWord);
    }

    /**
     * get a list of albums of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of albums
     */
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        return mSupplier.getAlbums(context, searchWord);

    }

    /**
     * get a list of genres of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of genres
     */
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        return mSupplier.getGenresAndMoods(context, searchWord);

    }

    /**
     * get a list of playlists of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of playlists
     */
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        return mSupplier.getPlaylists(context, searchWord);

    }

    /**
     * get a list of profiles of the search results
     *
     * @param context    context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of profiles
     */
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        return mSupplier.getProfiles(context, searchWord);

    }

    /**
     * ensure that the recent searches won't be returned again
     *
     * @param context  context of the activity
     * @param position position of the element which is deleted
     */
    public void removeOneRecentSearch(Context context, int position) {
        mSupplier.removeOneRecentSearch(context, position);
    }

    /**
     * ensure to return empty list when recent searches is required
     *
     * @param context context of the activity
     */
    public void removeAllRecentSearches(Context context) {
        mSupplier.removeAllRecentSearches(context);
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
    public ArrayList<Artist> getFollowedArtists(RestApi.UpdateArtistsLibrary listener, String type, int limit, String after) {
        return mSupplier.getFollowedArtists(listener, type, limit, after);
    }

    /**
     * Add the current user as a followers of one or more artists or other users
     *
     * @param context activity context
     * @param type    the type of what will be followed, can be artist or user
     * @param ids     array of users or artists ids
     */
    public void followArtistsOrUsers(Context context, String type, ArrayList<String> ids) {
        mSupplier.followArtistsOrUsers(context, type, ids);
    }

    /**
     * Remove the current user as a follower of one or more artists or other users
     *
     * @param context activity context
     * @param type    the type of what will be unFollowed, can be artist or user
     * @param ids     array of users or artists ids
     */
    public void unFollowArtistsOrUsers(Context context, String type, ArrayList<String> ids) {
        mSupplier.unFollowArtistsOrUsers(context, type, ids);
    }

    /**
     * Check to see if the current user is following an artist or more or other users
     *
     * @param context activity context
     * @param type    the type of the checked objects, can be artist or user
     * @param ids     array of users or artists ids
     * @return array of boolean
     */
    public ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids) {
        return mSupplier.isFollowing(context, type, ids);
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
    public ArrayList<Artist> getRecommendedArtists(Context context, String type, int offset, int limit) {
        return mSupplier.getRecommendedArtists(context, type, offset, limit);
    }

    public ArrayList<Artist> searchArtist(Context context, String q) {
        return mSupplier.searchArtist(context, q, 0, 20);
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
    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        return mSupplier.searchArtist(context, q, offset, limit);
    }

    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id      artist id
     * @return Arraylist of similar artists
     */
    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        return mSupplier.getArtistRelatedArtists(context, id);
    }

    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param listener
     * @param offset   The index of the first object to return
     * @param limit    The maximum number of objects to return
     * @return List of saved albums
     */
    public ArrayList<Album> getUserSavedAlbums(RestApi.UpdateAlbumsLibrary listener, int offset, int limit) {
        return mSupplier.getUserSavedAlbums(listener, offset, limit);
    }

    public ArrayList<Playlist> getCurrentUserPlaylists(RestApi.UpdatePlaylistsLibrary listener, int offset, int limit) {
        return mSupplier.getCurrentUserPlaylists(listener, offset, limit);
    }

    public ArrayList<Track> getUserSavedTracks(RestApi.UpdateSavedTracks listener, int offset, int limit) {
        return mSupplier.getUserSavedTracks(listener, offset, limit);
    }

    public ArrayList<Track> getRecommendedTracks(RestApi.UpdateExtraSongs listener, int offset, int limit) {
        return mSupplier.getRecommendedTracks(listener, offset, limit);
    }

    public int getNumberOfLikedSongs(RestApi.UpdateLikedSongsNumber listener) {
        return mSupplier.getNumberOfLikedSongs(listener);
    }


    /**
     * return a list of popular playlists
     *
     * @param context context of the activity
     * @return a ArrayList of Container of Popular playlists
     */
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        return mSupplier.getAllPopularPlaylists(context);
    }

    /**
     * return four popular playlists
     *
     * @param context context of the activity
     * @return return four popular playlists
     */
    public ArrayList<Container> getFourPlaylists(Context context) {
        return mSupplier.getFourPlaylists(context);
    }

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param id      artist id
     * @return artist object
     */
    public Artist getArtist(Context context, String id) {
        return mSupplier.getArtist(context, id);
    }

    /**
     * Get information for a single album.
     *
     * @param id      album id
     * @return album object
     */
    public Album getAlbum(RestApi.UpdateAlbum listener, String id) {
        return mSupplier.getAlbum(listener, id);
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
    public ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit) {
        return mSupplier.getAlbumTracks(context, id, offset, limit);
    }

    /**
     * Save one or more albums to the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     */
    public void saveAlbumsForUser(Context context, ArrayList<String> ids) {
        mSupplier.saveAlbumsForUser(context, ids);
    }

    /**
     * Remove one or more albums from the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     */
    public void removeAlbumsForUser(Context context, ArrayList<String> ids) {
        mSupplier.removeAlbumsForUser(context, ids);
    }

    /**
     * Check if one or more albums is already saved in the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids     array of albums ids
     * @return array of booleans, true for found and false for not found
     */
    public ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids) {
        return mSupplier.checkUserSavedAlbums(context, ids);
    }

    /**
     * handles promoting user to premium
     *
     * @param context holds context of activity
     * @param root    holds root view of fragment
     * @param token   holds token of user
     * @return returns true if promoted
     */
    public boolean promotePremium(final Context context, View root, String token) {
        return mSupplier.promotePremium(context, root, token);
    }

    /**
     * handles check premium token message API
     * @param context holds context of activity
     * @param token holds email token
     * @return returns true if success
     */
    public boolean checkPremiumToken(Context context, String token) {
        return mSupplier.checkPremiumToken(context, token);
    }

    /**
     * handles sending device token to receive notifications
     * @param context holds context of activity
     * @param register_token holds device register token
     * @return returns true if success
     */
    public boolean sendRegisterToken(Context context, String register_token) {
        return mSupplier.sendRegisterToken(context, register_token);
    }

    /**
     * handles getting notifications history API
     * @param context holds context of activity
     * @param token holds user token
     * @return returns true if success
     */
    public boolean getNotificationHistory(Context context, String token) {
        return mSupplier.getNotificationHistory(context, token);
    }

    /**
     * get users followers
     *
     * @param context context of the activity
     * @return arraylist of container of followers
     */
    public ArrayList<Container> getProfileFollowers(Context context) {
        return mSupplier.getProfileFollowers(context);
    }

    /**
     * get users who current user follow them
     *
     * @param context context of the activity
     * @return arraylist of container of users who follow the current user
     */
    public ArrayList<Container> getProfileFollowing(Context context) {
        return mSupplier.getProfileFollowing(context);
    }

    /**
     * get current user playlists
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment which called this function
     * @param id id of user
     * @return ArrayList of Container of User's playlists
     */
    public ArrayList<Container> getCurrentUserPlaylists(Context context, FragmentProfile fragmentProfile,String id) {
        return mSupplier.getCurrentUserPlaylists(context, fragmentProfile,id);
    }

    /**
     * get number of user followers
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @param id id of user
     * @return string of the number of followers
     */
    public String getNumbersoUserFollowers(Context context, FragmentProfile fragmentProfile,String id) {
        return mSupplier.getNumbersoUserFollowers(context, fragmentProfile,id);
    }

    /**
     * get number of users that user follow
     *
     * @param context         context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @param id id of user
     * @return string of the number of following
     */
    public String getNumbersoUserFollowing(Context context, FragmentProfile fragmentProfile,String id) {
        return mSupplier.getNumbersoUserFollowing(context, fragmentProfile,id);
    }

    /**
     * get a list of current user followers
     *
     * @param context                  context of the activity
     * @param profileFollowersFragment the fragment the function is called from
     * @param id id of user
     * @return ArrayList of Container of Followers
     */
    public ArrayList<Container> getCurrentUserFollowers(Context context, ProfileFollowersFragment profileFollowersFragment,String id) {
        return mSupplier.getCurrentUserFollowers(context, profileFollowersFragment,id);
    }

    /**
     * get number of profiles in search result
     *
     * @return int profiles Count
     */
    public int getProfilesCount() {
        return mSupplier.getProfilessCount();
    }

    /**
     * get number of playlists in search result
     *
     * @return int playlists Count
     */
    public int getPlaylistsCount() {
        return mSupplier.getPlaylistsCount();
    }

    /**
     * get number of artists in search result
     *
     * @return int artists Count
     */
    public int getArtistsCount() {
        return mSupplier.getArtistsCount();
    }

    /**
     * get number of albums in search result
     *
     * @return int albums Count
     */
    public int getAlbumsCount() {
        return mSupplier.getAlbumsCount();
    }

    /**
     * get number of genres in search result
     *
     * @return int genresCount
     */
    public int getGenresCount() {
        return mSupplier.getGenresCount();
    }

    /**
     * get number of songs in search result
     *
     * @return int songs Count
     */
    public int getSongsCount() {
        return mSupplier.getSongsCount();
    }

    /**
     * this function initialize the request to stream music
     *
     * @param context context of current activity
     */
    public void playTrack(Context context) {
        mSupplier.playTrack(context);
    }

    public void getTrack(Context context, String id) {
        mSupplier.getTrack(context, id);
    }

    /**
     * this function gets tracks of a certain playlist
     *
     * @param context          context of current activity
     * @param id               id of playlist
     * @param playlistFragment instance of the fragment to make the update in
     * @return array list of tracks
     */
    public ArrayList<Track> getTracksOfPlaylist(Context context, String id, PlaylistFragment playlistFragment) {
        return mSupplier.getTracksOfPlaylist(context, id, playlistFragment);
    }

    public void createPlaylist(Context context, String name) {
        mSupplier.createPlaylist(context, name);
    }

    public int getOwnedPlaylistsNumber(Context context) {
        return mSupplier.getOwnedPlaylistsNumber(context);
    }

    public Playlist getPlaylist(RestApi.UpdatePlaylist listener, String id) {
        return mSupplier.getPlaylist(listener, id);
    }

    public ArrayList<Track> getPlaylistTracks(RestApi.updateTracksNames listener, String id){
        return mSupplier.getPlaylistTracks(listener, id);
    }

    public void deletePlaylist(Context context, String id){
        mSupplier.deletePlaylist(context, id);
    }

    public void getQueue(Context context) {
        mSupplier.getQueue(context);
    }

    public void playNext(Context context) {
        mSupplier.playNext(context);
    }

    public void playPrev(Context context) {
        mSupplier.playPrev(context);
    }

    public void checkSaved(Context context, String ids, PlaylistFragment playlistFragment) {
        mSupplier.checkSaved(context, ids, playlistFragment);
    }

    public void saveTrack(Context context, String id) {
        mSupplier.saveTrack(context, id);
    }

    public void removeFromSaved(Context context, String id) {
        mSupplier.removeFromSaved(context, id);
    }

    /**
     *
     * @param context context of the activity
     * @param artistAlbums instance of artistAlbums fragment
     * @param name name of the album
     * @param image string of the image
     * @param albumType album or single
     * @param copyRights copyright of the album
     * @param copyRightsType p or c
     * @param bitmap bitmab of the image
     */
    public void createAlbum(Context context, ArtistAlbums artistAlbums, String name, String image, String albumType, String copyRights, String copyRightsType, Bitmap bitmap){
        mSupplier.createAlbum(context,artistAlbums,name,image,albumType,copyRights,copyRightsType,bitmap);
    }

    /**
     *
     * @param context context of the activity
     * @param artistAlbums instance of ArtistAlbums
     * @param id id of the album
     * @param pos pos of the album in the adapter
     */
    public void deleteAlbum(Context context,ArtistAlbums artistAlbums,String id,int pos){
        mSupplier.deleteAlbum(context,artistAlbums,id,pos);
    }

    /**
     *
     * @param context context of the activity
     * @param artistAlbums instance of ArtistAlbums
     * @param albumType album or sinlge
     * @return ArrayList<Container> of current artist's albums
     */
    public ArrayList<Container> getCurrentArtistAlbums(Context context, ArtistAlbums artistAlbums, String albumType){
        return mSupplier.getCurrentArtistAlbums(context,artistAlbums,albumType);
    }

    /**
     *
     * @param context context of the activity
     * @param artistAlbumTracks instance of ArtistAlbumTracks fragmnt
     * @param id id of the track
     * @return ArrayList<Container> of album's tracks
     */
    public ArrayList<Container> getAlbumTracks(Context context, ArtistAlbumTracks artistAlbumTracks, String id){
        return mSupplier.getAlbumTracks(context,artistAlbumTracks,id);
    }

    /**
     *
     * @param context context of the activity
     * @param artistAlbumTracks instance of ArtistAlbumTracks fragmnt
     * @param id id of the track
     * @param pos pos of the track in the adaper
     */
    public void deleteTrack(Context context,ArtistAlbumTracks artistAlbumTracks,String id,int pos){
        mSupplier.deleteTrack(context,artistAlbumTracks,id,pos);
    }
    public void addTrackToPlaylist(Context context, String playlistId, String trackId){
        mSupplier.addTrackToPlaylist(context, playlistId, trackId);
    }

}
