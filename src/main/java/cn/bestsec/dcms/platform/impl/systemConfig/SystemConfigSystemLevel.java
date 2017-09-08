package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_SystemLevelApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SystemConfig_SystemLevelRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_SystemLevelResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 系统定级
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午5:33:46
 */
@Component
public class SystemConfigSystemLevel implements SystemConfig_SystemLevelApi {
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_SystemLevelResponse execute(SystemConfig_SystemLevelRequest systemConfig_SystemLevelRequest)
            throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        if (systemConfig_SystemLevelRequest.getLevel() != null) {
            if (systemConfig.getSystemTopLevel() != 0) {
                throw new ApiException(ErrorCode.operationNotPermitted);
            }
            systemConfig.setSystemTopLevel(systemConfig_SystemLevelRequest.getLevel());
            systemConfigDao.save(systemConfig);

            AdminLogBuilder adminLogBuilder = systemConfig_SystemLevelRequest.createAdminLogBuilder();
            adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_systemLevel)
                    .admin(systemConfig_SystemLevelRequest.tokenWrapper().getAdmin())
                    .operateDescription(systemConfig_SystemLevelRequest.getLevel());
        }
        SystemConfig_SystemLevelResponse resp = new SystemConfig_SystemLevelResponse();
        resp.setLevel(systemConfig.getSystemTopLevel());
        return resp;
    }

}
