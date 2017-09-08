package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryWorkFlowByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowPolicyId;
    
    public SecurePolicy_QueryWorkFlowByIdRequest() {
    }

    public SecurePolicy_QueryWorkFlowByIdRequest(String token, Integer workFlowPolicyId) {
        this.token = token;
        this.workFlowPolicyId = workFlowPolicyId;
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
}
