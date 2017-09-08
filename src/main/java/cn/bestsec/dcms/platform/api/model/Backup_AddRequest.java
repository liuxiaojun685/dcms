package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Backup_AddRequest extends CommonRequestFieldsSupport {
    private String token;
    private String description;
    
    public Backup_AddRequest() {
    }

    public Backup_AddRequest(String token, String description) {
        this.token = token;
        this.description = description;
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
     * 备份描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
