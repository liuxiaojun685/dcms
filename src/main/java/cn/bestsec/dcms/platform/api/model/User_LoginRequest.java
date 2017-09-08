package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_LoginRequest extends CommonRequestFieldsSupport {
    private String account;
    private String passwd;
    private Integer clientLevel;
    private String ip;
    private String mac;
    private String pcName;
    private String osType;
    private Integer versionType;
    private String versionName;
    private Integer versionCode;
    
    public User_LoginRequest() {
    }

    public User_LoginRequest(String account, String passwd, Integer clientLevel, String ip, String mac, String pcName, String osType, Integer versionType, String versionName, Integer versionCode) {
        this.account = account;
        this.passwd = passwd;
        this.clientLevel = clientLevel;
        this.ip = ip;
        this.mac = mac;
        this.pcName = pcName;
        this.osType = osType;
        this.versionType = versionType;
        this.versionName = versionName;
        this.versionCode = versionCode;
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
     * 终端密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(Integer clientLevel) {
        this.clientLevel = clientLevel;
    }

    /**
     * 终端IP
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 终端MAC
     */
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 终端名称
     */
    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    /**
     * 终端操作系统 windows linux kylin
     */
    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * 终端版本类型
     */
    public Integer getVersionType() {
        return versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

    /**
     * 终端版本名
     */
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * 终端版本号
     */
    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
}
