package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_UpdateLevelRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleId;
    private Integer roleLevel;
    private Integer enable;
    
    public Role_UpdateLevelRequest() {
    }

    public Role_UpdateLevelRequest(String token, Integer roleId, Integer roleLevel, Integer enable) {
        this.token = token;
        this.roleId = roleId;
        this.roleLevel = roleLevel;
        this.enable = enable;
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
     * 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 文件密级
     */
    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    /**
     * 是否负责该文件密级
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
