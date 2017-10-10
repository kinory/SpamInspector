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
import android.widget.Toast;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.model.Dialog;
import com.kinory.meltzer.spaminspector.model.Message;
import com.kinory.meltzer.spaminspector.model.DialogsDatabase;
import com.kinory.meltzer.spaminspector.model.User;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class DialogsListActivity extends AppCompatActivity {

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    private DialogsDatabase dialogsDatabase = new DialogsDatabase();
    private DialogsListAdapter<Dialog> dialogsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs_list);

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
            getPermissionToReadSMS();
        } else {
            refreshSmsInbox();
        }

    }

    /**
     * Gets the needed permissions to read sms.
     */
    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                refreshSmsInbox();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Refreshes the sms inbox
     */
    private void refreshSmsInbox() {
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
            String id = String.format(Locale.getDefault(), "%s%d", sender, dateSent);
            User user = new User(sender);

            // Adds the message to the message database
            Message message = new Message(id, text, user, new Date(dateSent));
            dialogsDatabase.addMessage(sender, message);

        } while (smsInboxCursor.moveToNext());

        dialogsListAdapter.addItems(new ArrayList<>(dialogsDatabase.getAllDialogs()));
    }

}
