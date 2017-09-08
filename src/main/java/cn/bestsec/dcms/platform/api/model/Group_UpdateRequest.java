package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Group_UpdateRequest extends CommonRequestFieldsSupport {
    private String token;
    private String gid;
    private String name;
    private String description;
    private String did;
    
    public Group_UpdateRequest() {
    }

    public Group_UpdateRequest(String token, String gid, String name, String description, String did) {
        this.token = token;
        this.gid = gid;
        this.name = name;
        this.description = description;
        this.did = did;
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
     * 用户组ID
     */
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    /**
     * 用户组名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 用户组描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 所属部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
