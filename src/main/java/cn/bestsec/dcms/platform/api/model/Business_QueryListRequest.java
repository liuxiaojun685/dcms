package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Business_QueryListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer parentId;
    
    public Business_QueryListRequest() {
    }

    public Business_QueryListRequest(String token, Integer parentId) {
        this.token = token;
        this.parentId = parentId;
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
}
