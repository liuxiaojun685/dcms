package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_ChangeClientUninstallPasswdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String passwd;
    private String description;
    
    public Client_ChangeClientUninstallPasswdRequest() {
    }

    public Client_ChangeClientUninstallPasswdRequest(String token, String passwd, String description) {
        this.token = token;
        this.passwd = passwd;
        this.description = description;
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
     * 新卸载密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
