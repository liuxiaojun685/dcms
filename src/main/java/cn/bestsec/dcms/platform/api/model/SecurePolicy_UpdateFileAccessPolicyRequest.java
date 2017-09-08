package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_UpdateFileAccessPolicyRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileLevel;
    private Integer userLevel;
    private Integer enable;
    
    public SecurePolicy_UpdateFileAccessPolicyRequest() {
    }

    public SecurePolicy_UpdateFileAccessPolicyRequest(String token, Integer fileLevel, Integer userLevel, Integer enable) {
        this.token = token;
        this.fileLevel = fileLevel;
        this.userLevel = userLevel;
        this.enable = enable;
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
     * 用户密级 1一般 2重要 3核心
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * 是否允许操作 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
