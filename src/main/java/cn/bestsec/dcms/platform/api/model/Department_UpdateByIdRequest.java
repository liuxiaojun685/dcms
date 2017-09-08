package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Department_UpdateByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String did;
    private String name;
    private String description;
    private String parentDid;
    
    public Department_UpdateByIdRequest() {
    }

    public Department_UpdateByIdRequest(String token, String did, String name, String description, String parentDid) {
        this.token = token;
        this.did = did;
        this.name = name;
        this.description = description;
        this.parentDid = parentDid;
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
     * 部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    /**
     * 部门名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 部门描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * “”或者“did-root”表示从属根部门
     */
    public String getParentDid() {
        return parentDid;
    }

    public void setParentDid(String parentDid) {
        this.parentDid = parentDid;
    }
}
