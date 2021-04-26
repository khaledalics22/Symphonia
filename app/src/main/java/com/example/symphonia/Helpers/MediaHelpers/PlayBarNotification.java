package com.example.symphonia.Helpers.MediaHelpers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.symphonia.Entities.Track;
import com.example.symphonia.R;

public class PlayBarNotification {
    public static final String CHANNEL_ID = "channel1";
    public static final String CHANNEL_PREV = "prev";
    public static final String CHANNEL_NEXT = "next";
    public static final String CHANNEL_PLAY = "play";

    public static Notification notification;

    public static void PlayBarNotification(Context context, Track track, int playButton, int pos, int size) {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
        PendingIntent pendingIntentPrev;
        int drw_previous;
        if (pos == 0) {
            pendingIntentPrev = null;
            drw_previous = 0;
        } else {
            Intent intentPrev = new Intent(context, NotificationActionService.class)
                    .setAction(CHANNEL_PREV);
            pendingIntentPrev = PendingIntent.getBroadcast(context, 0
                    , intentPrev, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_previous = R.drawable.ic_skip_previous_black_24dp;
        }
        Intent intentPlay = new Intent(context, NotificationActionService.class)
                .setAction(CHANNEL_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0
                , intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
        // next
        PendingIntent pendingIntentNext;
        int drw_Next;
        if (pos == size) {
            pendingIntentNext = null;
            drw_Next = 0;
        } else {
            Intent intentNext = new Intent(context, NotificationActionService.class)
                    .setAction(CHANNEL_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context, 0
                    , intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_Next = R.drawable.ic_skip_next_black_24dp;
        }


        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_transparent)
                .setContentTitle(track.getmTitle())
                .setContentText(track.getmArtist())
                .setLargeIcon(track.getImageBitmap())
                .setColor(context.getColor(R.color.colorPrimaryDark))
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(drw_previous, "Previous", pendingIntentPrev)
                .addAction(playButton, "Play", pendingIntentPlay)
                .addAction(drw_Next, "Next", pendingIntentNext)
                .setStyle(new androidx.media.app.NotificationCompat.DecoratedMediaCustomViewStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        notificationManagerCompat.notify(1, notification);
    }
}
