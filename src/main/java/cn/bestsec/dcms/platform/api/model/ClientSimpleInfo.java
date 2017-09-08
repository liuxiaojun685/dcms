package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ClientSimpleInfo {
    private String cid;
    private Integer level;
    private String ip;
    private String mac;
    private Integer online;
    
    public ClientSimpleInfo() {
    }

    public ClientSimpleInfo(String cid, Integer level, String ip, String mac, Integer online) {
        this.cid = cid;
        this.level = level;
        this.ip = ip;
        this.mac = mac;
        this.online = online;
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
}
