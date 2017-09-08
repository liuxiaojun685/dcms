package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_QueryClientUninstallPasswdListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    
    public Client_QueryClientUninstallPasswdListRequest() {
    }

    public Client_QueryClientUninstallPasswdListRequest(String token, Integer pageNumber, Integer pageSize) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
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
}
