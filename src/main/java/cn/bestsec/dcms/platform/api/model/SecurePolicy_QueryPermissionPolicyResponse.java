package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryPermissionPolicyResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private PermissionInfo permission;
    
    public SecurePolicy_QueryPermissionPolicyResponse() {
    }

    public SecurePolicy_QueryPermissionPolicyResponse(PermissionInfo permission) {
        this.permission = permission;
    }

    public SecurePolicy_QueryPermissionPolicyResponse(Integer code, String msg, PermissionInfo permission) {
        this.code = code;
        this.msg = msg;
        this.permission = permission;
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
     * 权限详情
     */
    public PermissionInfo getPermission() {
        return permission;
    }

    public void setPermission(PermissionInfo permission) {
        this.permission = permission;
    }
}
