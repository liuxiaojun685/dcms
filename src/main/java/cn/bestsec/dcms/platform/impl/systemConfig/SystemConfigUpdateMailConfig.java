package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateMailConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateMailConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateMailConfigResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改邮件告警配置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月29日下午3:35:23
 */
@Component
public class SystemConfigUpdateMailConfig implements SystemConfig_UpdateMailConfigApi {
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateMailConfigResponse execute(
            SystemConfig_UpdateMailConfigRequest systemConfig_UpdateMailConfigRequest) throws ApiException {
        SystemConfig_UpdateMailConfigResponse resp = new SystemConfig_UpdateMailConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        if (systemConfig_UpdateMailConfigRequest.getMailSenderAccount() != null) {
            systemConfig.setMailSenderAccount(systemConfig_UpdateMailConfigRequest.getMailSenderAccount());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailSenderPasswd() != null) {
            systemConfig.setMailSenderPasswd(
                    StringEncrypUtil.encrypt(systemConfig_UpdateMailConfigRequest.getMailSenderPasswd()));
        }
        if (systemConfig_UpdateMailConfigRequest.getMailSenderSmtpAddr() != null) {
            systemConfig.setMailSenderSmtpAddr(systemConfig_UpdateMailConfigRequest.getMailSenderSmtpAddr());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailSenderSmtpPort() != null) {
            systemConfig.setMailSenderSmtpPort(systemConfig_UpdateMailConfigRequest.getMailSenderSmtpPort());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailSenderSSLEnable() != null) {
            systemConfig.setMailSenderSslenable(systemConfig_UpdateMailConfigRequest.getMailSenderSSLEnable());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailRecvResourceAccount() != null) {
            systemConfig.setMailRecvResourceAccount(systemConfig_UpdateMailConfigRequest.getMailRecvResourceAccount());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailRecvResourceThreshold() != null) {
            systemConfig.setMailRecvResourceThreshold(
                    JSON.toJSONString(systemConfig_UpdateMailConfigRequest.getMailRecvResourceThreshold()));
        }
        if (systemConfig_UpdateMailConfigRequest.getMailRecvLogStorageAddr() != null) {
            systemConfig.setMailRecvLogStorageAddr(systemConfig_UpdateMailConfigRequest.getMailRecvLogStorageAddr());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailRecvLogStorageThreshold() != null) {
            systemConfig.setMailRecvLogStorageThreshold(
                    JSON.toJSONString(systemConfig_UpdateMailConfigRequest.getMailRecvLogStorageThreshold()));
        }
        if (systemConfig_UpdateMailConfigRequest.getMailRecvFileStorageAddr() != null) {
            systemConfig.setMailRecvFileStorageAddr(systemConfig_UpdateMailConfigRequest.getMailRecvFileStorageAddr());
        }
        if (systemConfig_UpdateMailConfigRequest.getMailRecvFileStorageThreshold() != null) {
            systemConfig.setMailRecvFileStorageThreshold(
                    JSON.toJSONString(systemConfig_UpdateMailConfigRequest.getMailRecvFileStorageThreshold()));
        }

        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateMailConfigRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_mail)
                .admin(systemConfig_UpdateMailConfigRequest.tokenWrapper().getAdmin()).operateDescription();
        return resp;
    }

}
