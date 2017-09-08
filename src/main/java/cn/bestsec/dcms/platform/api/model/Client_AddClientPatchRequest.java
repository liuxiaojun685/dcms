package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_AddClientPatchRequest extends CommonRequestFieldsSupport {
    private String token;
    private String osType;
    private Integer versionType;
    private String versionName;
    private Integer versionCode;
    private String name;
    private String description;
    
    public Client_AddClientPatchRequest() {
    }

    public Client_AddClientPatchRequest(String token, String osType, Integer versionType, String versionName, Integer versionCode, String name, String description) {
        this.token = token;
        this.osType = osType;
        this.versionType = versionType;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.name = name;
        this.description = description;
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
     * 补丁名(文件名带后缀)
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
