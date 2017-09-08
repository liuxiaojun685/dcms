package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_QueryFinishedResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer totalPages;
    private Integer totalElements;
    private List<WorkFlowSimpleInfo> workFlowList;
    
    public WorkFlow_QueryFinishedResponse() {
    }

    public WorkFlow_QueryFinishedResponse(Integer totalPages, Integer totalElements, List<WorkFlowSimpleInfo> workFlowList) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.workFlowList = workFlowList;
    }

    public WorkFlow_QueryFinishedResponse(Integer code, String msg, Integer totalPages, Integer totalElements, List<WorkFlowSimpleInfo> workFlowList) {
        this.code = code;
        this.msg = msg;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.workFlowList = workFlowList;
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
     * 总页数
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 总行数
     */
    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * 流程列表
     */
    public List<WorkFlowSimpleInfo> getWorkFlowList() {
        return workFlowList;
    }

    public void setWorkFlowList(List<WorkFlowSimpleInfo> workFlowList) {
        this.workFlowList = workFlowList;
    }
}
