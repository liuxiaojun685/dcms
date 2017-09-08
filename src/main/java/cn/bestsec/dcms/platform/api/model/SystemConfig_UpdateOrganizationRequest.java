package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateOrganizationRequest extends CommonRequestFieldsSupport {
    private String token;
    private String organizationCode;
    private String organizationName;
    private String organizationDescription;
    
    public SystemConfig_UpdateOrganizationRequest() {
    }

    public SystemConfig_UpdateOrganizationRequest(String token, String organizationCode, String organizationName, String organizationDescription) {
        this.token = token;
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
        this.organizationDescription = organizationDescription;
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
     * 组织机构代码
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * 组织机构名称
     */
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * 组织机构描述
     */
    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }
}
