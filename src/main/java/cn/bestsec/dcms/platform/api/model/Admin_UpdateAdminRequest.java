package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_UpdateAdminRequest extends CommonRequestFieldsSupport {
    private String token;
    private String aid;
    private String description;
    private Integer derive;
    
    public Admin_UpdateAdminRequest() {
    }

    public Admin_UpdateAdminRequest(String token, String aid, String description, Integer derive) {
        this.token = token;
        this.aid = aid;
        this.description = description;
        this.derive = derive;
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

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 是否具有任命取消的权限
     */
    public Integer getDerive() {
        return derive;
    }

    public void setDerive(Integer derive) {
        this.derive = derive;
    }
}
