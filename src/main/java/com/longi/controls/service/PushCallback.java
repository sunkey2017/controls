package com.longi.controls.service;

import com.longi.controls.utils.CommonUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.fusesource.hawtbuf.UTF8Buffer;


/**
 * MQTT订阅回调类
 *
 * @version 1.0
 * @CalssName PushCallback
 * @Author sunke5
 * @Date 2019-11-18 16:53
 */
public class PushCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("connectionLost----------");
        try {
            //initMQTT();
            System.out.println("连接断开，重连");
        }catch (Exception e){
            CommonUtil.getExceptionDetail(e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload(),"UTF-8"));
    }
}
