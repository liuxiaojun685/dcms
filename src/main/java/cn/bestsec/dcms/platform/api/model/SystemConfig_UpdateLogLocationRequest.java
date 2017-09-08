package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateLogLocationRequest extends CommonRequestFieldsSupport {
    private String token;
    private LocationInfo logLocation;
    
    public SystemConfig_UpdateLogLocationRequest() {
    }

    public SystemConfig_UpdateLogLocationRequest(String token, LocationInfo logLocation) {
        this.token = token;
        this.logLocation = logLocation;
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
     * 
     */
    public LocationInfo getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(LocationInfo logLocation) {
        this.logLocation = logLocation;
    }
}
