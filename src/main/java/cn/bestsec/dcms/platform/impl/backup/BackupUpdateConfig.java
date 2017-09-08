package cn.bestsec.dcms.platform.impl.backup;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Backup_UpdateConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Backup_UpdateConfigRequest;
import cn.bestsec.dcms.platform.api.model.Backup_UpdateConfigResponse;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.StorageLocationDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.service.TimerScheduleService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 更新备份配置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午5:44:32
 */
@Component
public class BackupUpdateConfig implements Backup_UpdateConfigApi {
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private StorageLocationDao storageLocationDao;
    @Autowired
    private TimerScheduleService timerScheduleService;

    @Override
    @Transactional
    public Backup_UpdateConfigResponse execute(Backup_UpdateConfigRequest backup_UpdateConfigRequest)
            throws ApiException {
        Backup_UpdateConfigResponse resp = new Backup_UpdateConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        if (backup_UpdateConfigRequest.getBackupLocation() != null) {
            LocationInfo location = backup_UpdateConfigRequest.getBackupLocation();
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
            systemConfig.setStorageLocationByFkBackupLocationId(storageLocation);
            systemConfigDao.save(systemConfig);
        }
        if (backup_UpdateConfigRequest.getAutoBackupEnable() != null) {
            if (!backup_UpdateConfigRequest.getAutoBackupEnable().equals(systemConfig.getAutoBackupEnable())) {
                systemConfig.setAutoBackupEnable(backup_UpdateConfigRequest.getAutoBackupEnable());
                systemConfigDao.save(systemConfig);
                timerScheduleService.scheduleTimer();
            }
        }

        AdminLogBuilder adminLogBuilder = backup_UpdateConfigRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.backup_config)
                .admin(backup_UpdateConfigRequest.tokenWrapper().getAdmin()).operateDescription();
        return resp;
    }

}
