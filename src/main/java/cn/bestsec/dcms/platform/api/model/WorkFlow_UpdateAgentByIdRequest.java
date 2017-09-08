package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_UpdateAgentByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleId;
    private Long agentInvalidTime;
    
    public WorkFlow_UpdateAgentByIdRequest() {
    }

    public WorkFlow_UpdateAgentByIdRequest(String token, Integer roleId, Long agentInvalidTime) {
        this.token = token;
        this.roleId = roleId;
        this.agentInvalidTime = agentInvalidTime;
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

    /**
     * 委托到期时间
     */
    public Long getAgentInvalidTime() {
        return agentInvalidTime;
    }

    public void setAgentInvalidTime(Long agentInvalidTime) {
        this.agentInvalidTime = agentInvalidTime;
    }
}
