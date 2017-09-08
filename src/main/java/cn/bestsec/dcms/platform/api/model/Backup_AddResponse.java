package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Backup_AddResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer backupId;
    
    public Backup_AddResponse() {
    }

    public Backup_AddResponse(Integer backupId) {
        this.backupId = backupId;
    }

    public Backup_AddResponse(Integer code, String msg, Integer backupId) {
        this.code = code;
        this.msg = msg;
        this.backupId = backupId;
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
     * 备份历史ID
     */
    public Integer getBackupId() {
        return backupId;
    }

    public void setBackupId(Integer backupId) {
        this.backupId = backupId;
    }
}
