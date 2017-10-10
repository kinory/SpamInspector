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

    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return name;
    }

}
