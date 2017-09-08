package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_CreateResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer workFlowId;
    
    public WorkFlow_CreateResponse() {
    }

    public WorkFlow_CreateResponse(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }

    public WorkFlow_CreateResponse(Integer code, String msg, Integer workFlowId) {
        this.code = code;
        this.msg = msg;
        this.workFlowId = workFlowId;
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
     * 流程ID
     */
    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }
}
