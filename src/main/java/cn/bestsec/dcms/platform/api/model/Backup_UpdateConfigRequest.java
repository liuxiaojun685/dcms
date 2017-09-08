package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Backup_UpdateConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer autoBackupEnable;
    private LocationInfo backupLocation;
    
    public Backup_UpdateConfigRequest() {
    }

    public Backup_UpdateConfigRequest(String token, Integer autoBackupEnable, LocationInfo backupLocation) {
        this.token = token;
        this.autoBackupEnable = autoBackupEnable;
        this.backupLocation = backupLocation;
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
     * 是否自动备份 1是 0否
     */
    public Integer getAutoBackupEnable() {
        return autoBackupEnable;
    }

    public void setAutoBackupEnable(Integer autoBackupEnable) {
        this.autoBackupEnable = autoBackupEnable;
    }

    /**
     * 备份位置信息
     */
    public LocationInfo getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(LocationInfo backupLocation) {
        this.backupLocation = backupLocation;
    }
}
