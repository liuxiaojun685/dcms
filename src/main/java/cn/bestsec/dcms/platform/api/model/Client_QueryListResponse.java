package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_QueryListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer totalPages;
    private Integer totalElements;
    private List<ClientInfo> clientList;
    
    public Client_QueryListResponse() {
    }

    public Client_QueryListResponse(Integer totalPages, Integer totalElements, List<ClientInfo> clientList) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.clientList = clientList;
    }

    public Client_QueryListResponse(Integer code, String msg, Integer totalPages, Integer totalElements, List<ClientInfo> clientList) {
        this.code = code;
        this.msg = msg;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.clientList = clientList;
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
     * 
     */
    public List<ClientInfo> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientInfo> clientList) {
        this.clientList = clientList;
    }
}
