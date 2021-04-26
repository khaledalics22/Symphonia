package com.example.symphonia.Activities.User_Management.Notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Entities.Context;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

/**
 * Activity that handles receiving notification and send them to right pages
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 10-06-2020
 */
public class NotificationReceiver extends AppCompatActivity {

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);
        try {
            //receive type and ids of notification
            Bundle bundle = getIntent().getExtras();
            String Type = bundle.getString("type");
            String id = bundle.getString("id");

            //following code checks each type of them
            if(Type.equals("Following User")){
                //sends this id to open its profile
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("id",id );
                i.putExtras(b);
                startActivity(i);
            }else if(Type.equals("Like Playlist")){
                //sends this id to open its profile
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("id",id );
                i.putExtras(b);
                startActivity(i);
            }else if(Type.equals("PlayList Updated")){
                //sends this id to open its playlist page
                RestApi.UpdatePlaylist listener = new RestApi.UpdatePlaylist() {
                    @Override
                    public void updatePlaylist(Playlist playlist) {
                        Intent i = new Intent(NotificationReceiver.this,MainActivity.class);
                        i.putExtra("playlist_fragment", "playlist_fragment");
                        Utils.displayedContext = new Context();
                        Utils.displayedContext.setContext(playlist);
                        Utils.displayedContext.setContextType("playlist");
                        startActivity(i);
                    }
                };
                ServiceController.getInstance().getPlaylist(listener,id);
            }else{
                //sends this id to album page
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("albumID", id);
                i.putExtras(b);
                startActivity(i);
            }
        }catch (Exception e){
            e.printStackTrace();
            Intent i = new Intent(this,NotificationsHistoryActivity.class);
            startActivity(i);
        }
    }
}