package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_AddACLRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer middlewareId;
    private String ip;
    private Integer enable;
    private Integer passwdEnable;
    private String passwd;
    
    public Middleware_AddACLRequest() {
    }

    public Middleware_AddACLRequest(String token, Integer middlewareId, String ip, Integer enable, Integer passwdEnable, String passwd) {
        this.token = token;
        this.middlewareId = middlewareId;
        this.ip = ip;
        this.enable = enable;
        this.passwdEnable = passwdEnable;
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
     * 中间件ID
     */
    public Integer getMiddlewareId() {
        return middlewareId;
    }

    public void setMiddlewareId(Integer middlewareId) {
        this.middlewareId = middlewareId;
    }

    /**
     * 中间件应用IP
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 中间件应用是否允许接入
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 中间件应用是否启用授权口令
     */
    public Integer getPasswdEnable() {
        return passwdEnable;
    }

    public void setPasswdEnable(Integer passwdEnable) {
        this.passwdEnable = passwdEnable;
    }

    /**
     * 中间件应用授权口令
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
