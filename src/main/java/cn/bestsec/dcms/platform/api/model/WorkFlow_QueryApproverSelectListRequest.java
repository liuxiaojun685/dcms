package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_QueryApproverSelectListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowType;
    private Integer applyFileLevel;
    private Integer workFlowId;
    private Integer step;
    
    public WorkFlow_QueryApproverSelectListRequest() {
    }

    public WorkFlow_QueryApproverSelectListRequest(String token, Integer workFlowType, Integer applyFileLevel, Integer workFlowId, Integer step) {
        this.token = token;
        this.workFlowType = workFlowType;
        this.applyFileLevel = applyFileLevel;
        this.workFlowId = workFlowId;
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
     * 流程名称 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核
     */
    public Integer getWorkFlowType() {
        return workFlowType;
    }

    public void setWorkFlowType(Integer workFlowType) {
        this.workFlowType = workFlowType;
    }

    /**
     * 申请的文件密级
     */
    public Integer getApplyFileLevel() {
        return applyFileLevel;
    }

    public void setApplyFileLevel(Integer applyFileLevel) {
        this.applyFileLevel = applyFileLevel;
    }

    /**
     * 流程ID
     */
    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }

    /**
     * 级数
     */
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
