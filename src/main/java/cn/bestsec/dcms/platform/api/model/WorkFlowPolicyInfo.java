package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlowPolicyInfo {
    private Integer workFlowPolicyId;
    private Integer fileLevel;
    private Integer workFlowType;
    private Long createTime;
    private List<WorkFlowRoleInfo> workFlowRoleList;
    
    public WorkFlowPolicyInfo() {
    }

    public WorkFlowPolicyInfo(Integer workFlowPolicyId, Integer fileLevel, Integer workFlowType, Long createTime, List<WorkFlowRoleInfo> workFlowRoleList) {
        this.workFlowPolicyId = workFlowPolicyId;
        this.fileLevel = fileLevel;
        this.workFlowType = workFlowType;
        this.createTime = createTime;
        this.workFlowRoleList = workFlowRoleList;
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
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 流程类型 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核
     */
    public Integer getWorkFlowType() {
        return workFlowType;
    }

    public void setWorkFlowType(Integer workFlowType) {
        this.workFlowType = workFlowType;
    }

    /**
     * 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 审批员列表 按step级数
     */
    public List<WorkFlowRoleInfo> getWorkFlowRoleList() {
        return workFlowRoleList;
    }

    public void setWorkFlowRoleList(List<WorkFlowRoleInfo> workFlowRoleList) {
        this.workFlowRoleList = workFlowRoleList;
    }
}
