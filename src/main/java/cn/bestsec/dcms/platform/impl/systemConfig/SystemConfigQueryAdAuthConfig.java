package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryAdAuthConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryAdAuthConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryAdAuthConfigResponse;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 获取AD认证配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午4:43:18
 */
@Component
public class SystemConfigQueryAdAuthConfig implements SystemConfig_QueryAdAuthConfigApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryAdAuthConfigResponse execute(
            SystemConfig_QueryAdAuthConfigRequest systemConfig_QueryAdAuthConfigRequest) throws ApiException {
        SystemConfig_QueryAdAuthConfigResponse resp = new SystemConfig_QueryAdAuthConfigResponse();
        // 获取配置信息
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        resp.setAdAuthAutoSync(systemConfig.getAdAuthAutoSync());
        resp.setAdAuthDn(systemConfig.getAdAuthDn());
        resp.setAdAuthEnable(systemConfig.getAdAuthEnable());
        // 获取文件位置信息
        StorageLocation storageLocation = systemConfig.getStorageLocationByFkAdAuthLocationId();
        LocationInfo adAuthLocation = new LocationInfo();
        if (storageLocation != null) {
            adAuthLocation.setAccount(storageLocation.getAccount());
            adAuthLocation.setDomainName(storageLocation.getDomainName());
            adAuthLocation.setIp(storageLocation.getIp());
            adAuthLocation.setPasswd(StringEncrypUtil.decrypt(storageLocation.getPasswd()));
            adAuthLocation.setPath(storageLocation.getFilePath());
            adAuthLocation.setPort(storageLocation.getPort());
            adAuthLocation.setProtocol(storageLocation.getProtocol());
        }
        resp.setAdAuthLocation(adAuthLocation);
        resp.setAdAuthSyncRoot(systemConfig.getAdAuthSyncRoot());
        return resp;
    }

}
