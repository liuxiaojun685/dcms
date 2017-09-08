package cn.bestsec.dcms.platform.impl.systemConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateOtherSecurityConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateOtherSecurityConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateOtherSecurityConfigResponse;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 修改日志归档保留时间配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午6:00:41
 */
@Component
public class SystemConfigUpdateOtherSecurityConfig implements SystemConfig_UpdateOtherSecurityConfigApi {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    public SystemConfig_UpdateOtherSecurityConfigResponse execute(SystemConfig_UpdateOtherSecurityConfigRequest request)
            throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        if (request.getPreclassifiedForce() != null) {
            systemConfig.setPreclassifiedForce(request.getPreclassifiedForce());
            systemConfigDao.save(systemConfig);
        }
        return new SystemConfig_UpdateOtherSecurityConfigResponse();
    }

}
