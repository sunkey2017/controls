package com.longi.controls;

import com.longi.controls.service.PushCallback;
import com.longi.controls.utils.CommonUtil;
import com.longi.controls.utils.ConnectUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @version 1.0
 * @CalssName MainService
 * @Author sunke5
 * @Date 2019-11-18 17:00
 */
public class MainService {
    public static final String HOST = "tcp://10.0.10.98:1883";
    public static final String TOPIC = "toclient/98";
    public static final String TOPIC99 = "toclient/99";
    private static final String clientid = "client98";

    private MqttClient client;
    private MqttTopic topic;
    private MqttTopic topic99;
    private String userName = "admin";
    private String passWord = "password";

    private MqttMessage message;

    public MainService() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        client = ConnectUtil.initConnection(topic,userName,passWord,client);
        try {
            //client.setCallback(new PushCallback());
            topic = client.getTopic(TOPIC);
            topic99 = client.getTopic(TOPIC99);
        } catch (Exception e) {
            CommonUtil.getExceptionDetail(e);
        }
    }


    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    public static void main(String[] args) throws MqttException {
        MainService server = new MainService();

        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload("给客户端98推送的信息".getBytes());
        server.publish(server.topic , server.message);

        server.message = new MqttMessage();
        server.message.setQos(2);
        server.message.setRetained(true);
        server.message.setPayload("给客户端99推送的信息".getBytes());
        server.publish(server.topic99 , server.message);

        System.out.println(server.message.isRetained() + "------ratained状态");
    }
}
