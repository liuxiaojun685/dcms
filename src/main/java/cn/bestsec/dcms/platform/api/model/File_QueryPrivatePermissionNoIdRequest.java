package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryPrivatePermissionNoIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileState;
    private Integer fileLevel;
    private Integer fileIsMyCreate;
    
    public File_QueryPrivatePermissionNoIdRequest() {
    }

    public File_QueryPrivatePermissionNoIdRequest(String token, Integer fileState, Integer fileLevel, Integer fileIsMyCreate) {
        this.token = token;
        this.fileState = fileState;
        this.fileLevel = fileLevel;
        this.fileIsMyCreate = fileIsMyCreate;
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
     * 定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
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
     * 是否是自己创建的文件 1是 0否
     */
    public Integer getFileIsMyCreate() {
        return fileIsMyCreate;
    }

    public void setFileIsMyCreate(Integer fileIsMyCreate) {
        this.fileIsMyCreate = fileIsMyCreate;
    }
}
