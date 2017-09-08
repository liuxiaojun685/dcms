package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_QueryListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    private String keyword;
    private Integer level;
    private String ip;
    private String mac;
    private String osType;
    private Integer versionType;
    private String versionName;
    private Integer versionCode;
    private Integer online;
    private Long lastLoginTimeStart;
    private Long lastLoginTimeEnd;
    
    public Client_QueryListRequest() {
    }

    public Client_QueryListRequest(String token, Integer pageNumber, Integer pageSize, String keyword, Integer level, String ip, String mac, String osType, Integer versionType, String versionName, Integer versionCode, Integer online, Long lastLoginTimeStart, Long lastLoginTimeEnd) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.level = level;
        this.ip = ip;
        this.mac = mac;
        this.osType = osType;
        this.versionType = versionType;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.online = online;
        this.lastLoginTimeStart = lastLoginTimeStart;
        this.lastLoginTimeEnd = lastLoginTimeEnd;
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
     * 页号
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 行数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
     * 在线状态 1在线 2离线
     */
    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    /**
     * 末次登陆起始时间
     */
    public Long getLastLoginTimeStart() {
        return lastLoginTimeStart;
    }

    public void setLastLoginTimeStart(Long lastLoginTimeStart) {
        this.lastLoginTimeStart = lastLoginTimeStart;
    }

    /**
     * 末次登陆截止时间
     */
    public Long getLastLoginTimeEnd() {
        return lastLoginTimeEnd;
    }

    public void setLastLoginTimeEnd(Long lastLoginTimeEnd) {
        this.lastLoginTimeEnd = lastLoginTimeEnd;
    }
}
