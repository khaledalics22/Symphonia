package com.example.symphonia.Activities.User_Management.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.NotificationAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Context;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Activity that holds notifications history
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 10-06-2020
 */
public class NotificationsHistoryActivity extends AppCompatActivity implements RestApi.updateUIGetNotify {

    /**
     * holds recycler view of notifications items
     */
    private RecyclerView mRecyclerView;
    /**
     * holds progress bar item to be shown at loading
     */
    private ProgressBar mProgressBar;
    /**
     * holds list of objects of notifications
     */
    private ArrayList<NotificationItem> mNotificationItems;
    /**
     * adapter for recycler view
     */
    private NotificationAdapter notificationAdapter;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_history);

        //initialize list of notifications
        mNotificationItems = new ArrayList<>();
        //gets recycler view by id
        mRecyclerView = findViewById(R.id.rv_notification);
        //gets progress bar by id
        mProgressBar = findViewById(R.id.progress);

        //checks mock service state
        if(Constants.DEBUG_STATUS){
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            addMockItems();
        }
        else{
            //if state is service, then send request and show progress bar of loading
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.getNotificationHistory(this,Constants.currentToken);
        }

        //object of layoutManager that controls recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        //initializes adapter with the list
        notificationAdapter = new NotificationAdapter(mNotificationItems, this);
        mRecyclerView.setLayoutManager(layoutManager);
        //sets adapter for the recycler view
        mRecyclerView.setAdapter(notificationAdapter);

        //holds listener for clicking the notification item
        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //gets index of item pressed, then send it to its target
                sendToTarget(position);
            }
        });
    }

    /**
     * holds sending pressed notification to its target
     * @param position index of pressed item
     */
    public void sendToTarget(int position){

        //if current mode is debug, then show the index of item
        if(Constants.DEBUG_STATUS)
            Toast.makeText(this, ""+position,Toast.LENGTH_SHORT).show();

        //if current mode is server
        else {
            //if notification is following
            if(mNotificationItems.get(position).getText1().equals("Following User")) {
                //gets profile id from item who pressed follow
                String profileID = mNotificationItems.get(position).getSenderID();

                //sends this id to open its profile
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("id",profileID );
                i.putExtras(b);
                startActivity(i);
            }

            //if notification is liked playlist
            else if(mNotificationItems.get(position).getText1().equals("Like Playlist")) {

                //gets user id who pressed like
                String profileID = mNotificationItems.get(position).getSenderID();

                //sends this id to open its profile
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("id",profileID );
                i.putExtras(b);
                startActivity(i);
            }

            //if notification is updated playlist
            else if(mNotificationItems.get(position).getText1().equals("PlayList Updated")) {

                //gets playlist id which is updated
                String playlistID = mNotificationItems.get(position).getSenderID();

                //sends this id to open its playlist page
                RestApi.UpdatePlaylist listener = new RestApi.UpdatePlaylist() {
                    @Override
                    public void updatePlaylist(Playlist playlist) {
                            Intent i = new Intent(NotificationsHistoryActivity.this,MainActivity.class);
                            i.putExtra("playlist_fragment", "playlist_fragment");
                            Utils.displayedContext = new Context();
                            Utils.displayedContext.setContext(playlist);
                            Utils.displayedContext.setContextType("playlist");
                            startActivity(i);
                    }
                };
                ServiceController.getInstance().getPlaylist(listener,playlistID);
            }

            //if notification is updated tracks
            else {

                //gets album id from item
                String albumID = mNotificationItems.get(position).getSenderID();

                //sends this id to album page
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("albumID",albumID);
                i.putExtras(b);
                startActivity(i);
            }
        }

    }

    /**
     * holds filling history with mock data
     */
    private void addMockItems(){
        mNotificationItems.add(new NotificationItem(R.drawable.amr,"Amr Diab"
                , "new user has followed you","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.elissa,"Elissa"
                , "Artist posted new song","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.taylor,"Taylor Swift"
                , "new user added song to your playlist","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.amr,"Amr Diab"
                , "new user has followed you","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.elissa,"Ellissa"
                , "Artist posted new song","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.taylor,"Taylor Swift"
                , "new user added song to your playlist","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.amr,"Amr Diab"
                , "new user has followed you","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.elissa,"Ellissa"
                , "Artist posted new song","03:04:2020 At 02:13"));
        mNotificationItems.add(new NotificationItem(R.drawable.taylor,"Taylor Swift"
                , "new user added song to your playlist","03:04:2020 At 02:13"));

    }

    /**
     * handles success of get history request
     * @param root holds respond json body
     */
    @Override
    public void updateUIGetNotifySuccess(JSONObject root) {

        //initializes array to fill data in
        ArrayList<NotificationItem> notificationItemArrayList = new ArrayList<>();

        //parsing data with try and catch to avoid parsing exceptions
        try {
            JSONObject notifications = root.getJSONObject("notifications");
            JSONArray items = notifications.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject item = items.getJSONObject(i);
                JSONObject data = item.getJSONObject("data");
                String dataIn = data.getString("data");
                JsonObject object = new JsonParser().parse(dataIn).getAsJsonObject();
                String SenderID = object.get("from").getAsString();

                JSONObject notification = item.getJSONObject("notification");
                String title = notification.getString("title");
                String body = notification.getString("body");
                String icon = "";
                try {
                     icon = notification.getString("icon");
                }catch (Exception e){
                    icon = "https://thesymphonia.ddns.net/api/v1/images/users/default.png";
                }
                NotificationItem notificationItem = new NotificationItem(R.drawable.placeholder_user,
                        title,body);
                notificationItem.setImageUrl(icon);
                notificationItem.setSenderID(SenderID);

                String time_total = "";
                try {
                    time_total = item.getString("date");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" ,Locale.getDefault());
                    format.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date date = format.parse(time_total);
                    DateFormat format1 = new SimpleDateFormat("EEE, d MMM yyyy h:mm a", Locale.getDefault());
                    time_total = format1.format(date);
                 //   time_total = time_total.substring(0, time_total.length()-9);
                }catch (Exception e){
                    e.printStackTrace();
                    time_total = "";
                }
                notificationItem.setText3(time_total);
                notificationItemArrayList.add(notificationItem);
            }

            //after retrieving data, rotate it to show latest added
            for(int i=notificationItemArrayList.size()-1;i>=0;i--){
                mNotificationItems.add(notificationItemArrayList.get(i));
            }

            //shows recycler view
            mRecyclerView.setVisibility(View.VISIBLE);
            //hides loading
            mProgressBar.setVisibility(View.GONE);
            //show new data
            notificationAdapter.notifyDataSetChanged();

        } catch (JSONException e){
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }

    /**
     * holds if request is failed
     */
    @Override
    public void updateUIGetNotifyFailed() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * sends app to main activity if back is pressed
     */
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

}

/*
AlbumFragment fragment = new AlbumFragment();
Bundle arguments = new Bundle();
arguments.putString("ALBUM_ID" , mAlbum.getAlbumId());
fragment.setArguments(arguments);
((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction()
        .replace(R.id.nav_host_fragment, fragment)
        .addToBackStack(null)
        .commit();
*/
