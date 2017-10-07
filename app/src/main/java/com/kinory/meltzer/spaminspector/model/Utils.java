package com.kinory.meltzer.spaminspector.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.activity.MainActivity;

/**
 * Created by Gilad Kinory on 07/10/2017.
 * e-mail: giladkinory2000@gmail.com
 */

public class Utils {

    private static int currentNotificationId = 100;

    /**
     * Sends a notification that leads to the MainActivity
     * @param context The context
     * @param title The notification's title
     * @param contentText The content of the notification
     * @return The unique id of the notification (to be used for tracking and canceling).
     */
    public static int sendNotification(Context context, String title, String contentText) {

        // The id of the channel.
        String CHANNEL_ID = "spam_inspector_messages";

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setChannel(CHANNEL_ID)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(alarmSound);


        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);

        // Gets the notification manager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Sends the notification
        notificationManager.notify(++currentNotificationId, builder.build());

        return currentNotificationId;
    }

}
