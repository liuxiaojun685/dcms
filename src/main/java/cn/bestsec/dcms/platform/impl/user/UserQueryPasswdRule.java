package cn.bestsec.dcms.platform.impl.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_QueryPasswdRuleApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.User_QueryPasswdRuleRequest;
import cn.bestsec.dcms.platform.api.model.User_QueryPasswdRuleResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 获取用户密码规则 终端用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月9日 下午4:45:58
 */
@Component
public class UserQueryPasswdRule implements User_QueryPasswdRuleApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public User_QueryPasswdRuleResponse execute(User_QueryPasswdRuleRequest user_QueryPasswdRuleRequest)
            throws ApiException {
        User_QueryPasswdRuleResponse resp = new User_QueryPasswdRuleResponse();
        // 获取系统配置信息
        SystemConfig config = systemConfigService.getSystemConfig();
        resp.setContainsLetter(config.getLocalAuthPasswdContainsLetter());
        resp.setContainsNumber(config.getLocalAuthPasswdContainsNumber());
        resp.setContainsSpecial(config.getLocalAuthPasswdContainsSpecial());
        resp.setMaxLength(config.getLocalAuthPasswdMax());
        resp.setMinLength(config.getLocalAuthPasswdMin());
        return resp;
    }

}
