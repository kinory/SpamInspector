package com.kinory.meltzer.spaminspector.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kinory.meltzer.spaminspector.R;

import java.util.ArrayList;
import java.util.Collection;
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
        // The i-th message
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setMessages(Collection<String> messages) {
        this.messages = new ArrayList<>(messages);

        // Refreshes the view
        notifyDataSetInvalidated();
    }

    /**
     * Adds a collection of messages to the ListView
     * @param newMessages The new messages to add
     */
    public void addMessages(Collection<String> newMessages) {
        this.messages.addAll(newMessages);

        // Refreshes the view
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // Inflates the view if it's null
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_layout, viewGroup, false);
        }

        // Configures the view
        TextView messageTextView = view.findViewById(R.id.messageTextView);
        messageTextView.setText(messages.get(i));

        return view;
    }

}
