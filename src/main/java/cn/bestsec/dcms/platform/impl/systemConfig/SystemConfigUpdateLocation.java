package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateLocationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLocationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLocationResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.StorageLocationDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月29日 上午9:39:15
 */
@Component
public class SystemConfigUpdateLocation implements SystemConfig_UpdateLocationApi {
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private StorageLocationDao storageLocationDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateLocationResponse execute(
            SystemConfig_UpdateLocationRequest systemConfig_UpdateLocationRequest) throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        if (systemConfig_UpdateLocationRequest.getFileLocation0() != null) {
            systemConfig.setStorageLocationByFkFileLevel1locationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getFileLocation0()));
        }
        if (systemConfig_UpdateLocationRequest.getFileLocation1() != null) {
            systemConfig.setStorageLocationByFkFileLevel2locationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getFileLocation1()));
        }
        if (systemConfig_UpdateLocationRequest.getFileLocation2() != null) {
            systemConfig.setStorageLocationByFkFileLevel3locationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getFileLocation2()));
        }
        if (systemConfig_UpdateLocationRequest.getFileLocation3() != null) {
            systemConfig.setStorageLocationByFkFileLevel4locationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getFileLocation3()));
        }
        if (systemConfig_UpdateLocationRequest.getFileLocation4() != null) {
            systemConfig.setStorageLocationByFkFileLevel5locationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getFileLocation4()));
        }
        if (systemConfig_UpdateLocationRequest.getLogLocation() != null) {
            systemConfig.setStorageLocationByFkLogLocationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getLogLocation()));
        }
        if (systemConfig_UpdateLocationRequest.getPatchLocation() != null) {
            systemConfig.setStorageLocationByFkPatchLocationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getPatchLocation()));
        }
        if (systemConfig_UpdateLocationRequest.getBackupLocation() != null) {
            systemConfig.setStorageLocationByFkBackupLocationId(
                    saveLocation(systemConfig_UpdateLocationRequest.getBackupLocation()));
        }

        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateLocationRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_Location)
                .admin(systemConfig_UpdateLocationRequest.tokenWrapper().getAdmin()).operateDescription();

        return new SystemConfig_UpdateLocationResponse();
    }

    private StorageLocation saveLocation(LocationInfo location) {
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
        return storageLocationDao.save(storageLocation);
    }

}
