package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryWorkFlowResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<WorkFlowPolicyInfo> workFlowPolicyList;
    
    public SecurePolicy_QueryWorkFlowResponse() {
    }

    public SecurePolicy_QueryWorkFlowResponse(List<WorkFlowPolicyInfo> workFlowPolicyList) {
        this.workFlowPolicyList = workFlowPolicyList;
    }

    public SecurePolicy_QueryWorkFlowResponse(Integer code, String msg, List<WorkFlowPolicyInfo> workFlowPolicyList) {
        this.code = code;
        this.msg = msg;
        this.workFlowPolicyList = workFlowPolicyList;
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
     * 流程策略信息
     */
    public List<WorkFlowPolicyInfo> getWorkFlowPolicyList() {
        return workFlowPolicyList;
    }

    public void setWorkFlowPolicyList(List<WorkFlowPolicyInfo> workFlowPolicyList) {
        this.workFlowPolicyList = workFlowPolicyList;
    }
}
