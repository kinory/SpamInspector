package com.kinory.meltzer.spaminspector.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.kinory.meltzer.spaminspector.activity.DialogActivity;
import com.kinory.meltzer.spaminspector.activity.DialogsListActivity;
import com.kinory.meltzer.spaminspector.model.chat.Dialog;
import com.kinory.meltzer.spaminspector.model.chat.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gilad Kinory on 06/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class that manages the incoming text messages.
 */

public class SmsListener extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();
        List<Message> messages = new ArrayList<>();


        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);

            // Iterates over the messages
            assert sms != null;
            for (Object sm : sms) {
                String format = intentExtras.getString("format");
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm, format);

                // Gets the data of the message
                String sender = smsMessage.getOriginatingAddress();
                String smsBody = smsMessage.getMessageBody();
                long dateSent = smsMessage.getTimestampMillis();

                // Creates the message object from the data
                Message message = Utils.createMessageFromData(sender, smsBody, dateSent);

                messages.add(message);
            }

            DialogsListActivity.setIsDialogsListUpToDate(false);

            // Refreshes the DialogsListActivity if it's running
            if (DialogsListActivity.isCurrentlyRunning()) {
                DialogsListActivity.getCurrent().addMessagesToInbox(messages);
                DialogsListActivity.setIsDialogsListUpToDate(true);
            }

            // Refreshes the DialogActivity if it's running
            if (DialogActivity.isCurrentlyRunning()) {
                // Gets the current dialog
                DialogActivity dialogActivity = DialogActivity.getCurrent();
                Dialog dialog = dialogActivity.getDialog();
                String userId = dialog.getUser().getId();

                // Adds the messages matching to the current dialog
                messages.stream()
                        .filter(message -> message.getUser().getId().equals(userId))
                        .forEach(dialogActivity::addMessage);
            }

            // Notifies the user about the new messages
            for (Message message: messages)
                Utils.sendMessageNotification(context, message);
        }
    }

}
