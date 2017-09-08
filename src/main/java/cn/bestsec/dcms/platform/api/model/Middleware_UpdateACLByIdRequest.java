package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_UpdateACLByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer middlewareACLId;
    private String ip;
    private Integer enable;
    private Integer passwdEnable;
    private String passwd;
    
    public Middleware_UpdateACLByIdRequest() {
    }

    public Middleware_UpdateACLByIdRequest(String token, Integer middlewareACLId, String ip, Integer enable, Integer passwdEnable, String passwd) {
        this.token = token;
        this.middlewareACLId = middlewareACLId;
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
     * 接口访问控制策略ID
     */
    public Integer getMiddlewareACLId() {
        return middlewareACLId;
    }

    public void setMiddlewareACLId(Integer middlewareACLId) {
        this.middlewareACLId = middlewareACLId;
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
