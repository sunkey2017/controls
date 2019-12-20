package com.longi.controls.service;

import com.longi.controls.utils.CommonUtil;
import com.longi.controls.utils.ConnectUtil;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @version 1.0
 * @CalssName Server
 * @Author sunke5
 * @Date 2019-11-19 16:24
 */
public class Server {

    static final Logger log = Logger.getLogger(Server.class);

    private static String HOST = "tcp://10.0.10.98:61613";
    private static String userName = "admin";
    private static String passWord = "password";
    public static final String TOPIC1 = "test";
    public static final String TOPIC2 = "sunke";
    private static final String topicStr = "server";

    private MqttClient client;
    private MqttTopic topic11;
    private MqttTopic topic22;
    private MqttMessage message;

    public Server() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, topicStr, new MemoryPersistence());
        //client = new MqttClient(HOST, topicStr, new MqttDefaultFilePersistence());

        MqttTopic topic = client.getTopic(topicStr);
        MqttConnectOptions options = ConnectUtil.setOptions(topic,userName,passWord);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic11 = client.getTopic(TOPIC1);
            topic22 = client.getTopic(TOPIC2);
        } catch (Exception e) {
            log.info(CommonUtil.getExceptionDetail(e));
        }
    }

    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        log.info("message is published completely! "+ token.isComplete());
    }

    public static void main(String[] args) throws MqttException {
        Server server = new Server();

        server.message = new MqttMessage();
        server.message.setQos(0);
        server.message.setRetained(true);
        server.message.setPayload("给客户端11推送的信息".getBytes());
        server.publish(server.topic11 , server.message);

        server.message = new MqttMessage();
        server.message.setQos(2);
        server.message.setRetained(true);
        server.message.setPayload("给客户端22推送的信息".getBytes());
        server.publish(server.topic22 , server.message);

        log.info(server.message.isRetained() + "------ratained状态");
    }
}
