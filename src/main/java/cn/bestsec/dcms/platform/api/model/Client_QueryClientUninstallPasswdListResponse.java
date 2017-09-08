package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_QueryClientUninstallPasswdListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer totalPages;
    private Integer totalElements;
    private List<UninstallPasswdInfo> uninstallPasswdList;
    
    public Client_QueryClientUninstallPasswdListResponse() {
    }

    public Client_QueryClientUninstallPasswdListResponse(Integer totalPages, Integer totalElements, List<UninstallPasswdInfo> uninstallPasswdList) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.uninstallPasswdList = uninstallPasswdList;
    }

    public Client_QueryClientUninstallPasswdListResponse(Integer code, String msg, Integer totalPages, Integer totalElements, List<UninstallPasswdInfo> uninstallPasswdList) {
        this.code = code;
        this.msg = msg;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.uninstallPasswdList = uninstallPasswdList;
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
    public List<UninstallPasswdInfo> getUninstallPasswdList() {
        return uninstallPasswdList;
    }

    public void setUninstallPasswdList(List<UninstallPasswdInfo> uninstallPasswdList) {
        this.uninstallPasswdList = uninstallPasswdList;
    }
}
