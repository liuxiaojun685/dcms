package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_UpdateScopeRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer roleId;
    private List<ScopeSimpleInfo> scopeList;
    
    public Role_UpdateScopeRequest() {
    }

    public Role_UpdateScopeRequest(String token, Integer roleId, List<ScopeSimpleInfo> scopeList) {
        this.token = token;
        this.roleId = roleId;
        this.scopeList = scopeList;
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
     * 负责的组织ID数组
     */
    public List<ScopeSimpleInfo> getScopeList() {
        return scopeList;
    }

    public void setScopeList(List<ScopeSimpleInfo> scopeList) {
        this.scopeList = scopeList;
    }
}
