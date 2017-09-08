package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_LoginRequest extends CommonRequestFieldsSupport {
    private String account;
    private String passwd;
    private String keyCode;
    
    public Admin_LoginRequest() {
    }

    public Admin_LoginRequest(String account, String passwd, String keyCode) {
        this.account = account;
        this.passwd = passwd;
        this.keyCode = keyCode;
    }

    /**
     * 登录名
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 验证码
     */
    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
