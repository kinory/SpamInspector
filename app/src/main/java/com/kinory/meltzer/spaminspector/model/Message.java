package com.kinory.meltzer.spaminspector.model;

import android.support.annotation.NonNull;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gilad Kinory on 08/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class representing a message.
 */

public class Message implements IMessage, Serializable, Comparable<Message> {

    private String id;
    private String text;
    private IUser user;
    private Date createdAt;

    public Message(String id, String text, IUser user, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int compareTo(@NonNull Message o) {
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
