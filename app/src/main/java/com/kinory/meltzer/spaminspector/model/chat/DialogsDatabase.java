package com.kinory.meltzer.spaminspector.model.chat;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gilad Kinory on 10/10/2017.
 * e-mail: giladkinory2000@gmail.com
 */

public class DialogsDatabase {

    private Map<String, Dialog> sendersDialogsMap = new LinkedHashMap<>();

    /**
     * Adds a message to the database.
     * @param sender The sender of the message
     * @param message The message to add
     */
    public void addMessage(String sender, IMessage message) {
        if (!sendersDialogsMap.containsKey(sender)) {
            Dialog dialog = new Dialog(new User(sender));
            sendersDialogsMap.put(sender, dialog);
            dialog.addMessage(message);
        } else {
            sendersDialogsMap.get(sender).addMessage(message);
        }
    }

    /**
     * @return All of the senders in a set (ordered according to insertion)
     */
    public Set<String> getSenders() {
        return sendersDialogsMap.keySet();
    }

    /**
     * Gets all the messages of a given sender.
     * @param sender The sender to get it's messages
     * @return All the messages of the sender (in a TreeSet order by date of sending)
     */
    public Dialog getDialog(String sender) {
        return sendersDialogsMap.get(sender);
    }

    public Collection<Dialog> getAllDialogs() {
        return sendersDialogsMap.values();
    }

    public boolean containsSender(String sender) {
        return sendersDialogsMap.containsKey(sender);
    }

    public void clear() {
        sendersDialogsMap.clear();
    }
}
