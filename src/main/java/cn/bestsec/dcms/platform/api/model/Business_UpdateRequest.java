package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Business_UpdateRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer bId;
    private String name;
    
    public Business_UpdateRequest() {
    }

    public Business_UpdateRequest(String token, Integer bId, String name) {
        this.token = token;
        this.bId = bId;
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
    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
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
