package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_LogoutRequest extends CommonRequestFieldsSupport {
    private String token;
    
    public User_LogoutRequest() {
    }

    public User_LogoutRequest(String token) {
        this.token = token;
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
}
