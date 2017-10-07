package com.kinory.meltzer.spaminspector.model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.activity.MainActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gilad Kinory on 06/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class that manages the incoming text messages.
 */

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // When a text message is being received
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {

            Set<String> newMessages = new HashSet<>();

            // Iterates over the new messages
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

                // Adds the message's body to the set of messages
                String messageBody = smsMessage.getMessageBody();
                newMessages.add(messageBody);
            }

            // Adds the new messages to set of messages saved in the shared preferences
            SharedPreferences preferences =
                    context.getSharedPreferences(context.getString(R.string.messages_key), Context.MODE_PRIVATE);
            Set<String> messages = preferences.getStringSet(context.getString(R.string.messages_key), new HashSet<>());
            messages.addAll(newMessages);
            preferences.edit().putStringSet(context.getString(R.string.messages_key), messages).apply();

            // Sends a notification for each new message
            for (String message: newMessages) {
                Utils.sendNotification(context, "New Message", message);
            }
        }
    }

}
