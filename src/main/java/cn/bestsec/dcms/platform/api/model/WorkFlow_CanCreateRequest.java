package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_CanCreateRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowType;
    private String srcFid;
    private Integer applyFileLevel;
    private Integer fileState;
    
    public WorkFlow_CanCreateRequest() {
    }

    public WorkFlow_CanCreateRequest(String token, Integer workFlowType, String srcFid, Integer applyFileLevel, Integer fileState) {
        this.token = token;
        this.workFlowType = workFlowType;
        this.srcFid = srcFid;
        this.applyFileLevel = applyFileLevel;
        this.fileState = fileState;
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
     * 文件ID
     */
    public String getSrcFid() {
        return srcFid;
    }

    public void setSrcFid(String srcFid) {
        this.srcFid = srcFid;
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
     * 定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }
}
