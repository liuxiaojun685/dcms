package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_AddAgentResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer agentId;
    
    public WorkFlow_AddAgentResponse() {
    }

    public WorkFlow_AddAgentResponse(Integer agentId) {
        this.agentId = agentId;
    }

    public WorkFlow_AddAgentResponse(Integer code, String msg, Integer agentId) {
        this.code = code;
        this.msg = msg;
        this.agentId = agentId;
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
     * 委托ID
     */
    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
}
