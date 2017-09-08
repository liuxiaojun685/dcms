package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryValidPeriodRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileLevel;
    
    public SecurePolicy_QueryValidPeriodRequest() {
    }

    public SecurePolicy_QueryValidPeriodRequest(String token, Integer fileLevel) {
        this.token = token;
        this.fileLevel = fileLevel;
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
}
