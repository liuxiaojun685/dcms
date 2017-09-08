package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_UpdateWorkFlowRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowPolicyId;
    private List<Integer> roleId;
    private Integer step;
    
    public SecurePolicy_UpdateWorkFlowRequest() {
    }

    public SecurePolicy_UpdateWorkFlowRequest(String token, Integer workFlowPolicyId, List<Integer> roleId, Integer step) {
        this.token = token;
        this.workFlowPolicyId = workFlowPolicyId;
        this.roleId = roleId;
        this.step = step;
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
     * 流程策略ID
     */
    public Integer getWorkFlowPolicyId() {
        return workFlowPolicyId;
    }

    public void setWorkFlowPolicyId(Integer workFlowPolicyId) {
        this.workFlowPolicyId = workFlowPolicyId;
    }

    /**
     * 责任人角色信息ID 只有签发流程由签发人审核，其他均由定密责任人审核。
     */
    public List<Integer> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Integer> roleId) {
        this.roleId = roleId;
    }

    /**
     * 审批级数
     */
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
