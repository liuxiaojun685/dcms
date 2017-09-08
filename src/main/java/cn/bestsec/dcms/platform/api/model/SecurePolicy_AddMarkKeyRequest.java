package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_AddMarkKeyRequest extends CommonRequestFieldsSupport {
    private String token;
    private String keyName;
    
    public SecurePolicy_AddMarkKeyRequest() {
    }

    public SecurePolicy_AddMarkKeyRequest(String token, String keyName) {
        this.token = token;
        this.keyName = keyName;
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
     * 密钥名称
     */
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
