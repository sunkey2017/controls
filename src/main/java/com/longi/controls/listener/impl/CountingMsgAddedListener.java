package com.longi.controls.listener.impl;

import com.longi.controls.listener.MessageAddedListener;
import com.longi.controls.service.MessageService;

/**
 * @version 1.0
 * @CalssName CountingMsgAddedListener
 * @Author sunke5
 * @Date 2019-11-17 21:38
 */
public class CountingMsgAddedListener implements MessageAddedListener {
    private static int msgAddedCount = 0;
    @Override
    public void onMsgAdded(MessageService msgServer) {
        // Increment the number of messages
        msgAddedCount++;
        // Print the number of messages
        System.out.println("消息总数 : " + msgAddedCount);
    }
}
