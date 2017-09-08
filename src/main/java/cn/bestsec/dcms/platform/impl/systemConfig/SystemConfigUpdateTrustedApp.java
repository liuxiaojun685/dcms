package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateTrustedAppApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateTrustedAppRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateTrustedAppResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.TrustedApplicationDao;
import cn.bestsec.dcms.platform.entity.TrustedApplication;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改可信用程序
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年4月9日 下午2:10:05
 */
@Component
public class SystemConfigUpdateTrustedApp implements SystemConfig_UpdateTrustedAppApi {

    @Autowired
    private TrustedApplicationDao trustedApplicationDao;

    @Override
    @Transactional
    public SystemConfig_UpdateTrustedAppResponse execute(
            SystemConfig_UpdateTrustedAppRequest systemConfig_UpdateTrustedAppRequest) throws ApiException {
        TrustedApplication trustedApplication = trustedApplicationDao
                .findById(systemConfig_UpdateTrustedAppRequest.getTrustedAppId());
        trustedApplication.setDescription(systemConfig_UpdateTrustedAppRequest.getDescription());
        trustedApplication.setProcessName(systemConfig_UpdateTrustedAppRequest.getProcessName());
        trustedApplication.setExtensionName(systemConfig_UpdateTrustedAppRequest.getExtensionName());
        trustedApplicationDao.save(trustedApplication);

        // 操作日志
        AdminLogBuilder adminLogBuilder = systemConfig_UpdateTrustedAppRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_updateTrust)
                .admin(systemConfig_UpdateTrustedAppRequest.tokenWrapper().getAdmin())
                .operateDescription(systemConfig_UpdateTrustedAppRequest.getProcessName());
        return new SystemConfig_UpdateTrustedAppResponse();
    }

}
