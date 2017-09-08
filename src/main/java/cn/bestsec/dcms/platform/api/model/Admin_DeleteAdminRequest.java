package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_DeleteAdminRequest extends CommonRequestFieldsSupport {
    private String token;
    private String aid;
    
    public Admin_DeleteAdminRequest() {
    }

    public Admin_DeleteAdminRequest(String token, String aid) {
        this.token = token;
        this.aid = aid;
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
     * 管理员ID
     */
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }
}
