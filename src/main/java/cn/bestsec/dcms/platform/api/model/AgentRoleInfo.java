package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class AgentRoleInfo {
    private Integer roleId;
    private Integer roleType;
    private Integer fileLevel;
    private UserSimpleInfo approveAgent;
    private Long agentInvalidTime;
    
    public AgentRoleInfo() {
    }

    public AgentRoleInfo(Integer roleId, Integer roleType, Integer fileLevel, UserSimpleInfo approveAgent, Long agentInvalidTime) {
        this.roleId = roleId;
        this.roleType = roleType;
        this.fileLevel = fileLevel;
        this.approveAgent = approveAgent;
        this.agentInvalidTime = agentInvalidTime;
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
     * 角色类型 2定密责任人 3文件签发人
     */
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    /**
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 审批代理人
     */
    public UserSimpleInfo getApproveAgent() {
        return approveAgent;
    }

    public void setApproveAgent(UserSimpleInfo approveAgent) {
        this.approveAgent = approveAgent;
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
