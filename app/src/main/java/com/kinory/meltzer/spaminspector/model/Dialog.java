package com.kinory.meltzer.spaminspector.model;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Gilad Kinory on 08/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class representing a dialog (a chat) with a single user.
 */

public class Dialog implements IDialog, Serializable {

    private String id;
    private IUser user;

    public Dialog(String id, IUser user) {
        this.id = id;
        this.user = user;
    }

    @Override
    public String getId() {
        return id;
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
        return new Message("1", "dummy message", new User("1", "Gilad Kinory", "Kinory"), new Date());
    }

    @Override
    public void setLastMessage(IMessage message) {

    }

    @Override
    public int getUnreadCount() {
        return 0;
    }
}
