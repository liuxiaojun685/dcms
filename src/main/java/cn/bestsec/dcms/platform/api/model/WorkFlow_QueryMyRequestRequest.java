package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_QueryMyRequestRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer state;
    
    public WorkFlow_QueryMyRequestRequest() {
    }

    public WorkFlow_QueryMyRequestRequest(String token, Integer pageNumber, Integer pageSize, Integer state) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.state = state;
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
     * 页号
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 行数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 我的申请完成状态 0未完成，1完成
     */
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
