package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateLocalAuthConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLocalAuthConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLocalAuthConfigResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改本地认证配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午4:13:17
 */
@Component
public class SystemConfigUpdateLocalAuthConfig implements SystemConfig_UpdateLocalAuthConfigApi {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateLocalAuthConfigResponse execute(
            SystemConfig_UpdateLocalAuthConfigRequest systemConfig_UpdateLocalAuthConfigRequest) throws ApiException {
        SystemConfig_UpdateLocalAuthConfigResponse resp = new SystemConfig_UpdateLocalAuthConfigResponse();
        // 获取配置信息
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        // 是否启用本地认证
        Integer localAuthEnable = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthEnable();
        if (localAuthEnable != null) {
            systemConfig.setLocalAuthEnable(localAuthEnable);
        }
        // 新用户初始密码
        String localAuthInitPasswd = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthInitPasswd();
        if (localAuthInitPasswd != null) {
            systemConfig.setLocalAuthInitPasswd(StringEncrypUtil.encrypt(localAuthInitPasswd));
        }
        // 是否强制修改初始密码
        Integer localAuthInitPasswdForceChange = systemConfig_UpdateLocalAuthConfigRequest
                .getLocalAuthInitPasswdForceChange();
        if (localAuthInitPasswdForceChange != null) {
            systemConfig.setLocalAuthInitPasswdForceChange(localAuthInitPasswdForceChange);
        }
        // 密码复杂度(字母大小写) 1必须包含 0可不包含
        Integer localAuthPasswdContainsLetter = systemConfig_UpdateLocalAuthConfigRequest
                .getLocalAuthPasswdContainsLetter();
        if (localAuthPasswdContainsLetter != null) {
            systemConfig.setLocalAuthPasswdContainsLetter(localAuthPasswdContainsLetter);
        }
        // 密码复杂度(数字) 1必须包含 0可不包含
        Integer localAuthPasswdContainsNumber = systemConfig_UpdateLocalAuthConfigRequest
                .getLocalAuthPasswdContainsNumber();
        if (localAuthPasswdContainsNumber != null) {
            systemConfig.setLocalAuthPasswdContainsNumber(localAuthPasswdContainsNumber);
        }
        // 密码复杂度(特殊字符) 1必须包含 0可不包含
        Integer localAuthPasswdContainsSpecial = systemConfig_UpdateLocalAuthConfigRequest
                .getLocalAuthPasswdContainsSpecial();
        if (localAuthPasswdContainsSpecial != null) {
            systemConfig.setLocalAuthPasswdContainsSpecial(localAuthPasswdContainsSpecial);
        }
        // 是否强制定期修改密码 1是 0否
        Integer localAuthPasswdForceChange = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthPasswdForceChange();
        if (localAuthPasswdForceChange != null) {
            systemConfig.setLocalAuthPasswdForceChange(localAuthPasswdForceChange);
        }
        // 密码连续输入错误次数
        Integer localAuthPasswdLockThreshold = systemConfig_UpdateLocalAuthConfigRequest
                .getLocalAuthPasswdLockThreshold();
        if (localAuthPasswdLockThreshold != null) {
            systemConfig.setLocalAuthPasswdLockThreshold(localAuthPasswdLockThreshold);
        }
        // 密码错误锁定时间设置 毫秒
        Long localAuthPasswdLockTime = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthPasswdLockTime();
        if (localAuthPasswdLockTime != null) {
            systemConfig.setLocalAuthPasswdLockTime(localAuthPasswdLockTime);
        }
        // 本地认证密码最大长度
        Integer localAuthPasswdMax = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthPasswdMax();
        if (localAuthPasswdMax != null) {
            systemConfig.setLocalAuthPasswdMax(localAuthPasswdMax);
        }
        // 本地认证密码最小长度
        Integer localAuthPasswdMin = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthPasswdMin();
        if (localAuthPasswdMin != null) {
            systemConfig.setLocalAuthPasswdMin(localAuthPasswdMin);
        }
        // 定期修改密码时间设置 毫秒
        Long localAuthPasswdPeriod = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthPasswdPeriod();
        if (localAuthPasswdPeriod != null) {
            systemConfig.setLocalAuthPasswdPeriod(localAuthPasswdPeriod);
        }
        // 是否必须管理员解锁 1是 0否
        Integer localAuthUnlockByAdmin = systemConfig_UpdateLocalAuthConfigRequest.getLocalAuthUnlockByAdmin();
        if (localAuthUnlockByAdmin != null) {
            systemConfig.setLocalAuthUnlockByAdmin(localAuthUnlockByAdmin);
        }

        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateLocalAuthConfigRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_localAuth)
                .admin(systemConfig_UpdateLocalAuthConfigRequest.tokenWrapper().getAdmin()).operateDescription();
        return resp;
    }

}
