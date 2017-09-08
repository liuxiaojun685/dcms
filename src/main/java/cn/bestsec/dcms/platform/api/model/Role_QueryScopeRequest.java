package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_QueryScopeRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleId;
    
    public Role_QueryScopeRequest() {
    }

    public Role_QueryScopeRequest(String token, Integer roleId) {
        this.token = token;
        this.roleId = roleId;
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
     * 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
