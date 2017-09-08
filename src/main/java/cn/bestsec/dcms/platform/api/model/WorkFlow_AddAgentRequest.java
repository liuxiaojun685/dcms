package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_AddAgentRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleId;
    private String approveAgentUid;
    private Long agentInvalidTime;
    
    public WorkFlow_AddAgentRequest() {
    }

    public WorkFlow_AddAgentRequest(String token, Integer roleId, String approveAgentUid, Long agentInvalidTime) {
        this.token = token;
        this.roleId = roleId;
        this.approveAgentUid = approveAgentUid;
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
     * 审批代理人uid
     */
    public String getApproveAgentUid() {
        return approveAgentUid;
    }

    public void setApproveAgentUid(String approveAgentUid) {
        this.approveAgentUid = approveAgentUid;
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
