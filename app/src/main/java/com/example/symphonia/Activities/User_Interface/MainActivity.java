package com.example.symphonia.Activities.User_Interface;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.RvBarAdapter;
import com.example.symphonia.Adapters.RvPlaylistsHomeAdapter;
import com.example.symphonia.Adapters.RvTracksHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Context;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.library.AlbumFragment;
import com.example.symphonia.Fragments_and_models.library.EmptyPlaylistFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryFragment;
import com.example.symphonia.Fragments_and_models.playlist.BottomSheetDialogSettings;
import com.example.symphonia.Fragments_and_models.playlist.BottomSheetDialogSettingsCredits;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.premium.PremiumFragment;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbumTracks;
import com.example.symphonia.Fragments_and_models.profile.ArtistAlbums;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;
import com.example.symphonia.Fragments_and_models.profile.ProfilePlaylistsFragment;
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;
import com.example.symphonia.Helpers.MediaHelpers.MediaController;
import com.example.symphonia.Helpers.MediaHelpers.OnClearFromRecentService;
import com.example.symphonia.Helpers.MediaHelpers.PlayBarNotification;
import com.example.symphonia.Helpers.MediaHelpers.playable;
import com.example.symphonia.Helpers.SnapHelperOneByOne;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.example.symphonia.Service.updateUiPlaylists;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.symphonia.Helpers.Utils.CurrTrackInfo.track;

