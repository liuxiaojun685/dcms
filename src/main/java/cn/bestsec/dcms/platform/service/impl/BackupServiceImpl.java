package cn.bestsec.dcms.platform.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BackupHistoryConsts;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.dao.AdminLogDao;
import cn.bestsec.dcms.platform.dao.BackupHistoryDao;
import cn.bestsec.dcms.platform.entity.BackupHistory;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.service.BackupService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.DBBackupUtils;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月17日 下午6:05:59
 */
@Service
public class BackupServiceImpl implements BackupService {
    static Logger logger = LoggerFactory.getLogger(BackupServiceImpl.class);

    @Autowired
    private AdminLogDao adminLogDao;
    @Autowired
    private BackupHistoryDao backupHistoryDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public boolean backup(int createFrom, String description, Date date) throws ApiException {
        BackupHistory backupHistory = new BackupHistory();
        backupHistory.setCreateFrom(createFrom);
        backupHistory.setCreateTime(System.currentTimeMillis());
        backupHistory.setDescription(description);
        // 得到系统配置的备份路径
        StorageLocation storageLocation = systemConfigService.getBackupLocation();
        backupHistory.setStorageLocation(storageLocation);

        String dateString = new SimpleDateFormat("yyyyMMdd-HHmmss").format(date);
        String backupName = "backup-" + dateString + ".sql";

        backupHistory.setFileName(backupName);
        String fullName = SystemProperties.getInstance().getCacheDir() + File.separator + backupName;
        File backupFile = new File(fullName);
        if (!DBBackupUtils.backup(backupFile)) {
            throw new ApiException(ErrorCode.backupFailed);
        }
        backupHistory.setFileSize(backupFile.length());
        backupHistory.setFileMd5(Md5Utils.getMd5ByFile(backupFile));

        // 上传远程
        if (LocationUtils.canUpload(storageLocation)) {
            if (!LocationUtils.upload(storageLocation, backupFile.getPath(), backupName)) {
                logger.error("备份文件存储失败，请检查配置");
                throw new ApiException(ErrorCode.fileSaveFailed);
            }
        }

        backupFile.delete();
        backupHistoryDao.save(backupHistory);
        if (createFrom == BackupHistoryConsts.CreateFrom.auto.getCode()) {
            AdminLogBuilder adminLogBuilder = new AdminLogBuilder();
            adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.backup_create)
                    .operateDescription(backupName).operateResult(ResultType.success.getCode(), null).ip("127.0.0.1");
            adminLogDao.save(adminLogBuilder.build());
        }
        return true;
    }

}
