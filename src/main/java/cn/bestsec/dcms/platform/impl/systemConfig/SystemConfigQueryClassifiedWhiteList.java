package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryClassifiedWhiteListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryClassifiedWhiteListRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryClassifiedWhiteListResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

@Component
public class SystemConfigQueryClassifiedWhiteList implements SystemConfig_QueryClassifiedWhiteListApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryClassifiedWhiteListResponse execute(SystemConfig_QueryClassifiedWhiteListRequest request)
            throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        return new SystemConfig_QueryClassifiedWhiteListResponse(systemConfig.getClassifiedWhiteList());
    }

}
