package com.example.symphonia.Activities.User_Management.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.symphonia.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Random;

/**
 * Class that handles Messaging Service from firebase
 *
 * @author Hossam Alaa
 * @since 10-06-2020
 * @version 1.0
 */
public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    /**
     * holds receiving notification message
     * @param remoteMessage holds the message data
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //sends data to be retrieved
        Map<String, String> map = remoteMessage.getData();
        String data = map.get("data");
        JsonObject object = new JsonParser().parse(data).getAsJsonObject();
        String from = object.get("from").getAsString();
        String to = object.get("to").getAsString();
        showNotification(remoteMessage.getNotification().getTitle()
                ,remoteMessage.getNotification().getBody(), from);
    }

    /**
     * holds register token of device to be sent to
     * @param s holds the token of device
     */
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("newToken",s);
    }

    /**
     * holds showing notification with data
     * @param title holds title of notification
     * @param body holds body of notification
     * @param to holds id of going to profile
     */
    private void showNotification(String title, String body, String to){

        //create new notification manager to handle the channel
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notifyChannelId = "com.example.symphonia";

        //checks android device SDK to see if suitable or not
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(notifyChannelId,"Notification"
                    ,NotificationManager.IMPORTANCE_HIGH);

            //sets channel name
            notificationChannel.setDescription("Symphonia channel");
            notificationManager.createNotificationChannel(notificationChannel);

            //holds the page to go to on pressed (Receive page)
            Intent notificationIntent = new Intent(getApplicationContext(), NotificationReceiver.class);

            //store required data (type and id) and send them to receiver page
            Bundle bundle =new Bundle();
            bundle.putString("type",title);
            bundle.putString("id",to);
            notificationIntent.putExtras(bundle);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(notificationIntent);
            PendingIntent contentIntent = stackBuilder.getPendingIntent(0
                    , PendingIntent.FLAG_UPDATE_CURRENT);

            //sets notification properties to be shown
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,notifyChannelId);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_app_round)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_app))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(contentIntent)
                    .setContentInfo("Info");
            notificationManager.notify(new Random().nextInt(),notificationBuilder.build());

        }
    }
}
