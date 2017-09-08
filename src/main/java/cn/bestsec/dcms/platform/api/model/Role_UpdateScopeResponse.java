package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_UpdateScopeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String roleDispScope;
    
    public Role_UpdateScopeResponse() {
    }

    public Role_UpdateScopeResponse(String roleDispScope) {
        this.roleDispScope = roleDispScope;
    }

    public Role_UpdateScopeResponse(Integer code, String msg, String roleDispScope) {
        this.code = code;
        this.msg = msg;
        this.roleDispScope = roleDispScope;
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
     * 负责范围简述
     */
    public String getRoleDispScope() {
        return roleDispScope;
    }

    public void setRoleDispScope(String roleDispScope) {
        this.roleDispScope = roleDispScope;
    }
}
