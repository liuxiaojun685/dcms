package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryLocationListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private LocationInfo fileLocation0;
    private LocationInfo fileLocation1;
    private LocationInfo fileLocation2;
    private LocationInfo fileLocation3;
    private LocationInfo fileLocation4;
    private LocationInfo logLocation;
    private LocationInfo patchLocation;
    private LocationInfo backupLocation;
    
    public SystemConfig_QueryLocationListResponse() {
    }

    public SystemConfig_QueryLocationListResponse(LocationInfo fileLocation0, LocationInfo fileLocation1, LocationInfo fileLocation2, LocationInfo fileLocation3, LocationInfo fileLocation4, LocationInfo logLocation, LocationInfo patchLocation, LocationInfo backupLocation) {
        this.fileLocation0 = fileLocation0;
        this.fileLocation1 = fileLocation1;
        this.fileLocation2 = fileLocation2;
        this.fileLocation3 = fileLocation3;
        this.fileLocation4 = fileLocation4;
        this.logLocation = logLocation;
        this.patchLocation = patchLocation;
        this.backupLocation = backupLocation;
    }

    public SystemConfig_QueryLocationListResponse(Integer code, String msg, LocationInfo fileLocation0, LocationInfo fileLocation1, LocationInfo fileLocation2, LocationInfo fileLocation3, LocationInfo fileLocation4, LocationInfo logLocation, LocationInfo patchLocation, LocationInfo backupLocation) {
        this.code = code;
        this.msg = msg;
        this.fileLocation0 = fileLocation0;
        this.fileLocation1 = fileLocation1;
        this.fileLocation2 = fileLocation2;
        this.fileLocation3 = fileLocation3;
        this.fileLocation4 = fileLocation4;
        this.logLocation = logLocation;
        this.patchLocation = patchLocation;
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
     * 公开文件存储位置
     */
    public LocationInfo getFileLocation0() {
        return fileLocation0;
    }

    public void setFileLocation0(LocationInfo fileLocation0) {
        this.fileLocation0 = fileLocation0;
    }

    /**
     * 内部文件存储位置
     */
    public LocationInfo getFileLocation1() {
        return fileLocation1;
    }

    public void setFileLocation1(LocationInfo fileLocation1) {
        this.fileLocation1 = fileLocation1;
    }

    /**
     * 秘密文件存储位置
     */
    public LocationInfo getFileLocation2() {
        return fileLocation2;
    }

    public void setFileLocation2(LocationInfo fileLocation2) {
        this.fileLocation2 = fileLocation2;
    }

    /**
     * 机密文件存储位置
     */
    public LocationInfo getFileLocation3() {
        return fileLocation3;
    }

    public void setFileLocation3(LocationInfo fileLocation3) {
        this.fileLocation3 = fileLocation3;
    }

    /**
     * 绝密文件存储位置
     */
    public LocationInfo getFileLocation4() {
        return fileLocation4;
    }

    public void setFileLocation4(LocationInfo fileLocation4) {
        this.fileLocation4 = fileLocation4;
    }

    /**
     * 日志文件存储位置
     */
    public LocationInfo getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(LocationInfo logLocation) {
        this.logLocation = logLocation;
    }

    /**
     * 补丁文件存储位置
     */
    public LocationInfo getPatchLocation() {
        return patchLocation;
    }

    public void setPatchLocation(LocationInfo patchLocation) {
        this.patchLocation = patchLocation;
    }

    /**
     * 备份文件存储位置
     */
    public LocationInfo getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(LocationInfo backupLocation) {
        this.backupLocation = backupLocation;
    }
}
