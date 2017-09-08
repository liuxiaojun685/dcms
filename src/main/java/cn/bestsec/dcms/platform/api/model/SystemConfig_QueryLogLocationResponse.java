package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryLogLocationResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String domainName;
    private String ip;
    private String port;
    private String path;
    private String protocol;
    private String account;
    private String passwd;
    
    public SystemConfig_QueryLogLocationResponse() {
    }

    public SystemConfig_QueryLogLocationResponse(String domainName, String ip, String port, String path, String protocol, String account, String passwd) {
        this.domainName = domainName;
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.protocol = protocol;
        this.account = account;
        this.passwd = passwd;
    }

    public SystemConfig_QueryLogLocationResponse(Integer code, String msg, String domainName, String ip, String port, String path, String protocol, String account, String passwd) {
        this.code = code;
        this.msg = msg;
        this.domainName = domainName;
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.protocol = protocol;
        this.account = account;
        this.passwd = passwd;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 域名
     */
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /**
     * IP
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 端口
     */
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 路径
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 协议
     */
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 账号
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
}
