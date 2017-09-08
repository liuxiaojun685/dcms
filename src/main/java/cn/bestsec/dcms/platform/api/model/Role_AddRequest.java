package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_AddRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleType;
    private String uid;
    
    public Role_AddRequest() {
    }

    public Role_AddRequest(String token, Integer roleType, String uid) {
        this.token = token;
        this.roleType = roleType;
        this.uid = uid;
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
     * 角色类型 2定密责任人 3文件签发人 7签入人(特权) 8签出人(特权))
     */
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    /**
     * 用户ID
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
