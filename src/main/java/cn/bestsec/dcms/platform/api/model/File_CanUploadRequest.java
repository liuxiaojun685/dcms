package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_CanUploadRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer uploadType;
    private String fid;
    private Integer fileLevel;
    private Integer fileState;
    
    public File_CanUploadRequest() {
    }

    public File_CanUploadRequest(String token, Integer uploadType, String fid, Integer fileLevel, Integer fileState) {
        this.token = token;
        this.uploadType = uploadType;
        this.fid = fid;
        this.fileLevel = fileLevel;
        this.fileState = fileState;
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
     * 上传类型 2正式定密 3文件签发 4密级变更 5文件解密 6更新
     */
    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
    }

    /**
     * 文件ID
     */
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }
}
