package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateAdAuthConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer adAuthEnable;
    private LocationInfo adAuthLocation;
    private String adAuthDn;
    private Integer adAuthAutoSync;
    private String adAuthSyncRoot;
    
    public SystemConfig_UpdateAdAuthConfigRequest() {
    }

    public SystemConfig_UpdateAdAuthConfigRequest(String token, Integer adAuthEnable, LocationInfo adAuthLocation, String adAuthDn, Integer adAuthAutoSync, String adAuthSyncRoot) {
        this.token = token;
        this.adAuthEnable = adAuthEnable;
        this.adAuthLocation = adAuthLocation;
        this.adAuthDn = adAuthDn;
        this.adAuthAutoSync = adAuthAutoSync;
        this.adAuthSyncRoot = adAuthSyncRoot;
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
     * AD认证是否启用 1是 0否
     */
    public Integer getAdAuthEnable() {
        return adAuthEnable;
    }

    public void setAdAuthEnable(Integer adAuthEnable) {
        this.adAuthEnable = adAuthEnable;
    }

    /**
     * 认证服务器位置
     */
    public LocationInfo getAdAuthLocation() {
        return adAuthLocation;
    }

    public void setAdAuthLocation(LocationInfo adAuthLocation) {
        this.adAuthLocation = adAuthLocation;
    }

    /**
     * 连接账号全DN
     */
    public String getAdAuthDn() {
        return adAuthDn;
    }

    public void setAdAuthDn(String adAuthDn) {
        this.adAuthDn = adAuthDn;
    }

    /**
     * 是否启用AD自动同步 1是 0否
     */
    public Integer getAdAuthAutoSync() {
        return adAuthAutoSync;
    }

    public void setAdAuthAutoSync(Integer adAuthAutoSync) {
        this.adAuthAutoSync = adAuthAutoSync;
    }

    /**
     * 同步根DN
     */
    public String getAdAuthSyncRoot() {
        return adAuthSyncRoot;
    }

    public void setAdAuthSyncRoot(String adAuthSyncRoot) {
        this.adAuthSyncRoot = adAuthSyncRoot;
    }
}
