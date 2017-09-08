package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateFileLocationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateFileLocationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateFileLocationResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.SystemConsts;
import cn.bestsec.dcms.platform.dao.StorageLocationDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改文件上传存储位置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午5:12:30
 */
@Component
public class SystemConfigUpdateFileLocation implements SystemConfig_UpdateFileLocationApi {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private StorageLocationDao storageLocationDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateFileLocationResponse execute(
            SystemConfig_UpdateFileLocationRequest systemConfig_UpdateFileLocationRequest) throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        LocationInfo location = systemConfig_UpdateFileLocationRequest.getFileLocation();
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

        if (systemConfig_UpdateFileLocationRequest.getFileLevel() == SystemConsts.Level.pub.getCode()) {
            systemConfig.setStorageLocationByFkFileLevel1locationId(storageLocation);
        } else if (systemConfig_UpdateFileLocationRequest.getFileLevel() == SystemConsts.Level.inside.getCode()) {
            systemConfig.setStorageLocationByFkFileLevel2locationId(storageLocation);
        } else if (systemConfig_UpdateFileLocationRequest.getFileLevel() == SystemConsts.Level.secret.getCode()) {
            systemConfig.setStorageLocationByFkFileLevel3locationId(storageLocation);
        } else if (systemConfig_UpdateFileLocationRequest.getFileLevel() == SystemConsts.Level.confidential.getCode()) {
            systemConfig.setStorageLocationByFkFileLevel4locationId(storageLocation);
        } else if (systemConfig_UpdateFileLocationRequest.getFileLevel() == SystemConsts.Level.topSecret.getCode()) {
            systemConfig.setStorageLocationByFkFileLevel5locationId(storageLocation);
        } else {
            throw new ApiException(ErrorCode.invalidArgument);
        }
        storageLocationDao.save(storageLocation);
        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateFileLocationRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_fileLocation)
                .admin(systemConfig_UpdateFileLocationRequest.tokenWrapper().getAdmin()).operateDescription(
                        FileConsts.Level.parse(systemConfig_UpdateFileLocationRequest.getFileLevel()).getDescription());
        return new SystemConfig_UpdateFileLocationResponse();
    }

}
