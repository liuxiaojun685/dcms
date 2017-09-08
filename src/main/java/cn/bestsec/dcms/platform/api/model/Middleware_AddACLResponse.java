package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_AddACLResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer middlewareACLId;
    
    public Middleware_AddACLResponse() {
    }

    public Middleware_AddACLResponse(Integer middlewareACLId) {
        this.middlewareACLId = middlewareACLId;
    }

    public Middleware_AddACLResponse(Integer code, String msg, Integer middlewareACLId) {
        this.code = code;
        this.msg = msg;
        this.middlewareACLId = middlewareACLId;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
