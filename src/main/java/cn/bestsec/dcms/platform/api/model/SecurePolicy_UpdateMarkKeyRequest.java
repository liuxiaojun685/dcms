package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_UpdateMarkKeyRequest extends CommonRequestFieldsSupport {
    private String token;
    private String keyId;
    private String keyName;
    private Integer enable;
    
    public SecurePolicy_UpdateMarkKeyRequest() {
    }

    public SecurePolicy_UpdateMarkKeyRequest(String token, String keyId, String keyName, Integer enable) {
        this.token = token;
        this.keyId = keyId;
        this.keyName = keyName;
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
     * 密钥ID
     */
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    /**
     * 密钥名称
     */
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * 密钥是否启用 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
