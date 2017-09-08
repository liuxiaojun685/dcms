package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateAdAuthConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateAdAuthConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateAdAuthConfigResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.StorageLocationDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改AD认证配置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月29日下午5:17:05
 */
@Component
public class SystemConfigUpdateAdAuthConfig implements SystemConfig_UpdateAdAuthConfigApi {
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private StorageLocationDao storageLocationDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateAdAuthConfigResponse execute(
            SystemConfig_UpdateAdAuthConfigRequest systemConfig_UpdateAdAuthConfigRequest) throws ApiException {
        SystemConfig_UpdateAdAuthConfigResponse resp = new SystemConfig_UpdateAdAuthConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        if (systemConfig_UpdateAdAuthConfigRequest.getAdAuthEnable() != null) {
            systemConfig.setAdAuthEnable(systemConfig_UpdateAdAuthConfigRequest.getAdAuthEnable());
        }

        if (systemConfig_UpdateAdAuthConfigRequest.getAdAuthLocation() != null) {
            LocationInfo location = systemConfig_UpdateAdAuthConfigRequest.getAdAuthLocation();
            StorageLocation storageLocation = new StorageLocation();
            List<StorageLocation> sls = storageLocationDao.findByDomainNameAndIpAndPortAndFilePathAndProtocol(
                    location.getDomainName(), location.getIp(), location.getPort(), location.getPath(),
                    location.getProtocol());
            if (!sls.isEmpty()) {
                storageLocation = sls.get(sls.size() - 1);
            }
            storageLocation.setDomainName(location.getDomainName());
            storageLocation.setIp(location.getIp());
            storageLocation.setPort(location.getPort());
            storageLocation.setFilePath(location.getPath());
            storageLocation.setProtocol(location.getProtocol());
            storageLocation.setAccount(location.getAccount());
            storageLocation.setPasswd(StringEncrypUtil.encrypt(location.getPasswd()));
            storageLocationDao.save(storageLocation);

            systemConfig.setStorageLocationByFkAdAuthLocationId(storageLocation);
        }

        if (systemConfig_UpdateAdAuthConfigRequest.getAdAuthDn() != null) {
            systemConfig.setAdAuthDn(systemConfig_UpdateAdAuthConfigRequest.getAdAuthDn());
        }

        if (systemConfig_UpdateAdAuthConfigRequest.getAdAuthAutoSync() != null) {
            systemConfig.setAdAuthAutoSync(systemConfig_UpdateAdAuthConfigRequest.getAdAuthAutoSync());
        }

        if (systemConfig_UpdateAdAuthConfigRequest.getAdAuthSyncRoot() != null) {
            systemConfig.setAdAuthSyncRoot(systemConfig_UpdateAdAuthConfigRequest.getAdAuthSyncRoot());
        }

        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateAdAuthConfigRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_adAuth)
                .admin(systemConfig_UpdateAdAuthConfigRequest.tokenWrapper().getAdmin()).operateDescription();
        return resp;
    }

}
