package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_QueryListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<RoleInfo> roleList;
    
    public Role_QueryListResponse() {
    }

    public Role_QueryListResponse(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public Role_QueryListResponse(Integer code, String msg, List<RoleInfo> roleList) {
        this.code = code;
        this.msg = msg;
        this.roleList = roleList;
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
     * 用户信息列表
     */
    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }
}
