package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_UpdateRequest extends CommonRequestFieldsSupport {
    private String token;
    private String name;
    private String passwd;
    private String oldPasswd;
    
    public Admin_UpdateRequest() {
    }

    public Admin_UpdateRequest(String token, String name, String passwd, String oldPasswd) {
        this.token = token;
        this.name = name;
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
     * 管理员名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 登陆密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 旧登陆密码
     */
    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }
}
