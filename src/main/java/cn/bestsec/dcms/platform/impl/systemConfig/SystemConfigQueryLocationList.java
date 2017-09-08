package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryLocationListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLocationListRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLocationListResponse;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月27日 下午2:09:22
 */
@Component
public class SystemConfigQueryLocationList implements SystemConfig_QueryLocationListApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryLocationListResponse execute(
            SystemConfig_QueryLocationListRequest systemConfig_QueryLocationListRequest) throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        StorageLocation file0 = systemConfig.getStorageLocationByFkFileLevel1locationId();
        StorageLocation file1 = systemConfig.getStorageLocationByFkFileLevel2locationId();
        StorageLocation file2 = systemConfig.getStorageLocationByFkFileLevel3locationId();
        StorageLocation file3 = systemConfig.getStorageLocationByFkFileLevel4locationId();
        StorageLocation file4 = systemConfig.getStorageLocationByFkFileLevel5locationId();
        StorageLocation log = systemConfig.getStorageLocationByFkLogLocationId();
        StorageLocation patch = systemConfig.getStorageLocationByFkPatchLocationId();
        StorageLocation backup = systemConfig.getStorageLocationByFkBackupLocationId();
        return new SystemConfig_QueryLocationListResponse(buildLocationInfo(file0), buildLocationInfo(file1),
                buildLocationInfo(file2), buildLocationInfo(file3), buildLocationInfo(file4), buildLocationInfo(log),
                buildLocationInfo(patch), buildLocationInfo(backup));
    }

    private LocationInfo buildLocationInfo(StorageLocation storageLocation) {
        LocationInfo info = new LocationInfo();
        if (storageLocation != null) {
            info.setAccount(storageLocation.getAccount());
            info.setDomainName(storageLocation.getDomainName());
            info.setIp(storageLocation.getIp());
            info.setPasswd(StringEncrypUtil.decrypt(storageLocation.getPasswd()));
            info.setPath(storageLocation.getFilePath());
            info.setPort(storageLocation.getPort());
            info.setProtocol(storageLocation.getProtocol());
        }
        return info;
    }

}
