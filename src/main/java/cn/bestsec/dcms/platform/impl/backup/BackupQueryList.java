package cn.bestsec.dcms.platform.impl.backup;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Backup_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.BackupInfo;
import cn.bestsec.dcms.platform.api.model.Backup_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Backup_QueryListResponse;
import cn.bestsec.dcms.platform.dao.BackupHistoryDao;
import cn.bestsec.dcms.platform.entity.BackupHistory;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.utils.LocationUtils;

/**
 * 备份历史列表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 上午11:25:07
 */
@Component
public class BackupQueryList implements Backup_QueryListApi {

    @Autowired
    private BackupHistoryDao backupHistoryDao;

    @Transactional
    @Override
    public Backup_QueryListResponse execute(Backup_QueryListRequest backup_QueryListRequest) throws ApiException {
        Backup_QueryListResponse resp = new Backup_QueryListResponse();
        // 设置分页参数，获取分页列表
        Pageable pageable = new PageRequest(backup_QueryListRequest.getPageNumber(),
                backup_QueryListRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<BackupHistory> page = backupHistoryDao.findAll(pageable);
        List<BackupHistory> BackupList = page.getContent();
        // 返回备份历史数据
        List<BackupInfo> backupList = new ArrayList<BackupInfo>();
        BackupInfo backupInfo = null;
        for (BackupHistory backupHistory : BackupList) {
            backupInfo = new BackupInfo();
            backupInfo.setBackupId(backupHistory.getId());
            backupInfo.setCreateFrom(backupHistory.getCreateFrom());
            backupInfo.setCreateTime(backupHistory.getCreateTime());
            backupInfo.setDescription(backupHistory.getDescription());
            // 备份位置
            StorageLocation Location = backupHistory.getStorageLocation();
            backupInfo.setLocation(LocationUtils.toUrl(Location));
            backupInfo.setMd5(backupHistory.getFileMd5());
            backupInfo.setName(backupHistory.getFileName());
            backupInfo.setSize(backupHistory.getFileSize());
            backupList.add(backupInfo);
        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setBackupList(backupList);

        return resp;
    }

}
