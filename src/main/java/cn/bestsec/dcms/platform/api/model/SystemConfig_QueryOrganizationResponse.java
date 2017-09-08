package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryOrganizationResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String organizationCode;
    private String organizationName;
    private String organizationDescription;
    
    public SystemConfig_QueryOrganizationResponse() {
    }

    public SystemConfig_QueryOrganizationResponse(String organizationCode, String organizationName, String organizationDescription) {
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
        this.organizationDescription = organizationDescription;
    }

    public SystemConfig_QueryOrganizationResponse(Integer code, String msg, String organizationCode, String organizationName, String organizationDescription) {
        this.code = code;
        this.msg = msg;
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
        this.organizationDescription = organizationDescription;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
