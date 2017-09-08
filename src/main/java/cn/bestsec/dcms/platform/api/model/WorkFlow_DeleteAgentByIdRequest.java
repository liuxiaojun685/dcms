package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_DeleteAgentByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleId;
    
    public WorkFlow_DeleteAgentByIdRequest() {
    }

    public WorkFlow_DeleteAgentByIdRequest(String token, Integer roleId) {
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
     * 代理人角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
