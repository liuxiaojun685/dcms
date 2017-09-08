package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_DeleteByIdResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer roleId;
    
    public Role_DeleteByIdResponse() {
    }

    public Role_DeleteByIdResponse(Integer roleId) {
        this.roleId = roleId;
    }

    public Role_DeleteByIdResponse(Integer code, String msg, Integer roleId) {
        this.code = code;
        this.msg = msg;
        this.roleId = roleId;
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
     * 角色ID，删除失败时返回。
     */
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
