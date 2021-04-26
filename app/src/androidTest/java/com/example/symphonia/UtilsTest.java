
package com.example.symphonia;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.graphics.Color;
import android.util.Base64;

import androidx.test.filters.LargeTest;

import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
@LargeTest
public class UtilsTest {

    @Test
    public void isValidEmailSuccess(){
        boolean b = Utils.isValidEmail("abc@gmail.com");
        assertTrue(b);
    }

    @Test
    public void isValidEmailFail(){
        assertFalse(Utils.isValidEmail("sdn dsk.sdcds"));
    }

    @Test
    public void getNameEmailSuccess(){
        assertEquals(Utils.getNameFromEmail("abc@gmail.com"),"abc");
    }

    @Test
    public void getNameEmailFails(){
        assertNotEquals(Utils.getNameFromEmail("abc@gmail.com"),"fgfd");
    }
/*

    @Test
    public  void setTrackInfoTestSuccess()
    {
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null
                , R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI, false));
        tracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null
                , R.drawable.stupid_love, Uri.parse("http://stream.radiosai.net:8002/"), false));
        tracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null
                , R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), true));
        Utils.CurrPlaylist.playlist = new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), tracks);
        Utils.setTrackInfo(0,0,tracks);
        assertEquals(Utils.CurrTrackInfo.currPlayingPos,0);
        assertEquals(Utils.CurrTrackInfo.TrackPosInPlaylist,0);
        assertEquals(Utils.CurrTrackInfo.currPlaylistName,"Daily Left");
        for (int j = 0; j < tracks.size(); j++) {
            assertEquals(tracks.get(j).getId(), Utils.CurrTrackInfo.currPlaylistTracks.get(j).getId());
            assertEquals(tracks.get(j).getImageUrl(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getImageUrl());
            assertEquals(tracks.get(j).getmAlbum(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getmAlbum());
            assertEquals(tracks.get(j).getmArtist(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getmArtist());
            assertEquals(tracks.get(j).getmDescription(), Utils.CurrTrackInfo.currPlaylistTracks.get(j).getmDescription());
            assertEquals(tracks.get(j).getmDuration(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getmDuration());
            assertEquals(tracks.get(j).getmImageResources(), Utils.CurrTrackInfo.currPlaylistTracks.get(j).getmImageResources());
            assertEquals(tracks.get(j).getmTitle(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getmTitle());
            assertEquals(tracks.get(j).getPlaylistName(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getPlaylistName());
            assertEquals(tracks.get(j).getUri(),  Utils.CurrTrackInfo.currPlaylistTracks.get(j).getUri());
        }

    }

    @Test
    public  void setTrackInfoTestFails() {
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null
                , R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI, false));
        tracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null
                , R.drawable.stupid_love, Uri.parse("http://stream.radiosai.net:8002/"), false));
        tracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null
                , R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"), true));
        Utils.CurrPlaylist.playlist = new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), tracks);
        Utils.setTrackInfo(0, 0, tracks);
        assertNotEquals(Utils.CurrTrackInfo.currPlaylistName, "mood boost");
    }
*/

    @Test
    public void isColorDarkTest(){
        assertTrue(Utils.isColorDark(Color.BLACK));
        assertFalse(Utils.isColorDark(Color.WHITE));
    }

    @Test
    public void getStringImage() throws IOException {
        Bitmap bitmap=Utils.convertToBitmap(R.drawable.amr);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        assertEquals(encodedImage,Utils.getStringImage(bitmap));
    }
}
