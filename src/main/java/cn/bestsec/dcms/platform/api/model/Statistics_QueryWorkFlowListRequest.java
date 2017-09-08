package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_QueryWorkFlowListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Long startTime;
    private Long endTime;
    private Integer workflowType;
    
    public Statistics_QueryWorkFlowListRequest() {
    }

    public Statistics_QueryWorkFlowListRequest(String token, Long startTime, Long endTime, Integer workflowType) {
        this.token = token;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workflowType = workflowType;
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
     * 开始时间
     */
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 截止时间
     */
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * 流程类型 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核
     */
    public Integer getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(Integer workflowType) {
        this.workflowType = workflowType;
    }
}
