package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_UpdateValidPeriodRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileLevel;
    private String validPeriod;
    
    public SecurePolicy_UpdateValidPeriodRequest() {
    }

    public SecurePolicy_UpdateValidPeriodRequest(String token, Integer fileLevel, String validPeriod) {
        this.token = token;
        this.fileLevel = fileLevel;
        this.validPeriod = validPeriod;
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
     * 格式yymmdd
     */
    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }
}
