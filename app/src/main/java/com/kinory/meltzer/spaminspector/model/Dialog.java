package com.kinory.meltzer.spaminspector.model;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Gilad Kinory on 08/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class representing a dialog (a chat) with a single user.
 */

public class Dialog implements IDialog, Serializable {

    // A TreeSet with a comparator that compares 2 messages according to their dates of creation
    private TreeSet<IMessage> messages = new TreeSet<>();

    private IUser user;

    public Dialog(IUser user) {
        this.user = user;
    }

    /**
     * Adds a message to the dialog.
     * @param message The message to add
     */
    public void addMessage(IMessage message) {
        messages.add(message);
    }

    public TreeSet<IMessage> getMessages() {
        return messages;
    }

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public String getDialogPhoto() {
        return null;
    }

    @Override
    public String getDialogName() {
        return user.getName();
    }

    @Override
    public List<? extends IUser> getUsers() {
        return Collections.singletonList(user);
    }

    @Override
    public IMessage getLastMessage() {
        return messages.last();
    }

    @Override
    public void setLastMessage(IMessage message) {
        messages.add(message);
    }

    @Override
    public int getUnreadCount() {
        return 0;
    }
}
