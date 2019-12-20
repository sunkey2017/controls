package com.longi.controls.subject;

import com.longi.controls.listener.MessageAddedListener;
import com.longi.controls.service.MessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @CalssName MsgSubject
 * @Author sunke5
 * @Date 2019-11-18 21:33
 */
public class MsgSubject {

    private List<MessageService> messages = new ArrayList<>();

    private List<MessageAddedListener> listeners = new ArrayList<>();

    public void addMsg (MessageService msg) {
        // Add the message to the list of msgs
        this.messages.add(msg);
        // Notify the list of registered listeners
        this.notifyMsgAddedListeners(msg);
    }
    public MessageAddedListener registerMsgAddedListener (MessageAddedListener listener) {
        // Add the listener to the list of registered listeners
        this.listeners.add(listener);
        return listener;
    }
    public void unregisterMsgAddedListener (MessageAddedListener listener) {
        // Remove the listener from the list of the registered listeners
        this.listeners.remove(listener);
    }
    protected void notifyMsgAddedListeners (MessageService msg) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onMsgAdded(msg));
    }
}
