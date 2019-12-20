package com.longi.controls;

import com.longi.controls.listener.MessageAddedListener;
import com.longi.controls.listener.impl.CountingMsgAddedListener;
import com.longi.controls.listener.impl.DealMsgAddedListener;
import com.longi.controls.service.MessageService;
import com.longi.controls.subject.MsgSubject;

/**
 * @version 1.0
 * @CalssName Main
 * @Author sunke5
 * @Date 2019-11-17 21:20
 */
public class Main {
   public static void main (String[] args) {
       MsgSubject msgSub = new MsgSubject();
       // Register listeners to be notified when a msg is added
       MessageAddedListener listener1 = msgSub.registerMsgAddedListener(new DealMsgAddedListener());
       MessageAddedListener listener2 = msgSub.registerMsgAddedListener(new CountingMsgAddedListener());
       // Add an message notify the registered listeners
       msgSub.addMsg(new MessageService("Add-1号炉子加料"));
       msgSub.addMsg(new MessageService("Add-2号炉子加料"));

       //Cancel one of the listener
      // msgSub.unregisterMsgAddedListener(listener1);

       msgSub.addMsg(new MessageService("Add-3号炉子加料"));
   }
}
