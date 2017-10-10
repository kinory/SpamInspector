package com.kinory.meltzer.spaminspector.model;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Gilad Kinory on 10/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A comparator that compares 2 messages according to their dates of creation
 */

public class MessageComparator implements Comparator<IMessage>, Serializable {

    @Override
    public int compare(IMessage o1, IMessage o2) {
        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
    }
}
