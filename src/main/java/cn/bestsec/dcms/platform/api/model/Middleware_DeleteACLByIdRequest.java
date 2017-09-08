package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_DeleteACLByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer middlewareACLId;
    
    public Middleware_DeleteACLByIdRequest() {
    }

    public Middleware_DeleteACLByIdRequest(String token, Integer middlewareACLId) {
        this.token = token;
        this.middlewareACLId = middlewareACLId;
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
     * 接口访问控制策略ID
     */
    public Integer getMiddlewareACLId() {
        return middlewareACLId;
    }

    public void setMiddlewareACLId(Integer middlewareACLId) {
        this.middlewareACLId = middlewareACLId;
    }
}
