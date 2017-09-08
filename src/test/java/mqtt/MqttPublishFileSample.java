package mqtt;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublishFileSample {
    public static void main(String[] args) throws Exception {
        String topic = "topic/5166@xnjc";
        File file = new File("/Users/xuzewei/java/workspace/dcms/src/main/webapp/raml/api.raml");
        int qos = 2;
        String broker = "tcp://139.196.17.121:1883";
        MqttClient sampleClient = new MqttClient(broker, UUID.randomUUID().toString());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: " + broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");
        System.out.println("Publishing message: " + file);
        FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        IOUtils.read(is , buffer);
        MqttMessage message = new MqttMessage(buffer);
        message.setQos(qos);

        sampleClient.publish(topic, message);
        System.out.println("Message published");

        sampleClient.disconnect();

    }
}
