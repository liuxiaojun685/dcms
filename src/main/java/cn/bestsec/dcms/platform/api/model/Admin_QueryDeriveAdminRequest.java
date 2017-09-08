package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_QueryDeriveAdminRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer adminType;
    
    public Admin_QueryDeriveAdminRequest() {
    }

    public Admin_QueryDeriveAdminRequest(String token, Integer adminType) {
        this.token = token;
        this.adminType = adminType;
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
     * 管理员类型 1安全审计员 2系统管理员 3安全保密管理员
     */
    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }
}