/**
 * Activity that accessing tracks and playing them
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class MainActivity extends AppCompatActivity implements RvPlaylistsHomeAdapter.OnPlaylistClicked
        , RvTracksHomeAdapter.OnTrackClicked
        , updateUiPlaylists
        // ,RestApi.updateUiGetCategories
        , RestApi.updateUiArtistAlbums
        , RestApi.updateProfileFollow
        , RestApi.updateUiProfileInSetting
        , RestApi.updateUiProfileInProfileFragment
        , RestApi.updateProfilePlaylists
        , BottomSheetDialogSettings.BottomSheetListener
        , RvBarAdapter.ItemInterface, Serializable
        , MediaController.OnStartListener
        , playable
        , RestApi.updateUiArtistAlbumTracks {

    /**
     * play prev track  listener of notification
     */

    @Override
    public void onTrackPrev() {
        playPrevTrack();
    }

    /**
     * play track listener of notification
     */
    @Override
    public void onTrackPlay() {
        if (!MediaController.getController().isMediaPlayerPlaying())
            MediaController.getController().resumeMedia();
        PlayBarNotification.PlayBarNotification(MainActivity.this
                , Utils.currTrack
                , R.drawable.ic_baseline_pause_24
                , Utils.getPosInPlaying(Utils.currTrack.getId())
                , Utils.playingContext.getTracks().size() - 1);
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(pauseBtn);
        playBarBtnFrame.setOnClickListener(playBtnListener);
    }

    /**
     * pause track listener of notification
     */
    @Override
    public void onTrackPause() {
        if (Utils.currTrack != null) {
            MediaController.getController().pauseMedia();
            PlayBarNotification.PlayBarNotification(MainActivity.this
                    , Utils.currTrack
                    , R.drawable.ic_baseline_play_arrow_24
                    , Utils.getPosInPlaying(Utils.currTrack.getId())
                    , Utils.playingContext.getTracks().size() - 1);
            playBarBtnFrame.removeAllViews();
            playBarBtnFrame.addView(playBtn);
            playBarBtnFrame.setOnClickListener(playBtnListener);
        }
    }

    /**
     * play next track listener of notification
     */
    @Override
    public void onTrackNext() {
        playNextTrack();
    }

    /**
     * broad cast receiver of notification play bar
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            if (action.matches(PlayBarNotification.CHANNEL_NEXT)) {
                onTrackNext();
            } else if (action.matches(PlayBarNotification.CHANNEL_PLAY)) {
                if (Utils.currTrack.isPlaying())
                    onTrackPause();
                else
                    onTrackPlay();
            } else if (action.matches(PlayBarNotification.CHANNEL_PREV)) {
                onTrackPrev();
            }
        }
    };

    /**
     * on start track success listener
     */
    @Override
    public void onStartListener() {
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(pauseBtn);
        playBarBtnFrame.setOnClickListener(playBtnListener);
    }

    /**
     * on get current playing track failed listener
     */
    @Override
    public void getCurrPlayingTrackFailed() {
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(playBtn);
        playBarBtnFrame.setOnClickListener(playBtnListener);
        MediaController.getController().releaseMedia();
        onTrackPause();
        start = false;

    }

    /**
     * this holds instance of home fragment
     */
    private HomeFragment homeFragment;

    /**
     * library fragment objcet reference
     */
    private LibraryFragment libraryFragment;
    /**
     * premium fragment objcet reference
     */
    private PremiumFragment premiumFragment;
    /**
     * layout manager of recycler view in play bar
     */
    private RecyclerView.LayoutManager layoutManager;
    /**
     * recycler view of play bar
     */
    private RecyclerView rvBar;
    /**
     * adapter of play bar
     */
    private RvBarAdapter barAdapter;
    /**
     * like button
     */
    private ImageView ivIsFavourite;
    /**
     * play bar view
     */
    private View playBar;

    /**
     * image of track in playbar
     */
    private ImageView trackImage;
    /**
     * toast object
     */
    private Toast toast;
    /**
     * playlist fragment object reference
     */
    private PlaylistFragment playlistFragment;
    /**
     * indicates if root fragment is visible
     */
    private boolean root = true;
    /**
     * holds position of item which its color needs to be reset
     */
    int prevPos;
    /**
     * instance of Media Controller
     */
    private MediaController mediaController;
    /**
     * constant indicates that track is Utils.CurrTrackInfo.paused
     */
    private final String IS_PAUSED = "isPaused";
    /**
     * navigation view of main activity
     */
    private BottomNavigationView navView;
    /**
     * layout of settings
     */
    View linearLayout;
    /**
     * play bar btn frame container
     */
    FrameLayout playBarBtnFrame;
    /**
     * play button
     */
    ImageView playBtn;
    /**
     * pause button
     */
    ImageView pauseBtn;
    /**
     * progress bar
     */
    ProgressBar progressBar;
    /**
     * on Audio focus change listener of media
     */
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                    if (MediaController.getController().isMediaNotNull()) {
                        MediaController.play();
                        onTrackPlay();
                        playBarBtnFrame.removeAllViews();
                        playBarBtnFrame.addView(pauseBtn);
                        playBarBtnFrame.setOnClickListener(playBtnListener);
                    }

                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS:
                    MediaController.getController().releaseMedia();
                    onTrackPause();
                    playBarBtnFrame.removeAllViews();
                    playBarBtnFrame.addView(playBtn);
                    playBarBtnFrame.setOnClickListener(playBtnListener);
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (MediaController.getController().isMediaNotNull()) {
                        MediaController.getController().stop();
                        onTrackPause();
                        playBarBtnFrame.removeAllViews();
                        playBarBtnFrame.addView(playBtn);
                        playBarBtnFrame.setOnClickListener(playBtnListener);

                    }
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (MediaController.getController().isMediaNotNull()) {
                        MediaController.pause();
                        Utils.CurrTrackInfo.currPlayingPos = MediaController.getController().getCurrentPosition();
                        onTrackPause();
                        playBarBtnFrame.removeAllViews();
                        playBarBtnFrame.addView(playBtn);
                        playBarBtnFrame.setOnClickListener(playBtnListener);
                    }
                    break;
            }
        }

    };

    /**
     * this function is called when user click on like item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onLikeClicked(int pos) {
        if (!Utils.displayedContext.getTracks().get(pos).isLiked() && (!Utils.displayedContext.getTracks().get(pos).isLocked()
                || Constants.currentUser.isPremuim())) {
            Utils.displayedContext.getTracks().get(pos).setLiked(true);
            ServiceController.getInstance().saveTrack(MainActivity.this
                    , Utils.displayedContext.getTracks().get(pos).getId());
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeLikedItemAtPos(Utils.displayedContext.getTracks().get(pos).getId(), true);
            }

        } else if (!Utils.displayedContext.getTracks().get(pos).isLocked() || Constants.currentUser.isPremuim()) {
            Utils.displayedContext.getTracks().get(pos).setLiked(false);
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeLikedItemAtPos(Utils.displayedContext.getTracks().get(pos).getId(), false);
            }
            ServiceController.getInstance().removeFromSaved(MainActivity.this
                    , Utils.displayedContext.getTracks().get(pos).getId());
        } else {
            makeToast(MainActivity.this.getString(R.string.locked_songs));
        }
        if (Utils.playingContext != null && Utils.playingContext.getId().matches(Utils.displayedContext.getId()))
            Utils.playingContext = Utils.displayedContext;
    }

    /**
     * this function is called when user click on hide item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onHideClicked(int pos) {
        if (Utils.playingContext == null) {
            makeToast(getString(R.string.playlist_not_playing));
        }
        if (!Utils.displayedContext.getTracks().get(pos).isHidden() &&
                (!Utils.displayedContext.getTracks().get(pos).isLocked() || Constants.currentUser.isPremuim())) {
            Utils.playingContext.getTracks().get(pos).setHidden(true);
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeHidden(pos, true);
            }
        } else if (!Utils.displayedContext.getTracks().get(pos).isLocked() || Constants.currentUser.isPremuim()) {
            Utils.playingContext.getTracks().get(pos).setHidden(false);
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeHidden(pos, false);
            }
        } else {
            makeToast(MainActivity.this.getString(R.string.locked_songs));
        }
    }

    /**
     * this function is called when user click on report item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onReportClicked(int pos) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        Toast toast = new Toast(MainActivity.this);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * this function is called when user click on share item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onShareClicked(int pos) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "" + Utils.CurrPlaylist.playlist.getTracks().get(pos).getUri());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * this function is called when user click on show credits item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onCreditsClicked(int pos) {
        BottomSheetDialogSettingsCredits sheet = new BottomSheetDialogSettingsCredits(pos);
        sheet.show(getSupportFragmentManager(), "credits");
    }

    /**
     * this function is called when user click on view artist item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onViewArtistClicked(int pos) {
        //TODO open artist in islam's work
    }
    //----------------------------------------------------------------------------


    /**
     * this function updates data after fetching categories
     */
    @Override
    public void getCategoriesSuccess() {
        /*if (navView.getSelectedItemId() == R.id.navigation_home)
            homeFragment.loadAllPlaylists();*/
    }

    /**
     * get current playing track success listener
     *
     * @param id id of tarck
     */
    @Override
    public void getCurrPlayingTrackSuccess(String id) {
        int pos = Utils.getPosInPlaying(id);
        if (pos > -1) {
            if (!Utils.playingContext.getTracks().get(pos).isHidden()
                    && (!Utils.playingContext.getTracks().get(pos).isLocked() || Constants.currentUser.isPremuim())) {
                Utils.currTrack = Utils.playingContext.getTracks().get(pos);
                playTrack();
            } else {
                playNextTrack();
            }
        } else
            ServiceController.getInstance().getTrack(MainActivity.this, id);
    }

    /**
     * get track data success listener
     */
    @Override
    public void getTrackSuccess() {
        startTrack();
    }

    /**
     * this function updates ui when request returns tracks of playlists
     *
     * @param playlistFragment playlist fragment object reference
     * @param tracksList       tracks list
     */
    @Override
    public void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment, ArrayList<Track> tracksList) {
        if (tracksList.size() < 1) {
            makeToast(getString(R.string.no_tracks));
            return;
        }
        if (playlistFragment != null && playlistFragment.isVisible()) {
            Utils.displayedContext.setTracks(tracksList);
        } else {
            Utils.playingContext.setTracks(tracksList);
        }
        String ids = "";
        ids += tracksList.get(0).getId();
        for (int i = 1; i < tracksList.size(); i++) {
            ids += "," + tracksList.get(i).getId();
        }
        ServiceController.getInstance().checkSaved(MainActivity.this, ids, playlistFragment);
    }

    /**
     * this function updates ui when request returns tracks of playlists
     *
     * @param playlistFragment playlist fragment object reference
     */
    @Override
    public void updateUicheckSaved(PlaylistFragment playlistFragment) {
        if (playlistFragment != null && playlistFragment.isVisible()) {
            playlistFragment.updateTracks();
            playlistFragment.hideProgressBar();
        } else
            playTrack();

    }

    /**
     * this function updates data after fetching popular playlists
     */
    @Override
    public void updateUiGetPopularPlaylistsSuccess() {
        if (navView.getSelectedItemId() == R.id.navigation_home) {
            homeFragment.updatePopularPlaylists();
            homeFragment.hideProgressBar();
        }
    }

    /**
     * this function updates data after fetching random playlists
     */
    @Override
    public void updateUiGetRandomPlaylistsSuccess(HomeFragment homeFragment) {
        if (navView.getSelectedItemId() == R.id.navigation_home) {
            homeFragment.updateRandomPlaylists();
            homeFragment.hideProgressBar();
        }

    }

    /**
     * this function updates data after fetching recent playlists
     */
    @Override
    public void updateUiGetRecentPlaylistsSuccess(HomeFragment homeFragment) {
        if (navView.getSelectedItemId() == R.id.navigation_home) {
            homeFragment.updateRecentPlaylists();
            homeFragment.hideProgressBar();
        }
    }

    /**
     * get track of queueu success listener
     */
    @Override
    public void getTrackOfQueue() {
        showPlayBar();
    }

    /**
     * play track in queueu success listener
     */
    @Override
    public void updateUiPlayTrack() {
        ServiceController serviceController = ServiceController.getInstance();
        serviceController.getQueue(MainActivity.this);
    }

    /**
     * get queue success listener
     */
    @Override
    public void updateUiGetQueue() {
        Log.e("main update", "call play track");
        if (!Utils.CurrTrackInfo.loading)
            playTrack();
    }


    /**
     * this function updates data after fetching make-for-you playlists
     */
    @Override
    public void updateUiGetMadeForYouPlaylistsSuccess() {
        if (navView.getSelectedItemId() == R.id.navigation_home)
            homeFragment.updateMadeForYouPlaylists();
    }

    /**
     * this function update ui when request returns no tracks
     */
    @Override
    public void updateUiNoTracks(PlaylistFragment playlistFragment) {
        if (playlistFragment.isVisible()) {
            playlistFragment.hideProgressBar();
        }
        makeToast(getString(R.string.no_tracks));
    }

    /**
     * this function updates data if fail to  fetch popular playlists
     */
    @Override
    public void updateUiGetPopularPlaylistsFail() {

    }

    /**
     * this function updates data if fail to  fetch random playlists
     */
    @Override
    public void updateUiGetRandomPlaylistsFail() {

    }

    /**
     * this function updates data if fail to  fetch recent playlists
     */
    @Override
    public void updateUiGetRecentPlaylistsFail() {

    }

    /**
     * this function updates data if fail to  fetch make-for-you playlists
     */
    @Override
    public void updateUiGetMadeForYouPlaylistsFail() {

    }

    /**
     * Represents the initialization of activity
     *
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create channel for play bar notification

        createChannel();
        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        if (!Constants.DEBUG_STATUS)
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@io.reactivex.annotations.NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("Error", "getInstanceId failed", task.getException());
                                return;
                            }
                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            Log.d("newToken: ", token);
                            ServiceController serviceController = ServiceController.getInstance();
                            serviceController.sendRegisterToken(MainActivity.this, token);
                        }
                    });

        mediaController = MediaController.getController();
        Bundle b = getIntent().getExtras();
        checkUserType(b);

        playBtn = new ImageView(this);
        playBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        pauseBtn = new ImageView(this);
        pauseBtn.setImageResource(R.drawable.ic_pause_black_24dp);
        progressBar = new ProgressBar(this);

        ServiceController controller = ServiceController.getInstance();
        // get recently played track
        // initialize bottom navigation view
        initBottomNavView();

        // attach views
        attachViews();

        addListeners();

        // add tracks of playlist to play bar recycler view
        if (Utils.playingContext != null) {
            barAdapter = new RvBarAdapter(this, Utils.playingContext.getTracks());
            playBar.setVisibility(View.VISIBLE);
        } else
            barAdapter = new RvBarAdapter(this, null);

        rvBar.setAdapter(barAdapter);
        toast = null;
        checkIntent(getIntent().getExtras());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id = bundle.getString("id");
            if (id != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new FragmentProfile(id));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                String albumID = bundle.getString("albumID");
                if (albumID != null) {
                    AlbumFragment fragment = new AlbumFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("ALBUM_ID", albumID);
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        }


        try {
            String fragment = getIntent().getExtras().getString("playlist_fragment");
            if (fragment != null && fragment.matches("playlist_fragment")) {
                OnPlaylistClickedListener(Utils.displayedContext);
            }
        } catch (Exception e) {
            if (Utils.currTrack == null) {
                controller.getCurrPlaying(this);
                start = true;
            } else {
                showPlayBar();
                if (mediaController.isMediaNotNull() && mediaController.isMediaPlayerPlaying()) {
                    playBarBtnFrame.removeAllViews();
                    playBarBtnFrame.addView(pauseBtn);
                    playBarBtnFrame.setOnClickListener(playBtnListener);
                } else {
                    playBarBtnFrame.removeAllViews();
                    playBarBtnFrame.addView(playBtn);
                    playBarBtnFrame.setOnClickListener(playBtnListener);
                }
            }
        }

    }

    /**
     * indicates if activity is running for the first time
     */
    private static boolean start = true;

    /**
     * on start is called when activity was hidden and is shown again
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (playlistFragment != null && playlistFragment.isVisible() && mediaController.isMediaNotNull()) {
            playlistFragment.changeSelected();//prevPos, Utils.CurrTrackInfo.TrackPosInPlaylist);
        } else if (playlistFragment != null && playlistFragment.isVisible() && !mediaController.isMediaNotNull()) {
            playlistFragment.changeSelected();//prevPos, -1);
        }
        if (playlistFragment != null && playlistFragment.isVisible() && Utils.CurrTrackInfo.currPlaylistName != null) {
            for (int i = 0; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                //   playlistFragment.changeHidden(i, Utils.CurrTrackInfo.currPlaylistTracks.get(i).isHidden());
                //   playlistFragment.changeLikedItemAtPos(i, Utils.CurrTrackInfo.currPlaylistTracks.get(i).isLiked());
            }
        }
        MediaController.setOnCompletionListener(onCompletionListener);
        mediaController.setMediaPlayCompletionService();
        prevPos = Utils.CurrTrackInfo.prevTrackPos;
        if (Utils.currTrack != null) {
            showPlayBar();
            if (MediaController.getController().isMediaPlayerPlaying()) {
                playBarBtnFrame.removeAllViews();
                playBarBtnFrame.addView(pauseBtn);
                playBarBtnFrame.setOnClickListener(playBtnListener);
            } else {
                playBarBtnFrame.removeAllViews();
                playBarBtnFrame.addView(playBtn);
                playBarBtnFrame.setOnClickListener(playBtnListener);
            }
        }
        if (trackImage != null && track != null)
            if (!Constants.DEBUG_STATUS)
                Picasso.get()
                        .load(track.getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(trackImage);
            else
                trackImage.setImageResource(track.getmImageResources());
        //check if user online
        if (!isOnline()) {
            connectToInternet();
        }
    }

    /**
     * this function shows playBar
     */
    public void showPlayBar() {
        barAdapter.setTracks(Utils.playingContext.getTracks());
        barAdapter.notifyDataSetChanged();
        playBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(Utils.currTrack.getImageUrl())
                .fit()
                .centerCrop()
                .into(trackImage);
        int pos = Utils.getPosInPlaying(Utils.currTrack.getId());
        if (ivIsFavourite != null && Utils.currTrack != null) {
            if (Utils.currTrack.isLiked() && Utils.playingContext.getTracks().get(pos).getId().matches(Utils.currTrack.getId())) {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else if (Utils.playingContext.getTracks().get(pos).getId().matches(Utils.currTrack.getId())) {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }
        hardScrolling = true;
        rvBar.getLayoutManager().scrollToPosition(pos);

    }

    /**
     * this function creates toast to show a message to user
     *
     * @param message takes message to be shown in toast
     */
    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * indicates if recycler bar is hard scrolling
     */
    private static boolean hardScrolling = false;

    /**
     * this function is called when playBar is recycled
     *
     * @param pos position of current track
     */
    @Override
    public void OnItemSwitchedListener(int pos) {
        if (hardScrolling) {
            hardScrolling = false;
            return;
        }
        int prev = Utils.getPosInPlaying(Utils.currTrack.getId());
        while (pos > -1 && pos < Utils.playingContext.getTracks().size()
                && !Constants.currentUser.isPremuim()
                && (Utils.playingContext.getTracks().get(pos).isLocked()
                || Utils.playingContext.getTracks().get(pos).isHidden())) {
            if (pos - prev > 0) ++pos;
            else --pos;
        }
        if (pos < 0 || pos >= Utils.playingContext.getTracks().size()) {
            rvBar.getLayoutManager().scrollToPosition(prev);
            makeToast(getString(R.string.track_is_locked));
            return;
        }
        if (pos - prev > 0) playNextTrack();
        else playPrevTrack();
    }

    /**
     * play previous track
     */
    private void playPrevTrack() {
        ServiceController.getInstance().playPrev(this);
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(progressBar);
        playBarBtnFrame.setOnClickListener(null);
    }

    /**
     * this function play media player with track stored in Current Playing
     */
    public void playTrack() {
        Log.e("main", "play");
        if (!start) {
            Utils.CurrTrackInfo.paused = false;
            Intent intent = new Intent(this, MediaController.class);
            intent.setAction(MediaController.ACTION_PLAY);
            MediaController.addListener(this);
            MediaController.getController().setOnAudioFocusChangeListener(onAudioFocusChangeListener);
            startService(intent);
            MediaController.setOnCompletionListener(onCompletionListener);
            //show notification by calling its listener
            onTrackPlay();
        } else {
            playBarBtnFrame.removeAllViews();
            playBarBtnFrame.addView(playBtn);
            playBarBtnFrame.setOnClickListener(playBtnListener);

        }
        showPlayBar();
        start = false;
        if (playlistFragment != null && playlistFragment.isVisible()) {
            playlistFragment.changeSelected();
        }
    }

    /**
     * play next track
     */
    private void playNextTrack() {
        ServiceController.getInstance().playNext(this);
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(progressBar);
        playBarBtnFrame.setOnClickListener(null);
    }

    /**
     * notification manager object
     */
    private NotificationManager notificationManager;

    /**
     * create channel for notification play bar
     */
    private void createChannel() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PlayBarNotification.CHANNEL_ID,
                    "Symphonia"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

    }

    /**
     * listener called when track is clicked
     *
     * @param tracks list of tracks of current playlist
     * @param pos    position of track in current playlist
     * @param prev   previous position of track in current playlist
     */
    @Override
    public void OnTrackClickedListener(final ArrayList<Track> tracks, final int pos, int prev) {
        if (tracks.get(pos).isLocked() && !Constants.currentUser.isPremuim()) {
            makeToast(getString(R.string.track_is_locked));
            return;
        }
        Utils.playingContext = Utils.displayedContext;
        Utils.currContextType = Utils.displayedContext.getContextType();
        Utils.currTrack = tracks.get(pos);
        // update data of bar
        if (!Constants.DEBUG_STATUS)
            if (!Utils.currTrack.getImageUrl().matches(""))
                Picasso.get()
                        .load(Utils.currTrack.getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(trackImage);
            else {
                trackImage.setImageResource(R.drawable.no_image);
                Utils.currTrack.setImageResources(R.drawable.no_image);
            }
        else
            trackImage.setImageResource(Utils.currTrack.getmImageResources());
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(pauseBtn);
        //Utils.setTrackInfo(0, pos, tracks);
        if (playlistFragment.isVisible()) {
            playlistFragment.changeSelected();
        }
        if (tracks.get(pos).isLiked()) {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        Log.e("main", "track clicked");
        startTrack();
    }

    /**
     * this function is called to play track in both Mock and Rest API
     */
    public void startTrack() {
        playBarBtnFrame.removeAllViews();
        playBarBtnFrame.addView(progressBar);
        playBarBtnFrame.setOnClickListener(null);
        Utils.CurrTrackInfo.paused = false;
        Utils.currContextId = Utils.currTrack.getPlayListId();
        if (mediaController.isMediaPlayerPlaying())
            mediaController.releaseMedia();
        ServiceController.getInstance().playTrack(this);

    }

    /**
     * this function adds listener to views in main activity
     */
    private void addListeners() {
        ivIsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId())).isLiked()) {
                    Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId())).setLiked(false);
                    Utils.currTrack = Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId()));
                    ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    ServiceController.getInstance().removeFromSaved(MainActivity.this, Utils.currTrack.getId());
                    makeToast(getString(R.string.remove_from_liked_playlist));
                } else {
                    makeToast(getString(R.string.add_to_like_playlist));

                    ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    ServiceController.getInstance().saveTrack(MainActivity.this, Utils.currTrack.getId());
                    Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId())).setLiked(true);
                    Utils.currTrack = Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId()));
                }
                if (playlistFragment != null && Utils.playingContext.getId().matches(Utils.displayedContext.getId()))
                    playlistFragment.changeLikedItemAtPos(Utils.currTrack.getId()
                            , Utils.currTrack.isLiked());

            }
        });
        playBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra(IS_PAUSED, Utils.CurrTrackInfo.paused);
                prevPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
                startActivity(intent);
            }
        });
        playBarBtnFrame.setOnClickListener(playBtnListener);
    }

    /**
     * play buutonn listenr of play bar
     */
    View.OnClickListener playBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mediaController.isMediaPlayerPlaying()) {
                playBarBtnFrame.removeAllViews();
                playBarBtnFrame.addView(playBtn);
                mediaController.pauseMedia();
                onTrackPause();
            } else {
                playBarBtnFrame.removeAllViews();
                playBarBtnFrame.addView(pauseBtn);
                if (mediaController.isMediaNotNull()) {
                    mediaController.resumeMedia();
                    onTrackPlay();
                } else {
                    playTrack();
                    onTrackPlay();
                    if (playlistFragment != null && playlistFragment.isVisible()) {
                        playlistFragment.changeSelected();//-1, Utils.CurrTrackInfo.TrackPosInPlaylist);
                    }
                }
            }
        }
    };

    /**
     * this is a listener to media player after completion
     */
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            playNextTrack();
        }
    };


    /**
     * listener called when track is selected to be favourite
     *
     * @param selected is track selected
     * @param pos      position of track
     */
    @Override
    public void OnLikeClickedListener(boolean selected, int pos) {
        if (Utils.displayedContext == null) {
            return;
        }

        if (Utils.playingContext != null && Utils.playingContext.getId().matches(Utils.displayedContext.getId())) {
            Utils.playingContext.setTracks(Utils.displayedContext.getTracks());
        }
        if (Utils.displayedContext.getTracks().get(pos).getId().matches(Utils.currTrack.getId())) {
            Utils.currTrack.setLiked(selected);
            if (selected) {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }
        Utils.displayedContext.getTracks().get(pos).setLiked(selected);
    }


    /**
     * listener called when playlist is clicked
     *
     * @param currContext playlist that is clicked
     */
    @Override
    public void OnPlaylistClickedListener(Context currContext) {
        Utils.displayedContext = currContext;
        if (currContext.getContextType().matches("playlist")) {
            playlistFragment = new PlaylistFragment();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.nav_host_fragment, playlistFragment
                    , this.getString(R.string.playlist_fragment))
                    .addToBackStack(null)
                    .commit();
        } else if (currContext.getContextType().matches("album")) {
            // ServiceController.getInstance().getAlbum(this,currContext.getId());
            AlbumFragment fragment = new AlbumFragment();
            Bundle arguments = new Bundle();
            arguments.putString("ALBUM_ID", currContext.getId());
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        } else if (currContext.getContextType().matches("artist")) {
            //TODO open artist fragment
            makeToast("open artist");
        }
    }


    /**
     * this function attach data to views in mainActivity
     */
    private void attachViews() {
        rvBar = findViewById(R.id.rv_bar_main_activity);
        // add recycler to snap to control scroll one item at a time
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvBar);
        playBar = findViewById(R.id.layout_playing_bar);

        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvBar.setLayoutManager(layoutManager);
        rvBar.setHasFixedSize(true);
        playBarBtnFrame = playBar.findViewById(R.id.play_btn_parent_play_bar);
        trackImage = playBar.findViewById(R.id.iv_track_image_bar);
        ivIsFavourite = playBar.findViewById(R.id.iv_like_track_bar);
    }

    /**
     * this function is called when setting is clicked
     *
     * @param track track
     */
    @Override
    public void showTrackSettingFragment(Track track) {

        BottomSheetDialogSettings settings = new BottomSheetDialogSettings(track, this);
        settings.show(getSupportFragmentManager(), "settings");
      /*  setDataOfTrackSettings(pos);
        setSettingListeners(pos);*/
    }


    /**
     * this function is called when back arrow button is clicked
     */
    @Override
    public void onBackPressed() {
        if (playlistFragment != null && playlistFragment.isVisible()) {
            getSupportFragmentManager().popBackStack();
            navView.setSelectedItemId(R.id.navigation_home);
            return;
        }
        if (homeFragment != null && homeFragment.isVisible()) {
            finishAffinity();
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            Fragment fragment;
            fragment = getSupportFragmentManager().findFragmentByTag("home");
            if (fragment != null) {
                navView.setSelectedItemId(R.id.navigation_home);
                return;
            }
            fragment = getSupportFragmentManager().findFragmentByTag("library");
            if (fragment != null) {
                navView.setSelectedItemId(R.id.navigation_library);
                return;
            }
            fragment = getSupportFragmentManager().findFragmentByTag("premium");
            if (fragment != null) {
                navView.setSelectedItemId(R.id.navigation_premium);
                return;
            }
            return;
        }
        if (root) {
            finishAffinity();
        }
        super.onBackPressed();
    }

    /**
     * onClickListener called when user click on playBar
     *
     * @param tracks tracks in current playlist
     * @param pos    position of current playing track
     */
    @Override
    public void OnItemClickedListener(ArrayList<Track> tracks, int pos) {
        prevPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * this function initialize BottomNavigationView
     */
    private void initBottomNavView() {
        navView = findViewById(R.id.nav_view);

        // menu should be considered as top level destinations.
        NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.mobile_navigation);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit();
        homeFragment = new HomeFragment();
        libraryFragment = new LibraryFragment();
        premiumFragment = new PremiumFragment();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, homeFragment, "home")
                                .addToBackStack("home")
                                .commit();
                        return true;
                    case R.id.navigation_library:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, libraryFragment, "library")
                                .addToBackStack("library")
                                .commit();
                        return true;
                    case R.id.navigation_premium:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, premiumFragment, "premium")
                                .addToBackStack("premium")
                                .commit();
                        return true;
                }
                return false;
            }
        });


    }

    /**
     * shows an AlertDialog to go to WIFI settings
     */
    private void connectToInternet() {

        // build title and message views for AlertDialog
        TextView title = new TextView(this);
        title.setText(R.string.network);
        title.setTextColor(getResources().getColor(android.R.color.white));
        title.setTextSize(24/*set test size to 24sp */);
        title.setPadding((int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding));
        TextView body = new TextView(this);
        body.setText(R.string.you_are_not_online);
        body.setTextSize(18/*set test size to 18sp */);
        body.setTextColor(getResources().getColor(android.R.color.white));
        body.setPadding((int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(getString(R.string.connect), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, getString(R.string.only_local_data_show), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCustomTitle(title);
        dialog.setView(body);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.dark_gray);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(this.getResources().getColor(android.R.color.white));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(this.getResources().getColor(android.R.color.white));
    }

    /**
     * check if user is online
     *
     * @return true if user online otherwise return false
     */
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    public void checkUserType(Bundle b) {

        if (Constants.DEBUG_STATUS) {
            SharedPreferences sharedPref = getSharedPreferences("LoginPref", 0);
            String email = sharedPref.getString("email", "");
            boolean type = sharedPref.getBoolean("type", true);
            String x;
            try {
                x = b.getString("newuser");
            } catch (NullPointerException e) {
                ServiceController serviceController = ServiceController.getInstance();
                boolean logged = serviceController.logIn(this, email, "12345678", type);
                if (!logged)
                    serviceController.logIn(this, "user1@symphonia.com"
                            , "12345678", true);
            }
        }

/*        if (Constants.currentUser.isListenerType())
            Toast.makeText(this, "Listener", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Artist", Toast.LENGTH_SHORT).show();

        if (Constants.currentUser.isPremuim())
            Toast.makeText(this, "Premium", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Not Premium", Toast.LENGTH_SHORT).show();*/
    }

    public void checkIntent(Bundle b) {
        try {
            String received = b.getString("go_to");
            if (received.equals("premium")) {
                BottomNavigationView navView = findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_premium);
            }
        } catch (Exception e) {
            return;
        }
    }

    /**
     * call function updatePlaylists in fragmentProfile after successful response
     *
     * @param playlists       the list of updated playlists
     * @param fragmentProfile the fragment which will be updated
     */
    @Override
    public void getCurrentProfilePlaylists(ArrayList<Container> playlists, FragmentProfile fragmentProfile) {
        fragmentProfile.updatePlaylists(playlists);
    }

    /**
     * call function updateUiFollowing in fragmentProfile after successful response
     *
     * @param f               string of number of following
     * @param fragmentProfile the fragment which will be updated
     */
    @Override
    public void getCurrentUserFollowingNumber(String f, FragmentProfile fragmentProfile) {
        fragmentProfile.updateUiFollowing(f);
    }

    /**
     * call function updateUiFollowing in fragmentProfile after successful response
     *
     * @param profile          current user profile
     * @param settingsFragment the fragment which will be updated
     */
    @Override
    public void getCurrentProfile(Profile profile, Fragment settingsFragment, String id) {
        if (id == null) {
            SettingsFragment fragment = (SettingsFragment) settingsFragment;
            fragment.updateUiProfile(profile);
        } else {
            FragmentProfile fragmentProfile = (FragmentProfile) settingsFragment;
            fragmentProfile.updateUiProfile(profile);
        }
    }



    /*@Override
    public void getCurrentUserFollowing(ArrayList<Container> f, FragmentProfile fragmentProfile) {
        fragmentProfile.updateUiFollowing(f);
    }

    @Override
    public void getCurrentUserFollowers(ArrayList<Profile> f, FragmentProfile fragmentProfile) {
        //fragmentProfile.updateUiFollowers(f);
    }*/

    /**
     * call function updateUiFollowers in fragmentProfile after successful response
     *
     * @param num             string of number of followers
     * @param fragmentProfile the fragment which will be updated
     */
    @Override
    public void getCurrentUserFollowersNumber(String num, FragmentProfile fragmentProfile) {
        fragmentProfile.updateUiFollowers(num);
    }

    /**
     * call function updatePlaylists in profilePlaylistsFragment after successful response
     *
     * @param p                        list of updated playlists
     * @param profilePlaylistsFragment the fragment which will be updated
     */
    @Override
    public void getAllUserPlaylists(ArrayList<Container> p, ProfilePlaylistsFragment profilePlaylistsFragment) {
        profilePlaylistsFragment.updatePlaylists(p);
    }

    /**
     * call function updateUiFollowers in profileFollowersFragment after successful response
     *
     * @param f                        list of followers
     * @param profileFollowersFragment the fragment which will be updated
     */
    @Override
    public void getUserFollowers(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment) {
        profileFollowersFragment.updateUiFollowers(f);
    }

    /**
     * call function updateUiFollowing in profileFollowersFragment after successful response
     *
     * @param f                        list of following
     * @param profileFollowersFragment the fragment which will be updated
     */
    @Override
    public void getUserFollowing(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment) {
        profileFollowersFragment.updateUiFollowing(f);
    }

    @Override
    public void getArtistAlbums(ArrayList<Container> f, ArtistAlbums artistAlbums) {
        artistAlbums.updateUiArtistAlbums(f);
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                String id = data.getStringExtra("CREATED_PLAYLIST_ID");
                EmptyPlaylistFragment fragment = new EmptyPlaylistFragment();
                Bundle arguments = new Bundle();
                arguments.putString("PLAYLIST_ID", id);
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    public void startCreatePlaylist() {
        Intent createPlaylistIntent = new Intent(this, CreatePlaylistActivity.class);
        startActivityForResult(createPlaylistIntent, 2);
    }


    /**
     * on destroy activity called
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notificationManager != null)
            notificationManager.cancelAll();

        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onAddAlbumSuccess(ArtistAlbums artistAlbums, String id, String name, String imgUrl, Bitmap bitmap) {
        artistAlbums.OnAddAlbumSuccess(id, name, imgUrl, bitmap);
    }

    @Override
    public void onAddAlbumfailure(ArtistAlbums artistAlbums) {
        artistAlbums.OnAddAlbumFailure();
    }

    @Override
    public void onRenameAlbumSuccess(ArtistAlbums artistAlbums, int pos, String name) {
        artistAlbums.OnRenameAlbumSuccess(pos, name);
    }

    @Override
    public void onRenameAlbumfailure(ArtistAlbums artistAlbums) {
        artistAlbums.OnRenameAlbumFailure();
    }

    @Override
    public void onDelAlbumSuccess(ArtistAlbums artistAlbums, String id, int pos) {
        artistAlbums.OnDelAlbumSuccess(pos);
    }

    @Override
    public void onDelAlbumfailure(ArtistAlbums artistAlbums) {
        artistAlbums.OnDelAlbumFailure();
    }

    @Override
    public void ongetAlbumTracks(ArtistAlbumTracks artistAlbumTracks, ArrayList<Container> tracks) {
        artistAlbumTracks.updateUiGetTracks(tracks);
    }

    @Override
    public void onAddTrackSuccess(ArtistAlbumTracks artistAlbumTracks, String id, String name, String imgUrl, Bitmap bitmap) {

    }

    @Override
    public void onAddTrackfailure(ArtistAlbumTracks artistAlbumTracks) {

    }

    @Override
    public void onRenameTrackSuccess(ArtistAlbumTracks artistAlbumTracks, int pos, String name) {
        artistAlbumTracks.OnRenameTrackSuccess(pos, name);
    }

    @Override
    public void onRenameTrackfailure(ArtistAlbumTracks artistAlbumTracks) {
        artistAlbumTracks.OnRenameTrackFailure();
    }

    @Override
    public void onDelTrackSuccess(ArtistAlbumTracks artistAlbumTracks, String id, int pos) {
        artistAlbumTracks.OnDelTrackSuccess(pos);
    }

    @Override
    public void onDelTrackfailure(ArtistAlbumTracks artistAlbumTracks) {
        artistAlbumTracks.OnDelTrackFailure();
    }

    @Override
    public void getCurrentProfilePlaylistFragment(String name, PlaylistFragment playlistFragment) {
        if (playlistFragment != null && playlistFragment.isVisible()) {
            playlistFragment.updateMadeByView(name);
        }
    }

/*@Override
    public void getCategoriesSuccess(ArrayList<Category> c) {
        searchFragment.UpdateUiGetCategories(c);
    }*/
}
