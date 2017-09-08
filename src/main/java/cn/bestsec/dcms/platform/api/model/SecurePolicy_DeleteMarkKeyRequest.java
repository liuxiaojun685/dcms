package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_DeleteMarkKeyRequest extends CommonRequestFieldsSupport {
    private String token;
    private String keyId;
    
    public SecurePolicy_DeleteMarkKeyRequest() {
    }

    public SecurePolicy_DeleteMarkKeyRequest(String token, String keyId) {
        this.token = token;
        this.keyId = keyId;
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
}
