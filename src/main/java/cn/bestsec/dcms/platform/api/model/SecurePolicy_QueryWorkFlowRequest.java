package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryWorkFlowRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileLevel;
    private Integer workFlowType;
    
    public SecurePolicy_QueryWorkFlowRequest() {
    }

    public SecurePolicy_QueryWorkFlowRequest(String token, Integer fileLevel, Integer workFlowType) {
        this.token = token;
        this.fileLevel = fileLevel;
        this.workFlowType = workFlowType;
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
}
