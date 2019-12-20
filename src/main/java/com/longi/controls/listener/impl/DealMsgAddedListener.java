package com.longi.controls.listener.impl;

import com.longi.controls.listener.MessageAddedListener;
import com.longi.controls.service.MessageService;

/**
 * @version 1.0
 * @CalssName PrintNameAnimalAddedListener
 * @Author sunke5
 * @Date 2019-11-17 21:34
 */
public class DealMsgAddedListener implements MessageAddedListener {

    @Override
    public void onMsgAdded(MessageService msgServer) {
        //
        System.out.println("新增一个消息 : '" + msgServer.getMsg() + "'");
    }
}
