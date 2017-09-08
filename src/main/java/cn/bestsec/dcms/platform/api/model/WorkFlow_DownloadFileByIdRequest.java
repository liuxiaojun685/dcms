package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_DownloadFileByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowId;
    
    public WorkFlow_DownloadFileByIdRequest() {
    }

    public WorkFlow_DownloadFileByIdRequest(String token, Integer workFlowId) {
        this.token = token;
        this.workFlowId = workFlowId;
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
     * 流程ID
     */
    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }
}
