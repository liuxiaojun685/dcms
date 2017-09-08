package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_QueryApproverSelectListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer totalStep;
    private List<WorkFlowRoleInfo> stepList;
    
    public WorkFlow_QueryApproverSelectListResponse() {
    }

    public WorkFlow_QueryApproverSelectListResponse(Integer totalStep, List<WorkFlowRoleInfo> stepList) {
        this.totalStep = totalStep;
        this.stepList = stepList;
    }

    public WorkFlow_QueryApproverSelectListResponse(Integer code, String msg, Integer totalStep, List<WorkFlowRoleInfo> stepList) {
        this.code = code;
        this.msg = msg;
        this.totalStep = totalStep;
        this.stepList = stepList;
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
     * 总审批级数
     */
    public Integer getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(Integer totalStep) {
        this.totalStep = totalStep;
    }

    /**
     * 
     */
    public List<WorkFlowRoleInfo> getStepList() {
        return stepList;
    }

    public void setStepList(List<WorkFlowRoleInfo> stepList) {
        this.stepList = stepList;
    }
}
