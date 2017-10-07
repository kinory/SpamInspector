package com.kinory.meltzer.spaminspector.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kinory.meltzer.spaminspector.R;
import com.kinory.meltzer.spaminspector.view.MessagesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView messageListView;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageListView = (ListView) findViewById(R.id.messagesListView);
        messagesAdapter = new MessagesAdapter(this);
        messageListView.setAdapter(messagesAdapter);
    }

    public void addMessages(List<String> messages) {
        messagesAdapter.setMessages(messages);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<String> messages = getIntent().getStringArrayListExtra("messages");
        if (messages != null) {
            addMessages(messages);
        }
    }
}
