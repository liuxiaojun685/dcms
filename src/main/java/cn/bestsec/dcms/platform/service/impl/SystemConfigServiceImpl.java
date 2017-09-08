package cn.bestsec.dcms.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年5月26日 下午3:41:21
 */
@Service
@Transactional
public class SystemConfigServiceImpl implements SystemConfigService {
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    public SystemConfig getSystemConfig() throws ApiException {
        SystemConfig systemConfig = systemConfigDao.findAll().get(0);
        if (systemConfig == null) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }
        return systemConfig;
    }

    @Override
    public StorageLocation getBackupLocation() throws ApiException {
        StorageLocation storageLocation = getSystemConfig().getStorageLocationByFkBackupLocationId();
        if (storageLocation == null) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }
        return storageLocation;
    }

    @Override
    public StorageLocation getPatchLocation() throws ApiException {
        StorageLocation storageLocation = getSystemConfig().getStorageLocationByFkPatchLocationId();
        if (storageLocation == null) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }
        return storageLocation;
    }

    @Override
    public StorageLocation getLogLocation() throws ApiException {
        StorageLocation storageLocation = getSystemConfig().getStorageLocationByFkLogLocationId();
        if (storageLocation == null) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }
        return storageLocation;
    }

    @Override
    public StorageLocation getFileLocation(Integer level) throws ApiException {
        StorageLocation storageLocation = null;
        // 文件密级 0公开 1内部 2秘密 3机密 4绝密
        if (level.equals(FileConsts.Level.open.getCode())) {
            storageLocation = getSystemConfig().getStorageLocationByFkFileLevel1locationId();
        }
        if (level.equals(FileConsts.Level.inside.getCode())) {
            storageLocation = getSystemConfig().getStorageLocationByFkFileLevel2locationId();
        }
        if (level.equals(FileConsts.Level.secret.getCode())) {
            storageLocation = getSystemConfig().getStorageLocationByFkFileLevel3locationId();
        }
        if (level.equals(FileConsts.Level.confidential.getCode())) {
            storageLocation = getSystemConfig().getStorageLocationByFkFileLevel4locationId();
        }
        if (level.equals(FileConsts.Level.topSecret.getCode())) {
            storageLocation = getSystemConfig().getStorageLocationByFkFileLevel5locationId();
        }
        if (storageLocation == null) {
            throw new ApiException(ErrorCode.invalidArgument);
        }
        return storageLocation;
    }

}
