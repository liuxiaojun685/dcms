package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_DownloadNewestVersionRequest extends CommonRequestFieldsSupport {
    private String token;
    private String osType;
    private Integer versionType;
    
    public Client_DownloadNewestVersionRequest() {
    }

    public Client_DownloadNewestVersionRequest(String token, String osType, Integer versionType) {
        this.token = token;
        this.osType = osType;
        this.versionType = versionType;
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
}
