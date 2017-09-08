package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Business_AddRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer parentId;
    private String name;
    
    public Business_AddRequest() {
    }

    public Business_AddRequest(String token, Integer parentId, String name) {
        this.token = token;
        this.parentId = parentId;
        this.name = name;
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
     * 业务属性ID
     */
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 业务名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
