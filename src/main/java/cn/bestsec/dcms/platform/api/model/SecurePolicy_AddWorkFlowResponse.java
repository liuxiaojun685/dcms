package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_AddWorkFlowResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer workFlowPolicyId;
    
    public SecurePolicy_AddWorkFlowResponse() {
    }

    public SecurePolicy_AddWorkFlowResponse(Integer workFlowPolicyId) {
        this.workFlowPolicyId = workFlowPolicyId;
    }

    public SecurePolicy_AddWorkFlowResponse(Integer code, String msg, Integer workFlowPolicyId) {
        this.code = code;
        this.msg = msg;
        this.workFlowPolicyId = workFlowPolicyId;
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
     * 流程策略ID
     */
    public Integer getWorkFlowPolicyId() {
        return workFlowPolicyId;
    }

    public void setWorkFlowPolicyId(Integer workFlowPolicyId) {
        this.workFlowPolicyId = workFlowPolicyId;
    }
}
