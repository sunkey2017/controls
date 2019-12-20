package com.longi.controls.client;

import com.longi.controls.utils.CommonUtil;
import com.longi.controls.utils.ConnectUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @version 1.0
 * @CalssName ReceiveClient
 * @Author sunke5
 * @Date 2019-11-19 16:33
 */
public class ReceiveClient {

    private static String host = "tcp://10.0.10.98:61613";
    private static String userName = "admin";
    private static String passWord = "password";

    private static MqttClient client;
    private static final String clientId = "client21";
    private static String TOPIC = "BS";

    private void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            if(client==null){
                client = new MqttClient(host, clientId, new MemoryPersistence());
            }
            MqttTopic topic = client.getTopic(TOPIC);
            //连接MQTT服务
            client = ConnectUtil.initConnection(topic,userName,passWord,client);

            /**
             * 订阅消息
             *  qos ＝ 0：最多一次的传输
             *  qos ＝ 1：至少一次的传输
             *  qos ＝ 2：只有一次的传输
             **/
            int[] Qos  = {0};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);

        } catch (Exception e) {
            CommonUtil.getExceptionDetail(e);
        }
    }

    public static void main(String[] args) throws MqttException {
        ReceiveClient client = new ReceiveClient();
        client.start();
    }
}
