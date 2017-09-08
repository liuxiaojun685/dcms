package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_QueryAgentResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<AgentRoleInfo> agentRoleList;
    
    public WorkFlow_QueryAgentResponse() {
    }

    public WorkFlow_QueryAgentResponse(List<AgentRoleInfo> agentRoleList) {
        this.agentRoleList = agentRoleList;
    }

    public WorkFlow_QueryAgentResponse(Integer code, String msg, List<AgentRoleInfo> agentRoleList) {
        this.code = code;
        this.msg = msg;
        this.agentRoleList = agentRoleList;
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
     * 代理人角色列表
     */
    public List<AgentRoleInfo> getAgentRoleList() {
        return agentRoleList;
    }

    public void setAgentRoleList(List<AgentRoleInfo> agentRoleList) {
        this.agentRoleList = agentRoleList;
    }
}
