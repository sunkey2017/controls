package com.longi.controls.utils;

import com.longi.controls.service.PushCallback;
import org.eclipse.paho.client.mqttv3.*;

/**
 * @version 1.0
 * @CalssName ConnectUtil
 * @Author sunke5
 * @Date 2019-11-19 11:36
 */
public class ConnectUtil {

    //自己写发布消息的方法，然后循环调用。
    public static MqttConnectOptions setOptions(MqttTopic topic,String userName, String passWord){
        MqttConnectOptions options = new MqttConnectOptions();
        //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        //这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        //设置连接的用户名
        options.setUserName(userName);
        //设置连接的密码
        options.setPassword(passWord.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);

        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
        options.setWill(topic, "close".getBytes(), 2, true);

        return options;
    }

    public static MqttClient initConnection(MqttTopic topic,String userName, String passWord, MqttClient client) throws MqttException {

        MqttConnectOptions options = setOptions(topic,userName,passWord);
        //回调
        client.setCallback(new PushCallback());
        //链接
        client.connect(options);
        return client;
    }

    //自己写发布消息的方法，然后循环调用。
    public static void PushMsg(MqttClient client,String msg){
        MqttMessage m = new MqttMessage();
        m.setQos(1);
        m.setRetained(true);
        m.setPayload(msg.getBytes());
        try {
            client.publish("test", m);
        }catch(Exception e){
            System.out.println("发布消息失败-->"+msg);
            CommonUtil.getExceptionDetail(e);
        }
    }
}
