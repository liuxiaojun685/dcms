package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublishSample {
	public static void main(String[] args) throws Exception {
		String topic = "topic/5166@xnjc";
		@SuppressWarnings("unused")
		String json = "{\"ctime\":1493884035077,\"msg\":\"您有一个流程待审批，点击查看详情  [新建 文本文档.txt]\",\"stime\":1493884035093,\"type\":\"apv\",\"url\":\"dcms/dcms_client_web/myselfInfo.html?workflowId=93\"}";
		String content = "===================hahahahhahahahahahhahaha============================";
		int qos = 0;
		String broker = "tcp://47.93.78.186:1883";
		String clientId = "server123";
		MemoryPersistence persistence = new MemoryPersistence();

		MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		System.out.println("Connecting to broker: " + broker);
		sampleClient.connect(connOpts);
		System.out.println("Connected");
		System.out.println("Publishing message: " + json);
		MqttMessage message = new MqttMessage(json.getBytes());
		message.setQos(qos);

		sampleClient.publish(topic + "|24f4802a8ff5573fd0e56f573ebb7be7", message);
		System.out.println("Message published");

		sampleClient.disconnect();

	}
}
