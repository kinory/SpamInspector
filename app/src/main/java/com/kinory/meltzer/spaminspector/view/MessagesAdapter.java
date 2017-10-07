package com.kinory.meltzer.spaminspector.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kinory.meltzer.spaminspector.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gilad Kinory on 06/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * An adapter for the messages list view.
 */

public class MessagesAdapter extends BaseAdapter {

    private Context context;
    private List<String> messages = new ArrayList<>();  // The list of messages

    public MessagesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        // The number of messages
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        // The i'th message
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
        notifyDataSetInvalidated();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.message_layout, viewGroup, false);
        }

        TextView messageTextView = view.findViewById(R.id.messageTextView);
        messageTextView.setText(messages.get(i));

        return view;
    }

}
