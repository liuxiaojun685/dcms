package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryLocalAuthConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLocalAuthConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLocalAuthConfigResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 获取本地认证配置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月29日下午4:40:39
 */
@Component
public class SystemConfigQueryLocalAuthConfig implements SystemConfig_QueryLocalAuthConfigApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryLocalAuthConfigResponse execute(
            SystemConfig_QueryLocalAuthConfigRequest systemConfig_QueryLocalAuthConfigRequest) throws ApiException {
        SystemConfig_QueryLocalAuthConfigResponse resp = new SystemConfig_QueryLocalAuthConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        resp.setLocalAuthEnable(systemConfig.getLocalAuthEnable());
        resp.setLocalAuthInitPasswd(StringEncrypUtil.decrypt(systemConfig.getLocalAuthInitPasswd()));
        resp.setLocalAuthInitPasswdForceChange(systemConfig.getLocalAuthInitPasswdForceChange());
        resp.setLocalAuthPasswdContainsLetter(systemConfig.getLocalAuthPasswdContainsLetter());
        resp.setLocalAuthPasswdContainsNumber(systemConfig.getLocalAuthPasswdContainsNumber());
        resp.setLocalAuthPasswdContainsSpecial(systemConfig.getLocalAuthPasswdContainsSpecial());
        resp.setLocalAuthPasswdForceChange(systemConfig.getLocalAuthPasswdForceChange());
        resp.setLocalAuthPasswdLockThreshold(systemConfig.getLocalAuthPasswdLockThreshold());
        resp.setLocalAuthPasswdLockTime(systemConfig.getLocalAuthPasswdLockTime());
        resp.setLocalAuthPasswdMax(systemConfig.getLocalAuthPasswdMax());
        resp.setLocalAuthPasswdMin(systemConfig.getLocalAuthPasswdMin());
        resp.setLocalAuthPasswdPeriod(systemConfig.getLocalAuthPasswdPeriod());
        resp.setLocalAuthUnlockByAdmin(systemConfig.getLocalAuthUnlockByAdmin());

        return resp;
    }

}
