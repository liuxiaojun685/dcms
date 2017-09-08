package cn.bestsec.dcms.platform.impl.backup;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Backup_DeleteByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Backup_DeleteByIdRequest;
import cn.bestsec.dcms.platform.api.model.Backup_DeleteByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.BackupHistoryDao;
import cn.bestsec.dcms.platform.entity.BackupHistory;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.LocationUtils;

/**
 * 删除备份
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日上午9:59:32
 */
@Component
public class BackupDeleteById implements Backup_DeleteByIdApi {
    @Autowired
    private BackupHistoryDao backupHistoryDao;

    @Override
    @Transactional
    public Backup_DeleteByIdResponse execute(Backup_DeleteByIdRequest backup_DeleteByIdRequest) throws ApiException {
        Backup_DeleteByIdResponse resp = new Backup_DeleteByIdResponse();
        BackupHistory backupHistory = backupHistoryDao.findById(backup_DeleteByIdRequest.getBackupId());
        if (backupHistory == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        StorageLocation location = backupHistory.getStorageLocation();
        if (location != null && LocationUtils.canDelete(location)) {
            try {
                LocationUtils.delete(location, backupHistory.getFileName());
            } catch (Throwable e) {
            }
        }
        backupHistoryDao.delete(backupHistory);

        AdminLogBuilder adminLogBuilder = backup_DeleteByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.backup_delete)
                .admin(backup_DeleteByIdRequest.tokenWrapper().getAdmin()).operateDescription(backupHistory.getFileName());

        return resp;
    }

}
