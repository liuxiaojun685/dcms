package cn.bestsec.dcms.platform.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月8日 下午2:33:31
 */
public interface MqttMessageService {
    void receive(MqttMessage message);

    void send(String uid, String type, String msg, String url);

    void flush(String to);

    void push(String uid, String type, String msg, String url);

}
