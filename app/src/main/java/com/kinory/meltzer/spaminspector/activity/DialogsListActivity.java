package com.kinory.meltzer.spaminspector.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.model.Utils;
import com.kinory.meltzer.spaminspector.model.chat.Dialog;
import com.kinory.meltzer.spaminspector.model.chat.Message;
import com.kinory.meltzer.spaminspector.model.chat.DialogsDatabase;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DialogsListActivity extends AppCompatActivity {

    private DialogsDatabase dialogsDatabase;
    private DialogsListAdapter<Dialog> dialogsListAdapter;
    private static DialogsListActivity current;
    private static boolean isDialogsListUpToDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs_list);

        dialogsDatabase = new DialogsDatabase();

        DialogsList dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        dialogsListAdapter = new DialogsListAdapter<>(null);
        dialogsList.setAdapter(dialogsListAdapter);

        // Start a DialogActivity when a dialog is clicked
        dialogsListAdapter.setOnDialogClickListener(dialog -> {
            Intent intent = new Intent(this, DialogActivity.class);
            intent.putExtra("dialog", dialog);
            startActivity(intent);
        });

        // Refreshes sms inbox (asks for permissions first, if needed)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Utils.getPermissionToReadSMS(this);
        } else {
            refreshInbox();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrent(this);
        if (!isDialogsListUpToDate()) refreshInbox();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setCurrent(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        // Refreshes the inbox if sms permissions were granted
        if (requestCode == Utils.READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                refreshInbox();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Refreshes the sms inbox
     */
    private void refreshInbox() {
        // Accesses the sms database
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        // Gets the database keys
        assert smsInboxCursor != null;
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int indexDateSent = smsInboxCursor.getColumnIndex("date_sent");

        // Makes sure that the sms database isn't empty
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;

        // Clears the message database and the dialogs list
        dialogsDatabase.clear();
        dialogsListAdapter.clear();

        // Iterates over the messages
        do {

            // Gets the data of the message from the sms database
            String sender = smsInboxCursor.getString(indexAddress);
            String text = smsInboxCursor.getString(indexBody);
            long dateSent = smsInboxCursor.getLong(indexDateSent);

            // Adds the message to the message database
            Message message = Utils.createMessageFromData(sender, text, dateSent);
            dialogsDatabase.addMessage(sender, message);

        } while (smsInboxCursor.moveToNext());

        dialogsListAdapter.addItems(new ArrayList<>(dialogsDatabase.getAllDialogs()));

        setIsDialogsListUpToDate(true);
    }

    /**
     * Adds a list of messages to the inbox
     * @param messages The list of messages to add.
     */
    public void addMessagesToInbox(List<? extends IMessage> messages) {
        for (IMessage message: messages) {
            String sender = message.getUser().getId();
            boolean dialogExists = dialogsDatabase.containsSender(sender);
            dialogsDatabase.addMessage(sender, message);
            if (!dialogExists) dialogsListAdapter.addItem(dialogsDatabase.getDialog(sender));
        }
        dialogsListAdapter.notifyDataSetChanged();
    }

    public static DialogsListActivity getCurrent() {
        return current;
    }

    public static void setCurrent(DialogsListActivity current) {
        DialogsListActivity.current = current;
    }

    public static boolean isCurrentlyRunning() {
        return current != null;
    }

    public static boolean isDialogsListUpToDate() {
        return isDialogsListUpToDate;
    }

    public static void setIsDialogsListUpToDate(boolean isDialogsListUpToDate) {
        DialogsListActivity.isDialogsListUpToDate = isDialogsListUpToDate;
    }
}
