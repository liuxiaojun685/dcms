package cn.bestsec.dcms.platform.impl.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_RefreshMessageApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.User_RefreshMessageRequest;
import cn.bestsec.dcms.platform.api.model.User_RefreshMessageResponse;
import cn.bestsec.dcms.platform.service.MqttMessageService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月28日 下午1:50:15
 */
@Component
public class UserRefreshMessage implements User_RefreshMessageApi {
    @Autowired
    private MqttMessageService mqttMessageService;

    @Override
    @Transactional
    public User_RefreshMessageResponse execute(User_RefreshMessageRequest user_RefreshMessageRequest)
            throws ApiException {
        mqttMessageService.flush(user_RefreshMessageRequest.getToken());
        return new User_RefreshMessageResponse();
    }

}
