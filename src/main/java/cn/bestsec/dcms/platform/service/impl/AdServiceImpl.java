package cn.bestsec.dcms.platform.service.impl;

import org.springframework.stereotype.Service;

import cn.bestsec.dcms.platform.mqtt.MqttMessageHandler;
import cn.bestsec.dcms.platform.service.AdService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月22日 下午5:14:07
 */
@Service
public class AdServiceImpl implements AdService {

    @Override
    public void sync() {
        //MqttMessageHandler.mqttPublish("broadcast", "test " + System.currentTimeMillis());
    }

}
