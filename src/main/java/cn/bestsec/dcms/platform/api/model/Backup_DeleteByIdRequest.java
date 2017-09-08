package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Backup_DeleteByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer backupId;
    
    public Backup_DeleteByIdRequest() {
    }

    public Backup_DeleteByIdRequest(String token, Integer backupId) {
        this.token = token;
        this.backupId = backupId;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 备份历史ID
     */
    public Integer getBackupId() {
        return backupId;
    }

    public void setBackupId(Integer backupId) {
        this.backupId = backupId;
    }
}
