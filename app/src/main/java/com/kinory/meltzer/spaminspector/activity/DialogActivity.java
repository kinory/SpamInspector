package com.kinory.meltzer.spaminspector.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.model.Dialog;
import com.kinory.meltzer.spaminspector.model.Message;
import com.kinory.meltzer.spaminspector.model.User;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Collections;
import java.util.Date;

public class DialogActivity extends AppCompatActivity {

    private MessagesListAdapter<IMessage> messagesAdapter;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        dialog = (Dialog) getIntent().getSerializableExtra("dialog");

        // Sets up the adapter and connects it to the messagesListView
        MessagesList messageList = (MessagesList) findViewById(R.id.messagesList);
        messagesAdapter = new MessagesListAdapter<>(dialog.getId(), null);
        messageList.setAdapter(messagesAdapter);

        messagesAdapter.addToEnd(Collections.singletonList(dialog.getLastMessage()), false);
        messagesAdapter.addToStart(new Message("2", "Hello", new User("2", "Daniel Meltzer", "Meltzer"), new Date()), true);
    }

//    /**
//     * Sets the messages list in the messageListView.
//     * @param messages The messages to put in the list view.
//     */
//    public void setMessages(Collection<String> messages) {
//        messagesAdapter.setMessages(messages);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Gets the updated set of messages from the shared preferences
//        SharedPreferences preferences = getSharedPreferences(getString(R.string.messages_key), Context.MODE_PRIVATE);
//        Set<String> messages = preferences.getStringSet(getString(R.string.messages_key), new HashSet<>());
//
//        // Adds the messages to messagesListView
//        setMessages(messages);
//    }
}
