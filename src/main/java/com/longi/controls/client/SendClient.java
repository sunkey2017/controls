package com.longi.controls.client;

import com.longi.controls.utils.CommonUtil;
import com.longi.controls.utils.ConnectUtil;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * @version 1.0
 * @CalssName SendClient
 * @Author sunke5
 * @Date 2019-11-19 16:34
 */
public class SendClient {

    static final Logger log = Logger.getLogger(SendClient.class);

    private static String host = "tcp://10.0.10.98:61613";
    private static String userName = "admin";
    private static String passWord = "password";
    private static MqttClient client;
    private static final String clientId = "client22";

    //本次测试订阅的主题：test
    private static String topicStr = "test";

    public static void initMQTT() throws MqttException {
        //MemoryPersistence设置clientId的保存形式，默认为以内存保存
        if(client==null){
            client = new MqttClient(host, clientId,new MqttDefaultFilePersistence());
        }
        MqttTopic topic = client.getTopic(topicStr);
        //连接MQTT服务
        client = ConnectUtil.initConnection(topic,userName,passWord,client);

        //订阅
        client.subscribe(topicStr, 0);
        //取消订阅
        //client.unsubscribe(topicStr);

    }

    public static void main(String[] args) throws MqttException{
        initMQTT();
        while (true){
            try {
                Thread.sleep(5000);
                ConnectUtil.PushMsg(client,"客户端发送："+System.currentTimeMillis());
            }catch (Exception e){
                CommonUtil.getExceptionDetail(e);
            }
        }
    }

}
