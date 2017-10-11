package com.kinory.meltzer.spaminspector.model;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.activity.DialogActivity;
import com.kinory.meltzer.spaminspector.activity.DialogsListActivity;
import com.kinory.meltzer.spaminspector.model.chat.Message;
import com.kinory.meltzer.spaminspector.model.chat.User;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Gilad Kinory on 07/10/2017.
 * e-mail: giladkinory2000@gmail.com
 */

public class Utils {

    public static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    private static int currentNotificationId = 100;


    /**
     * Sends a notification on a new message that leads to the DialogActivity
     * @param context The context
     * @param message The new message.
     * @return The unique id of the notification (to be used for tracking and canceling).
     */
    public static int sendMessageNotification(Context context, Message message) {

        // The id of the channel.
        String CHANNEL_ID = "spam_inspector_messages";

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentTitle(message.getUser().getName())
                        .setContentText(message.getText())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(alarmSound);


        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, DialogsListActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(DialogActivity.class);

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

    /**
     * Parses a boolean string ('True' or 'False') into a boolean
     * @param booleanString The string representing the boolean.
     * @return The boolean value of the string.
     */
    public static boolean parseBooleanString(String booleanString) {
        switch (booleanString) {
            case "True":
                return true;
            case "False":
                return false;
            default:
                throw new IllegalArgumentException("booleanString should be either True or False");
        }
    }

    /**
     * Parses a given string into a URL valid form.
     * @param string The string to parse
     * @return The string in the wanted format.
     */
    public static String parseToURL(String string) {
        return string.replaceAll(" ", "%20");
    }

    /**
     * Gets the needed permissions to read sms.
     */
    public static void getPermissionToReadSMS(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    /**
     * Creates a message by it's data.
     * @param sender The sender of the message.
     * @param text The text of the message.
     * @param dateSentMillis The creation date of the message (in milliseconds).
     * @return The Message object with the given data.
     */
    public static Message createMessageFromData(String sender, String text, long dateSentMillis) {
        String id = String.format(Locale.getDefault(), "%s%d", sender, dateSentMillis);
        User user = new User(sender);

        // Crates the message with the data
        return new Message(id, text, user, new Date(dateSentMillis));
    }
}
