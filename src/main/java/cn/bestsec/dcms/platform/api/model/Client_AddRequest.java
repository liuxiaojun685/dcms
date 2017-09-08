package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_AddRequest extends CommonRequestFieldsSupport {
    private String token;
    private String osType;
    private Integer versionType;
    private String versionName;
    private Integer versionCode;
    private String ip;
    private String mac;
    private Integer level;
    private String pcName;
    
    public Client_AddRequest() {
    }

    public Client_AddRequest(String token, String osType, Integer versionType, String versionName, Integer versionCode, String ip, String mac, Integer level, String pcName) {
        this.token = token;
        this.osType = osType;
        this.versionType = versionType;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.ip = ip;
        this.mac = mac;
        this.level = level;
        this.pcName = pcName;
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
     * 终端操作系统 windows linux kylin
     */
    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * 终端版本类型 1网络版 2单机版
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
     * 终端密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 机器名
     */
    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }
}
