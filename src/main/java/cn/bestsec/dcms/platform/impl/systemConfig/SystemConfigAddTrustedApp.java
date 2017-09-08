package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_AddTrustedAppApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_AddTrustedAppRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_AddTrustedAppResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.TrustedApplicationDao;
import cn.bestsec.dcms.platform.entity.TrustedApplication;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 添加可信应用程序
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午5:29:59
 */
@Component
public class SystemConfigAddTrustedApp implements SystemConfig_AddTrustedAppApi {

    @Autowired
    private TrustedApplicationDao trustedApplicationDao;

    @Override
    @Transactional
    public SystemConfig_AddTrustedAppResponse execute(
            SystemConfig_AddTrustedAppRequest systemConfig_AddTrustedAppRequest) throws ApiException {
        TrustedApplication trustedApplication = new TrustedApplication();
        trustedApplication.setDescription(systemConfig_AddTrustedAppRequest.getDescription());
        trustedApplication.setProcessName(systemConfig_AddTrustedAppRequest.getProcessName());
        trustedApplication.setExtensionName(systemConfig_AddTrustedAppRequest.getExtensionName());
        trustedApplicationDao.save(trustedApplication);

        AdminLogBuilder adminLogBuilder = systemConfig_AddTrustedAppRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_addTrust)
                .admin(systemConfig_AddTrustedAppRequest.tokenWrapper().getAdmin())
                .operateDescription(systemConfig_AddTrustedAppRequest.getProcessName());

        SystemConfig_AddTrustedAppResponse resp = new SystemConfig_AddTrustedAppResponse();
        resp.setTrustedAppId(trustedApplication.getId());
        return resp;
    }

}
