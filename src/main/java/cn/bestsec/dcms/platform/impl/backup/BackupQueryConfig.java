package cn.bestsec.dcms.platform.impl.backup;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Backup_QueryConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Backup_QueryConfigRequest;
import cn.bestsec.dcms.platform.api.model.Backup_QueryConfigResponse;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 查询备份配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 上午11:40:22
 */
@Component
public class BackupQueryConfig implements Backup_QueryConfigApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Transactional
    @Override
    public Backup_QueryConfigResponse execute(Backup_QueryConfigRequest backup_QueryConfigRequest) throws ApiException {
        Backup_QueryConfigResponse resp = new Backup_QueryConfigResponse();
        // 获取配置信息
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        resp.setAutoBackupEnable(systemConfig.getAutoBackupEnable());
        // 获取文件配置位置信息
        StorageLocation location = systemConfig.getStorageLocationByFkBackupLocationId();
        LocationInfo backupLocation = new LocationInfo();
        if (location != null) {
            backupLocation.setAccount(location.getAccount());
            backupLocation.setDomainName(location.getDomainName());
            backupLocation.setIp(location.getIp());
            backupLocation.setPasswd(StringEncrypUtil.decrypt(location.getPasswd()));
            backupLocation.setPath(location.getFilePath());
            backupLocation.setPort(location.getPort());
            backupLocation.setProtocol(location.getProtocol());
        }
        resp.setBackupLocation(backupLocation);
        return resp;
    }

}
