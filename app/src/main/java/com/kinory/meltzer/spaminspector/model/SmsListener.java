package com.kinory.meltzer.spaminspector.model;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.kinory.meltzer.spaminspector.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gilad Kinory on 06/10/2017.
 * e-mail: giladkinory2000@gmail.com
 */

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            ArrayList<String> messages = new ArrayList<>();
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                messages.add(messageBody);
            }

            try {
                Intent intentCall = new Intent(context, MainActivity.class);
                intentCall.putStringArrayListExtra("messages", messages);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentCall, 0);
                pendingIntent.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
