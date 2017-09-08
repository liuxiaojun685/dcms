package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_BackupMarkKeyRequest extends CommonRequestFieldsSupport {
    private String token;
    private String passwd;
    
    public SecurePolicy_BackupMarkKeyRequest() {
    }

    public SecurePolicy_BackupMarkKeyRequest(String token, String passwd) {
        this.token = token;
        this.passwd = passwd;
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
     * 加密密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
