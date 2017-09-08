package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_QueryScopeAdminResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<AdminRole> userList;
    
    public Admin_QueryScopeAdminResponse() {
    }

    public Admin_QueryScopeAdminResponse(List<AdminRole> userList) {
        this.userList = userList;
    }

    public Admin_QueryScopeAdminResponse(Integer code, String msg, List<AdminRole> userList) {
        this.code = code;
        this.msg = msg;
        this.userList = userList;
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
     * 管理员用户列表
     */
    public List<AdminRole> getUserList() {
        return userList;
    }

    public void setUserList(List<AdminRole> userList) {
        this.userList = userList;
    }
}
