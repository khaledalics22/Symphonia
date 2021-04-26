package com.example.symphonia;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Copyright;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Entities.User;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbumTracks;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbums;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.Service.MockService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MockServiceTest {

    private MockService mockService;
    private Context appContext;
    private User user;


    @Before
    public void setUp() {
        Constants.currentUser = new User();

        mockService = new MockService();


/*        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("1", Utils.convertToBitmap(R.drawable.ragheb), "Ragheb Alama"));
        artists.add(new Artist("2", Utils.convertToBitmap(R.drawable.elissa), "Elissa"));
        artists.add(new Artist("3", Utils.convertToBitmap(R.drawable.angham), "Angham"));
        artists.add(new Artist("4", Utils.convertToBitmap(R.drawable.wael), "Wael Kfoury"));
        artists.add(new Artist("5", Utils.convertToBitmap(R.drawable.wael_gassar), "Wael Jassar"));*/

        /*ArrayList<Album> albums = new ArrayList<>();

        albums.add(new Album("6qqNVTkY8uBg9cP3Jd7DAH",
                "single",
                new ArrayList<Artist>(Collections.singletonList(artists.get(0))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 Darkroom/Interscope Records", "C"),
                        new Copyright("2020 Darkroom/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.no_time_to_die),
                "No Time To Die",
                "2020-02-13",
                new ArrayList<Track>()));

        albums.add(new Album("7eFyrxZRPqw8yvZXMUm88A",
                "album",
                new ArrayList<Artist>(Collections.singletonList(artists.get(1))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 Nay", "C"),
                        new Copyright("2018 Nay", "P"))),
                Utils.convertToBitmap(R.drawable.kol_hayaty),
                "Kol Hayaty",
                "2018-10-03",
                new ArrayList<Track>()));

        albums.add(new Album("2D1nEskDzLz38JiUeVK5mh",
                "single",
                new ArrayList<Artist>(Collections.singletonList(artists.get(2))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 MuzicUp", "C"),
                        new Copyright("2020 MuzicUp", "P"))),
                Utils.convertToBitmap(R.drawable.shamekh),
                "Shamekh",
                "2020-01-12",
                new ArrayList<Track>()));

        albums.add(new Album("0hZwt0aSEEiwUByVQuxntK",
                "album",
                new ArrayList<Artist>(Collections.singletonList(artists.get(3))),
                new ArrayList<Copyright>(Collections.singletonList(new Copyright("2012 Awakening Worldwide", "C"))),
                Utils.convertToBitmap(R.drawable.fogive_me),
                "Forgive Me",
                "2012-04-02",
                new ArrayList<Track>()));*/


        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
/*        user = new User("eslam1092@hotmail.com", "f3fgd", true, Utils.convertToBitmap(R.drawable.amr)
                , "Islam Ahmed", "1998-11-24", "male", true
                , 65500, 40, new ArrayList<User>()
                , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                , artists, albums, new ArrayList<Track>());*/


    }

    @Test
    public void LoginListenerFail() {
        assertFalse(mockService.logIn(appContext, "anything", "1234", true));
    }

    @Test
    public void LoginListenerSuccess() {
        assertTrue(mockService.logIn(appContext, "user1@symphonia.com", "12345678", true));
    }

    @Test
    public void LoginArtistFail() {
        assertFalse(mockService.logIn(appContext, "anything", "1234", false));
    }

    @Test
    public void LoginArtistSuccess() {
        assertTrue(mockService.logIn(appContext, "artist1@symphonia.com", "12345678", false));
    }

    @Test
    public void EmailAvailabilityArtistFail() {
        assertFalse(mockService.checkEmailAvailability(appContext, "artist1@symphonia.com", false));
    }

    @Test
    public void EmailAvailabilityArtistSuccess() {
        assertTrue(mockService.checkEmailAvailability(appContext, "artist14@symphonia.com", false));
    }

    @Test
    public void EmailAvailabilityListenerFail() {
        assertFalse(mockService.checkEmailAvailability(appContext, "user1@symphonia.com", true));
    }

    @Test
    public void EmailAvailabilityListenerSuccess() {
        assertTrue(mockService.checkEmailAvailability(appContext, "user14@symphonia.com", true));
    }


    @Test
    public void SignUpSuccessListener() {
        assertTrue(mockService.signUp(appContext, true, "email@gmail.com", "password"
                , "1999", "male", "Hossam Alaa"));
    }

    @Test
    public void SignUpSuccessArtist() {
        assertTrue(mockService.signUp(appContext, false, "email@gmail.com", "password"
                , "1999", "male", "Hossam Alaa"));
    }

    @Test
    public void ApplyPremiumSuccess() {
        assertTrue(mockService.promotePremium(appContext, new View(appContext), "Token"));
    }

  /* public void getFollowedArtistsSuccess() {
        Constants.currentUser = user;
        assertEquals(5, mockService.getFollowedArtists(appContext, "user", 20, null).size());
    }

    @Test
    public void getFollowedArtistsFail() {
        Constants.currentUser = user;
        assertNotEquals(5, mockService.getFollowedArtists(appContext, "user", 3, null).size());
    }

    @Test
    public void followArtistSuccess() {
        Constants.currentUser = user;
        mockService.followArtistsOrUsers(appContext, "artist" , new ArrayList<String>(Collections.singletonList("6")));
        assertEquals(6, mockService.getFollowedArtists(appContext, "user", 20, null).size());
    }

    @Test
    public void followArtistFail() {
        Constants.currentUser = user;
        mockService.followArtistsOrUsers(appContext, "artist", new ArrayList<String>(Collections.singletonList("3")));
        assertNotEquals(6, mockService.getFollowedArtists(appContext, "user", 20, null).size());
    }

    @Test
    public void unFollowArtistTest(){
        Constants.currentUser = user;
        mockService.unFollowArtistsOrUsers(appContext, "artist", new ArrayList<String>(Collections.singletonList("3")));
        assertEquals(4, mockService.getFollowedArtists(appContext, "user", 20, null).size());
    }*/
    /*@Test
    public void isFollowingSuccess() {
        Constants.currentUser = user;
        assertTrue(mockService.isFollowing(appContext, "artist", new ArrayList<String>(Collections.singletonList("3"))).get(0));
    }

    @Test
    public void isFollowingFail() {
        Constants.currentUser = user;
        assertFalse(mockService.isFollowing(appContext, "artist", new ArrayList<String>(Collections.singletonList("6"))).get(0));
    }

    @Test
    public void getRecommendedArtistsSuccess() {
        Constants.currentUser = user;
        assertEquals(8, mockService.getRecommendedArtists(appContext, "user", 0, 8).size());
    }

    @Test
    public void getRecommendedArtistsFail() {
        Constants.currentUser = user;
        assertNotEquals(50, mockService.getRecommendedArtists(appContext, "user", 0, 50).size());
    }

    @Test
    public void getArtistSuccess(){
        assertEquals("5", mockService.getArtist(appContext, "5").getId());
    }

    @Test
    public void getArtistFail(){
        assertNull(mockService.getArtist(appContext, "500"));
    }
*/


   /* @Test
    public void getAlbumSuccess(){
        assertEquals("2D1nEskDzLz38JiUeVK5mh", mockService.getAlbum(appContext, "2D1nEskDzLz38JiUeVK5mh").getAlbumId());
    }

    @Test
    public void getAlbumFail(){
        assertNull(mockService.getAlbum(appContext, "500"));
    }
*/

  /*  @Test
    public void getArtistRelatedArtistsTest(){
        assertEquals(6, mockService.getArtistRelatedArtists(appContext, "1").size());
    }

    @Test
    public void searchArtistTest(){
        assertEquals("Ragheb Alama", mockService.searchArtist(appContext, "Ragheb Alama", 0, 20).get(0).getArtistName());
    }*/

   /* @Test
    public void getUserSavedAlbumsTest(){
        Constants.currentUser = user;
        assertEquals(4, mockService.getUserSavedAlbums(appContext, 0, 20).size());
    }

    @Test
    public void saveAlbumsTest() {
        Constants.currentUser = user;
        mockService.saveAlbumsForUser(appContext, new ArrayList<String>(Collections.singletonList("3JfSxDfmwS5OeHPwLSkrfr")));
        assertEquals(5, mockService.getUserSavedAlbums(appContext, 0, 20).size());
    }*/

   /* @Test
    public void removeAlbumsTest(){
        Constants.currentUser = user;
        mockService.removeAlbumsForUser(appContext, new ArrayList<String>(Collections.singletonList("7eFyrxZRPqw8yvZXMUm88A")));
        assertEquals(3, mockService.getUserSavedAlbums(appContext, 0, 20).size());
    }
*/
/*
    @Test
    public void checkAlbumsTest() {
        Constants.currentUser = user;
        assertTrue(mockService.checkUserSavedAlbums(appContext, new ArrayList<String>(Collections.singletonList("7eFyrxZRPqw8yvZXMUm88A"))).get(0));
    }
*/

    @Test
    public void getResultsOfSearchFail() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Gamal", "Album.Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        testedData.add(new Container("Samir Abo Elnil", "Artist", Utils.convertToBitmap(R.drawable.download)));
        testedData.add(new Container("Amr Diab", "Artist", Utils.convertToBitmap(R.drawable.download)));
        testedData.add(new Container("Silence Of Lambs", "Album.Little Mix", Utils.convertToBitmap(R.drawable.download1)));
        ArrayList<Container> comingData = mockService.getResultsOfSearch(appContext, "Am");
        assertNotEquals(comingData.size(), testedData.size());
    }
    @Test
    public void getResultsOfSearchSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Amr Diab", appContext.getResources().getString(R.string.Artist), Utils.convertToBitmap(R.drawable.amr)));
        ArrayList<Container> comingData = mockService.getResultsOfSearch(appContext, "Am");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }
    @Test
    public void getArtistsFail() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Amr Dia", appContext.getResources().getString(R.string.Artist), Utils.convertToBitmap(R.drawable.download)));
        ArrayList<Container> comingData = mockService.getArtists(appContext, "Am");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }
    @Test
    public void getArtistsSuccess() {
        String artist = appContext.getResources().getString(R.string.Artist);
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Adele", artist, Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("Amr Diab", artist, Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("Samir Abo Elnil", artist, Utils.convertToBitmap(R.drawable.download)));
        ArrayList<Container> comingData = mockService.getArtists(appContext, "A");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }
    @Test
    public void getSongsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Cat Sound", appContext.getResources().getString(R.string.Song) + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        ArrayList<Container> comingData = mockService.getSongs(appContext, "Th");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }
    @Test
    public void getSongsSuccess() {
        String song = appContext.getResources().getString(R.string.Song);
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Cat Sound", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        testedData.add(new Container("ThunderClouds", song + ".Eminem juice WRLD", Utils.convertToBitmap(R.drawable.images)));
        ArrayList<Container> comingData = mockService.getSongs(appContext, "C");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }
    @Test
    public void getAlbumsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Gamal", appContext.getResources().getString(R.string.Album) + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        ArrayList<Container> comingData = mockService.getAlbums(appContext, "G");
        assertNotEquals(comingData.size(), testedData.size());
    }
    @Test
    public void getAlbumsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("The Shadows", appContext.getResources().getString(R.string.Album) + ".Little Mix", Utils.convertToBitmap(R.drawable.download)));
        ArrayList<Container> comingData = mockService.getAlbums(appContext, "T");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }
    @Test
    public void getProfilesFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Gamal", appContext.getResources().getString(R.string.Profile) + ".Little Mix", Utils.convertToBitmap(R.drawable.images3)));
        ArrayList<Container> comingData = mockService.getProfiles(appContext, "G");
        assertNotEquals(comingData.size(), testedData.size());
    }
    @Test
    public void getProfilesSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        ArrayList<Container> comingData = mockService.getProfiles(appContext, "G");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }
    @Test
    public void getGenresFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Roc", appContext.getResources().getString(R.string.Genre), Utils.convertToBitmap(R.drawable.images3)));
        ArrayList<Container> comingData = mockService.getGenresAndMoods(appContext, "R");
        assertNotEquals(testedData.get(0).getCat_Name(), comingData.get(0).getCat_Name());
    }
    @Test
    public void getGenresSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Rock", appContext.getResources().getString(R.string.Genre), Utils.convertToBitmap(R.drawable.images3)));
        ArrayList<Container> comingData = mockService.getGenresAndMoods(appContext, "R");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());
        }
    }

    @Test
    public void getPlaylistsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("quran", appContext.getResources().getString(R.string.Playlist), Utils.convertToBitmap(R.drawable.images)));
        ArrayList<Container> comingData = mockService.getPlaylists(appContext, "Q");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }

    @Test
    public void getPlaylistsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        testedData.add(new Container("Quran", appContext.getResources().getString(R.string.Playlist), Utils.convertToBitmap(R.drawable.download)));
        ArrayList<Container> comingData = mockService.getPlaylists(appContext, "Q");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }

    @Test
    public void removeAllRecentSearchesTest() {
        ArrayList<Container> beforeRemoving = mockService.getResentResult(appContext);
        mockService.removeAllRecentSearches(appContext);
        ArrayList<Container> afterRemoving = mockService.getResentResult(appContext);
        assertEquals(afterRemoving.size(), 0);
        assertNotEquals(beforeRemoving.size(), afterRemoving.size());
    }

    @Test
    public void getAllPopularPlaylistsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("Bahaa Sultan", "2,100 " + followers, Utils.convertToBitmap(R.drawable.bahaa)));
        testedData.add(new Container("anghami", "1,100 " + followers, Utils.convertToBitmap(R.drawable.angham)));
        testedData.add(new Container("Thunder", "2100 " + followers, Utils.convertToBitmap(R.drawable.halsey)));
        testedData.add(new Container("selena", "2800 " + followers, Utils.convertToBitmap(R.drawable.selena)));
        testedData.add(new Container("Smoke Grenades", "500 " + followers, Utils.convertToBitmap(R.drawable.jannat)));
        testedData.add(new Container("Playlist", "26 " + followers, Utils.convertToBitmap(R.drawable.wael)));
        testedData.add(new Container("3 d2at", "773 " + followers, Utils.convertToBitmap(R.drawable.abu)));
        ArrayList<Container> comingData = mockService.getAllPopularPlaylists(appContext);
        assertNotEquals(testedData.size(), comingData.size());
    }

    @Test
    public void getAllPopularPlaylistsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("greate playlist", "2,700 " + followers, Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("Amr Diab", "5,200 " + followers, Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("beautiful", "3,300 " + followers, Utils.convertToBitmap(R.drawable.imagine)));
        testedData.add(new Container("simple", "800 " + followers, Utils.convertToBitmap(R.drawable.alan)));
        testedData.add(new Container("nice songs", "1200 " + followers, Utils.convertToBitmap(R.drawable.ed)));
        testedData.add(new Container("araby", "3000 " + followers, Utils.convertToBitmap(R.drawable.assala)));
        testedData.add(new Container("Bahaa Sultan", "2,100 " + followers,Utils.convertToBitmap( R.drawable.bahaa)));
        testedData.add(new Container("anghami", "1,100 " + followers, Utils.convertToBitmap(R.drawable.angham)));
        testedData.add(new Container("Thunder", "2100 " + followers, Utils.convertToBitmap(R.drawable.halsey)));
        testedData.add(new Container("selena", "2800 " + followers, Utils.convertToBitmap(R.drawable.selena)));
        testedData.add(new Container("Smoke Grenades", "500 " + followers, Utils.convertToBitmap(R.drawable.jannat)));
        testedData.add(new Container("Playlist", "26 " + followers, Utils.convertToBitmap(R.drawable.wael)));
        testedData.add(new Container("3 d2at", "773 " + followers,Utils.convertToBitmap( R.drawable.abu)));
        testedData.add(new Container("Oranges", "470 " + followers, Utils.convertToBitmap(R.drawable.hamza)));
        ArrayList<Container> comingData = mockService.getAllPopularPlaylists(appContext);
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }
    @Test
    public void getFourPlaylistsFails() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("greate playlist", "2,700 " + followers, Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("Amr Diab", "5,200 " + followers, Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("beutiful", "3,300 " + followers, Utils.convertToBitmap(R.drawable.imagine)));
        testedData.add(new Container("simple", "800 " + followers, Utils.convertToBitmap(R.drawable.alan)));
        ArrayList<Container> comingData = mockService.getFourPlaylists(appContext);
        assertNotEquals(testedData.get(2).getCat_Name(), comingData.get(2).getCat_Name());
    }

    @Test
    public void getFourPlaylistsSuccess() {
        ArrayList<Container> testedData = new ArrayList<>();
        String followers = appContext.getResources().getString(R.string.Followers);
        testedData.add(new Container("greate playlist", "2,700 " + followers, Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("Amr Diab", "5,200 " + followers, Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("beautiful", "3,300 " + followers, Utils.convertToBitmap(R.drawable.imagine)));
        testedData.add(new Container("simple", "800 " + followers, Utils.convertToBitmap(R.drawable.alan)));
        ArrayList<Container> comingData = mockService.getFourPlaylists(appContext);
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), testedData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }
    }

    @Test
    public void getTrackOfPlaylistSuccess() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "sdflk;asfl;jsd[jrfd", "OneRepublic", "mood booster",
                "Rescue Me", R.drawable.rescue_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        tracks.add(new Track("Freaking Me Out", "sdlfknesdfalklsdlsd", "Ava Max",
                "mood booster", null, R.drawable.freaking_me_out, Settings.System.DEFAULT_RINGTONE_URI, true));
        tracks.add(new Track("You Can't Stop The Girl", "dlsfk;jl;esafdoilfd",
                "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl
                , Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        testPlaylists.add(new com.example.symphonia.Entities.Context("mood booster", "ewoirgwepdoed",
                "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks, "playlist"));
        Utils.playingContext = testPlaylists.get(0);
        ArrayList<Track> comingData = mockService.getTracksOfPlaylist(appContext, testPlaylists.get(0).getId(), null);
        assertEquals(testPlaylists.get(0).getTracks().size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            //     assertEquals(testPlaylists.get(i), comingData.get(i));
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j).getId(), comingData.get(j).getId());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getImageUrl(), comingData.get(j).getImageUrl());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmAlbum(), comingData.get(j).getmAlbum());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmArtist(), comingData.get(j).getmArtist());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDescription(), comingData.get(j).getmDescription());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDuration(), comingData.get(j).getmDuration());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmImageResources(), comingData.get(j).getmImageResources());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmTitle(), comingData.get(j).getmTitle());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getPlaylistName(), comingData.get(j).getPlaylistName());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getUri(), comingData.get(j).getUri());
            }
        }
    }

    @Test
    public void getTrackOfPlaylistFail() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "sdflk;asfl;jsd[jrfd", "OneRepublic", "mood booster",
                "Rescue Me", R.drawable.rescue_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        testPlaylists.add(new com.example.symphonia.Entities.Context("mood booster", "ewoirgwepdoed",
                "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks, "playlist"));
        ArrayList<Track> comingData = mockService.getTracksOfPlaylist(appContext, testPlaylists.get(0).getId(), null);
        assertNotEquals(testPlaylists.get(0).getTracks().size(), comingData.size());
    }

    @Test
    public void getRecentPlaylistsTest() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Little Do You Know", "srtyhtutyhgdgsfdgdsdf", "Alex & Sierra", "Rewind-the sound of 2014"
                , null, R.drawable.little_do_you_know, Settings.System.DEFAULT_RINGTONE_URI, true));
        tracks.add(new Track("Wildest Dreams", "shtfgfmnbghguyghf", "Taylor Swift", "Rewind-the sound of 2014"
                , null, R.drawable.wildest_dreams, Uri.parse("http://stream.radiosai.net:8002/"), false));
        tracks.add(new Track("One Last Time", "lfdgkm;dskvnlsd", "Ariana Grande", "Rewind-the sound of 2014"
                , null, R.drawable.one_last_time, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        testPlaylists.add(new com.example.symphonia.Entities.Context("Rewind-the sound of 2014", "sdfljdsfkdslkfv", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getRecentPlaylists(appContext, null);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            //     assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getId(), comingData.get(i).getId());
            assertEquals(testPlaylists.get(i).getImageUrl(), comingData.get(i).getImageUrl());
            assertEquals(testPlaylists.get(i).getmContextDescription(), comingData.get(i).getmContextDescription());
            assertEquals(testPlaylists.get(i).getmContextTitle(), comingData.get(i).getmContextTitle());
            assertEquals(testPlaylists.get(i).getTracksURL(), comingData.get(i).getTracksURL());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j).getId(), comingData.get(i).getTracks().get(j).getId());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getImageUrl(), comingData.get(i).getTracks().get(j).getImageUrl());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmAlbum(), comingData.get(i).getTracks().get(j).getmAlbum());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmArtist(), comingData.get(i).getTracks().get(j).getmArtist());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDescription(), comingData.get(i).getTracks().get(j).getmDescription());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDuration(), comingData.get(i).getTracks().get(j).getmDuration());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmImageResources(), comingData.get(i).getTracks().get(j).getmImageResources());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmTitle(), comingData.get(i).getTracks().get(j).getmTitle());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getPlaylistName(), comingData.get(i).getTracks().get(j).getPlaylistName());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getUri(), comingData.get(i).getTracks().get(j).getUri());
            }
        }
    }

    @Test
    public void getRecentPlaylistsFails() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Little Do You Know", "srtyhtutyhgdgsfdgdsdf", "Alex & Sierra", "Rewind-the sound of 2014"
                , null, R.drawable.little_do_you_know, Settings.System.DEFAULT_RINGTONE_URI, true));
        testPlaylists.add(new com.example.symphonia.Entities.Context("Rewind-the sound of 2014", "sdfljdsfkdslkfv", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getRecentPlaylists(appContext, null);
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertNotEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
        }
    }

    @Test
    public void getRandomPlaylists() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "sdfirjkfdvkjdsc", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI, false));
        tracks.add(new Track("Stupid Love", "sldksldkfnlasdnc", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, Uri.parse("http://stream.radiosai.net:8002/"), false));
        tracks.add(new Track("Feel Me", "slkdjfslkfdkdsljf", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), true));
        testPlaylists.add(new com.example.symphonia.Entities.Context("Daily Left", "dslkfoeirefdds", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getRandomPlaylists(appContext, null);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            //  assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getId(), comingData.get(i).getId());
            assertEquals(testPlaylists.get(i).getImageUrl(), comingData.get(i).getImageUrl());
            assertEquals(testPlaylists.get(i).getmContextDescription(), comingData.get(i).getmContextDescription());
            assertEquals(testPlaylists.get(i).getmContextTitle(), comingData.get(i).getmContextTitle());
            assertEquals(testPlaylists.get(i).getTracksURL(), comingData.get(i).getTracksURL());
            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j).getId(), comingData.get(i).getTracks().get(j).getId());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getImageUrl(), comingData.get(i).getTracks().get(j).getImageUrl());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmAlbum(), comingData.get(i).getTracks().get(j).getmAlbum());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmArtist(), comingData.get(i).getTracks().get(j).getmArtist());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDescription(), comingData.get(i).getTracks().get(j).getmDescription());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDuration(), comingData.get(i).getTracks().get(j).getmDuration());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmImageResources(), comingData.get(i).getTracks().get(j).getmImageResources());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmTitle(), comingData.get(i).getTracks().get(j).getmTitle());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getPlaylistName(), comingData.get(i).getTracks().get(j).getPlaylistName());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getUri(), comingData.get(i).getTracks().get(j).getUri());
            }
        }
    }

    @Test
    public void getRandomPlaylistsFails() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "sdfirjkfdvkjdsc", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI, false));
        testPlaylists.add(new com.example.symphonia.Entities.Context("Daily Left", "dslkfoeirefdds", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getRandomPlaylists(appContext, null);
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertNotEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
        }
    }

    @Test
    public void getMadeForYouPlaylists() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "sdfirjkfdvkjdsc", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI, false));
        tracks.add(new Track("Stupid Love", "sldksldkfnlasdnc", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, Uri.parse("http://stream.radiosai.net:8002/"), false));
        tracks.add(new Track("Feel Me", "slkdjfslkfdkdsljf", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), true));
        testPlaylists.add(new com.example.symphonia.Entities.Context("Daily Left", "dslkfoeirefdds", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getMadeForYouPlaylists(appContext, Constants.currentToken);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            //    assertEquals(testPlaylists.get(i), comingData.get(i));
            //     assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getId(), comingData.get(i).getId());
            assertEquals(testPlaylists.get(i).getImageUrl(), comingData.get(i).getImageUrl());
            assertEquals(testPlaylists.get(i).getmContextDescription(), comingData.get(i).getmContextDescription());
            assertEquals(testPlaylists.get(i).getmContextTitle(), comingData.get(i).getmContextTitle());
            assertEquals(testPlaylists.get(i).getTracksURL(), comingData.get(i).getTracksURL());

            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j).getId(), comingData.get(i).getTracks().get(j).getId());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getImageUrl(), comingData.get(i).getTracks().get(j).getImageUrl());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmAlbum(), comingData.get(i).getTracks().get(j).getmAlbum());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmArtist(), comingData.get(i).getTracks().get(j).getmArtist());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDescription(), comingData.get(i).getTracks().get(j).getmDescription());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDuration(), comingData.get(i).getTracks().get(j).getmDuration());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmImageResources(), comingData.get(i).getTracks().get(j).getmImageResources());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmTitle(), comingData.get(i).getTracks().get(j).getmTitle());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getPlaylistName(), comingData.get(i).getTracks().get(j).getPlaylistName());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getUri(), comingData.get(i).getTracks().get(j).getUri());

            }
        }
    }

    @Test
    public void getMadeForYouPlaylistsFails() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Feel Me", "slkdjfslkfdkdsljf", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), true));
        testPlaylists.add(new com.example.symphonia.Entities.Context("Daily Left", "dslkfoeirefdds", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getMadeForYouPlaylists(appContext, Constants.currentToken);
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertNotEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
        }

    }

    @Test
    public void getPopularPlaylists() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "sdflk;asfl;jsd[jrfd", "OneRepublic", "mood booster",
                "Rescue Me", R.drawable.rescue_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        tracks.add(new Track("Freaking Me Out", "sdlfknesdfalklsdlsd", "Ava Max",
                "mood booster", null, R.drawable.freaking_me_out, Settings.System.DEFAULT_RINGTONE_URI, true));
        tracks.add(new Track("You Can't Stop The Girl", "dlsfk;jl;esafdoilfd",
                "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl
                , Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        testPlaylists.add(new com.example.symphonia.Entities.Context("mood booster", "ewoirgwepdoed",
                "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getPopularPlaylists(appContext, null);
        assertEquals(testPlaylists.size(), comingData.size());
        for (int i = 0; i < testPlaylists.size(); i++) {
            //   assertEquals(testPlaylists.get(i), comingData.get(i));
            assertEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
            assertEquals(testPlaylists.get(i).getId(), comingData.get(i).getId());
            assertEquals(testPlaylists.get(i).getImageUrl(), comingData.get(i).getImageUrl());
            assertEquals(testPlaylists.get(i).getmContextDescription(), comingData.get(i).getmContextDescription());
            assertEquals(testPlaylists.get(i).getmContextTitle(), comingData.get(i).getmContextTitle());
            assertEquals(testPlaylists.get(i).getTracksURL(), comingData.get(i).getTracksURL());

            for (int j = 0; j < testPlaylists.get(i).getTracks().size(); j++) {
                assertEquals(testPlaylists.get(i).getTracks().get(j).getId(), comingData.get(i).getTracks().get(j).getId());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getImageUrl(), comingData.get(i).getTracks().get(j).getImageUrl());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmAlbum(), comingData.get(i).getTracks().get(j).getmAlbum());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmArtist(), comingData.get(i).getTracks().get(j).getmArtist());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDescription(), comingData.get(i).getTracks().get(j).getmDescription());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmDuration(), comingData.get(i).getTracks().get(j).getmDuration());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmImageResources(), comingData.get(i).getTracks().get(j).getmImageResources());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getmTitle(), comingData.get(i).getTracks().get(j).getmTitle());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getPlaylistName(), comingData.get(i).getTracks().get(j).getPlaylistName());
                assertEquals(testPlaylists.get(i).getTracks().get(j).getUri(), comingData.get(i).getTracks().get(j).getUri());
            }
        }
    }

    @Test
    public void getPopularPlaylistsFails() {
        ArrayList<com.example.symphonia.Entities.Context> testPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("You Can't Stop The Girl", "dlsfk;jl;esafdoilfd",
                "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl
                , Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), false));
        testPlaylists.add(new com.example.symphonia.Entities.Context("mood booster", "ewoirgwepdoed",
                "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks, "playlist"));
        ArrayList<com.example.symphonia.Entities.Context> comingData = mockService.getPopularPlaylists(appContext, null);
        for (int i = 0; i < testPlaylists.size(); i++) {
            assertNotEquals(testPlaylists.get(i).getTracks().size(), comingData.get(i).getTracks().size());
        }
    }

    @Test
    public void getArtistAlbumsSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("album1","3 songs",Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("album2","3 songs",Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("album3","3 songs",Utils.convertToBitmap(R.drawable.abu)));
        testedData.add(new Container("album4","3 songs",Utils.convertToBitmap(R.drawable.assala)));
        ArtistAlbums artistAlbums=new ArtistAlbums();
        ArrayList<Container> comingData = mockService.getCurrentArtistAlbums(appContext, artistAlbums,"album");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }

    }

    @Test
    public void getArtistAlbumsFailure(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("album1","3 songs",Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("album2","3 songs",Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("album3","3 songs",Utils.convertToBitmap(R.drawable.abu)));
        ArtistAlbums artistAlbums=new ArtistAlbums();
        ArrayList<Container> comingData = mockService.getCurrentArtistAlbums(appContext, artistAlbums,"album");
        assertNotEquals(comingData.size(), testedData.size());

    }

    @Test
    public void getArtistAlbumTracksSuccess(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("track1","3 minutes",Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("track2","2 minutes",Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("track3","1 minutes",Utils.convertToBitmap(R.drawable.abu)));
        testedData.add(new Container("track4","5 minutes",Utils.convertToBitmap(R.drawable.assala)));
        ArtistAlbumTracks artistAlbumTracks=new ArtistAlbumTracks("");
        ArrayList<Container> comingData = mockService.getAlbumTracks(appContext, artistAlbumTracks,"id");
        assertEquals(comingData.size(), testedData.size());
        for (int i = 0; i < comingData.size(); i++) {
            assertEquals(comingData.get(i).getCat_Name(), testedData.get(i).getCat_Name());
            assertEquals(comingData.get(i).getCat_Name2(), comingData.get(i).getCat_Name2());
            assertEquals(comingData.get(i).getImg_Res().getConfig(), testedData.get(i).getImg_Res().getConfig());
            assertEquals(comingData.get(i).getImg_Res().getWidth(), testedData.get(i).getImg_Res().getWidth());
            assertEquals(comingData.get(i).getImg_Res().getHeight(), testedData.get(i).getImg_Res().getHeight());        }

    }

    @Test
    public void getArtistAlbumTracksFailure(){
        ArrayList<Container>testedData=new ArrayList<>();
        testedData.add(new Container("track","3 minutes",Utils.convertToBitmap(R.drawable.amr)));
        testedData.add(new Container("track2","2 minutes",Utils.convertToBitmap(R.drawable.adele)));
        testedData.add(new Container("track3","1 minutes",Utils.convertToBitmap(R.drawable.abu)));
        testedData.add(new Container("track4","5 minutes",Utils.convertToBitmap(R.drawable.assala)));
        ArtistAlbumTracks artistAlbumTracks=new ArtistAlbumTracks("");
        ArrayList<Container> comingData = mockService.getAlbumTracks(appContext, artistAlbumTracks,"id");
        assertNotEquals(comingData.get(0).getCat_Name(), testedData.get(0).getCat_Name());
    }
}
