package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateFileLocationRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileLevel;
    private LocationInfo fileLocation;
    
    public SystemConfig_UpdateFileLocationRequest() {
    }

    public SystemConfig_UpdateFileLocationRequest(String token, Integer fileLevel, LocationInfo fileLocation) {
        this.token = token;
        this.fileLevel = fileLevel;
        this.fileLocation = fileLocation;
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
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 
     */
    public LocationInfo getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(LocationInfo fileLocation) {
        this.fileLocation = fileLocation;
    }
}
