package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Backup_RecoverApi {
    /**
     * 权限:系统管理员

     */
    Backup_RecoverResponse execute(Backup_RecoverRequest backup_RecoverRequest) throws ApiException;
}
