package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_QueryDeriveAdminResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<AdminRole> adminList;
    
    public Admin_QueryDeriveAdminResponse() {
    }

    public Admin_QueryDeriveAdminResponse(List<AdminRole> adminList) {
        this.adminList = adminList;
    }

    public Admin_QueryDeriveAdminResponse(Integer code, String msg, List<AdminRole> adminList) {
        this.code = code;
        this.msg = msg;
        this.adminList = adminList;
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
     * 派生管理员角色
     */
    public List<AdminRole> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<AdminRole> adminList) {
        this.adminList = adminList;
    }
}
