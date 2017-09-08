package cn.bestsec.dcms.platform.impl.systemConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryOtherSecurityConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryOtherSecurityConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryOtherSecurityConfigResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 查询日志归档保留时间配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午6:00:41
 */
@Component
public class SystemConfigQueryOtherSecurityConfig implements SystemConfig_QueryOtherSecurityConfigApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public SystemConfig_QueryOtherSecurityConfigResponse execute(SystemConfig_QueryOtherSecurityConfigRequest request)
            throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        Integer preclassifiedForce = systemConfig.getPreclassifiedForce();
        return new SystemConfig_QueryOtherSecurityConfigResponse(preclassifiedForce);
    }

}
