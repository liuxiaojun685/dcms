package cn.bestsec.dcms.platform.impl.backup;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Backup_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Backup_AddRequest;
import cn.bestsec.dcms.platform.api.model.Backup_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BackupHistoryConsts;
import cn.bestsec.dcms.platform.service.BackupService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 创建数据库新备份
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午2:41:08
 */
@Component
public class BackupAdd implements Backup_AddApi {
    @Autowired
    private BackupService backupServices;

    @Override
    @Transactional
    public Backup_AddResponse execute(Backup_AddRequest backup_AddRequest) throws ApiException {
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyyMMdd-HHmmss").format(date);
        String backupName = "backup-" + dateString + ".sql";

        AdminLogBuilder adminLogBuilder = backup_AddRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.backup_create)
                .admin(backup_AddRequest.tokenWrapper().getAdmin()).operateDescription(backupName);
        
        backupServices.backup(BackupHistoryConsts.CreateFrom.manual.getCode(), backup_AddRequest.getDescription(), date);

        Backup_AddResponse resp = new Backup_AddResponse();
        return resp;
    }

}
