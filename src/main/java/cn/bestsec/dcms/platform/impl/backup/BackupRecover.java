package cn.bestsec.dcms.platform.impl.backup;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Backup_RecoverApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Backup_RecoverRequest;
import cn.bestsec.dcms.platform.api.model.Backup_RecoverResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.BackupHistoryDao;
import cn.bestsec.dcms.platform.entity.BackupHistory;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 备份恢复
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 下午12:12:42
 */
@Component
public class BackupRecover implements Backup_RecoverApi {

    @Autowired
    private BackupHistoryDao backupHistoryDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Transactional
    @Override
    public Backup_RecoverResponse execute(Backup_RecoverRequest backup_RecoverRequest) throws ApiException {
        Backup_RecoverResponse resp = new Backup_RecoverResponse();
        Integer id = backup_RecoverRequest.getBackupId();
        BackupHistory backupHistory;
        if (id == null) {
            backupHistory = backupHistoryDao.findByOrderByCreateTimeDesc();
        } else {
            backupHistory = backupHistoryDao.findById(id);
        }
        if (backupHistory == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = backup_RecoverRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.backup_recover)
                .admin(backup_RecoverRequest.tokenWrapper().getAdmin()).operateDescription(backupHistory.getFileName());

        // 数据库备份的位置
        StorageLocation location = systemConfigService.getBackupLocation();
        String filePath = location.getFilePath();
        // TODO 后续还需完善
        // recover(backupFile);
        throw new ApiException(ErrorCode.unsupport);

        // return resp;
    }

}
