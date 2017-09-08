package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Backup_QueryConfigResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer autoBackupEnable;
    private LocationInfo backupLocation;
    
    public Backup_QueryConfigResponse() {
    }

    public Backup_QueryConfigResponse(Integer autoBackupEnable, LocationInfo backupLocation) {
        this.autoBackupEnable = autoBackupEnable;
        this.backupLocation = backupLocation;
    }

    public Backup_QueryConfigResponse(Integer code, String msg, Integer autoBackupEnable, LocationInfo backupLocation) {
        this.code = code;
        this.msg = msg;
        this.autoBackupEnable = autoBackupEnable;
        this.backupLocation = backupLocation;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
