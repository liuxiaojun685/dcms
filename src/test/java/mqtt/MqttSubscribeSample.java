package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscribeSample {

    public static void main(String[] args) throws Exception {
        String topic = "topic/5166@xnjc";
        String broker = "tcp://47.93.78.186:1883";
        String clientId = "java12";
        MemoryPersistence persistence = new MemoryPersistence();

        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: " + broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");

        sampleClient.subscribe(topic);
        sampleClient.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println(topic + " " + message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println(token);
            }

            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
            }
        });
    }
}
