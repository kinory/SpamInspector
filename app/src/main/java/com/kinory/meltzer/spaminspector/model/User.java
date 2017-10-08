package com.kinory.meltzer.spaminspector.model;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;

/**
 * Created by Gilad Kinory on 08/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class representing a user.
 */

public class User implements IUser, Serializable {

    private String id;
    private String name;
    private String avatar;

    public User(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

}
