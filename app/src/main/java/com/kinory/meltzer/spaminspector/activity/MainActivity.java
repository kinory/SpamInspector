package com.kinory.meltzer.spaminspector.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.view.MessagesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up the adapter and connects it to the messagesListView
        ListView messageListView = (ListView) findViewById(R.id.messagesListView);
        messagesAdapter = new MessagesAdapter(this);
        messageListView.setAdapter(messagesAdapter);
    }

    /**
     * Sets the messages list in the messageListView.
     * @param messages The messages to put in the list view.
     */
    public void setMessages(Collection<String> messages) {
        messagesAdapter.setMessages(messages);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Gets the updated set of messages from the shared preferences
        SharedPreferences preferences = getSharedPreferences(getString(R.string.messages_key), Context.MODE_PRIVATE);
        Set<String> messages = preferences.getStringSet(getString(R.string.messages_key), new HashSet<>());

        // Adds the messages to messagesListView
        setMessages(messages);
    }
}
