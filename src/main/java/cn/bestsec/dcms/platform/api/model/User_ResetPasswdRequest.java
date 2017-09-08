package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_ResetPasswdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String uid;
    
    public User_ResetPasswdRequest() {
    }

    public User_ResetPasswdRequest(String token, String uid) {
        this.token = token;
        this.uid = uid;
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
     * 用户ID
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
