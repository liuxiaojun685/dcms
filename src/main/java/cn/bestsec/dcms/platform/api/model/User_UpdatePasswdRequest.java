package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_UpdatePasswdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String passwd;
    private String oldPasswd;
    
    public User_UpdatePasswdRequest() {
    }

    public User_UpdatePasswdRequest(String token, String passwd, String oldPasswd) {
        this.token = token;
        this.passwd = passwd;
        this.oldPasswd = oldPasswd;
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
     * 新密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 旧密码
     */
    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }
}
