package com.kinory.meltzer.spaminspector.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.model.Utils;
import com.kinory.meltzer.spaminspector.model.chat.Dialog;
import com.kinory.meltzer.spaminspector.model.chat.Message;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity {

    private MessagesListAdapter<IMessage> messagesAdapter;
    private SmsManager smsManager = SmsManager.getDefault();
    private Dialog dialog;
    private static DialogActivity current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        dialog = (Dialog) getIntent().getSerializableExtra("dialog");

        // Sets up the adapter and connects it to the messagesListView
        MessagesList messageList = (MessagesList) findViewById(R.id.messagesList);
        messagesAdapter = new MessagesListAdapter<>(dialog.getId(), null);
        messageList.setAdapter(messagesAdapter);

        // Adds all the messages of the dialog that aren't yet in the list
        for (IMessage message: dialog.getMessages()) {
            messagesAdapter.addToStart(message, true);
        }


        // Add an input listener in order to send new message when the send button is clicked
        MessageInput messageInput = (MessageInput) findViewById(R.id.input);
        messageInput.setInputListener(input -> {
            sendMessage(input.toString());
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrent(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setCurrent(null);
    }

    /**
     * Sends a message.
     * @param message The message to send.
     */
    private void sendMessage(String message) {
        // Asks for permissions if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Utils.getPermissionToReadSMS(this);
        } else {

            // Sends the message
            smsManager.sendTextMessage(dialog.getUser().getId(), null, message, null, null);
        }
    }

    public static DialogActivity getCurrent() {
        return current;
    }

    public static void setCurrent(DialogActivity current) {
        DialogActivity.current = current;
    }

    public static boolean isCurrentlyRunning() {
        return current != null;
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Adds a message to the dialog (and the list)
     * @param message The new message to add.
     */
    public void addMessage(IMessage message) {
        dialog.addMessage(message);
        messagesAdapter.addToStart(message, true);
    }
}
