package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Business_DeleteRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer bId;
    
    public Business_DeleteRequest() {
    }

    public Business_DeleteRequest(String token, Integer bId) {
        this.token = token;
        this.bId = bId;
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
    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
    }
}
