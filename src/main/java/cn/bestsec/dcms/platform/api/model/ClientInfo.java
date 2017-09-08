package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ClientInfo {
    private String cid;
    private Integer level;
    private String ip;
    private String mac;
    private Integer online;
    private String osType;
    private Integer versionType;
    private String versionName;
    private Integer versionCode;
    private String pcName;
    private UserSimpleInfo firstLoginUser;
    private UserSimpleInfo lastLoginUser;
    private Long firstLoginTime;
    private Long lastLoginTime;
    private String description;
    private Integer state;
    
    public ClientInfo() {
    }

    public ClientInfo(String cid, Integer level, String ip, String mac, Integer online, String osType, Integer versionType, String versionName, Integer versionCode, String pcName, UserSimpleInfo firstLoginUser, UserSimpleInfo lastLoginUser, Long firstLoginTime, Long lastLoginTime, String description, Integer state) {
        this.cid = cid;
        this.level = level;
        this.ip = ip;
        this.mac = mac;
        this.online = online;
        this.osType = osType;
        this.versionType = versionType;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.pcName = pcName;
        this.firstLoginUser = firstLoginUser;
        this.lastLoginUser = lastLoginUser;
        this.firstLoginTime = firstLoginTime;
        this.lastLoginTime = lastLoginTime;
        this.description = description;
        this.state = state;
    }

    /**
     * 终端ID
     */
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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
     * 在线状态 1在线 2离线
     */
    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
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
     * 机器名
     */
    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    /**
     * 首次登陆用户
     */
    public UserSimpleInfo getFirstLoginUser() {
        return firstLoginUser;
    }

    public void setFirstLoginUser(UserSimpleInfo firstLoginUser) {
        this.firstLoginUser = firstLoginUser;
    }

    /**
     * 末次登陆用户
     */
    public UserSimpleInfo getLastLoginUser() {
        return lastLoginUser;
    }

    public void setLastLoginUser(UserSimpleInfo lastLoginUser) {
        this.lastLoginUser = lastLoginUser;
    }

    /**
     * 首次登陆时间
     */
    public Long getFirstLoginTime() {
        return firstLoginTime;
    }

    public void setFirstLoginTime(Long firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    /**
     * 末次登陆时间
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    /**
     * 终端状态 1已删除 4已锁定
     */
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
