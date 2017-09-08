package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_QueryListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<MiddlewareInfo> middlewareList;
    
    public Middleware_QueryListResponse() {
    }

    public Middleware_QueryListResponse(List<MiddlewareInfo> middlewareList) {
        this.middlewareList = middlewareList;
    }

    public Middleware_QueryListResponse(Integer code, String msg, List<MiddlewareInfo> middlewareList) {
        this.code = code;
        this.msg = msg;
        this.middlewareList = middlewareList;
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
     * 中间件列表
     */
    public List<MiddlewareInfo> getMiddlewareList() {
        return middlewareList;
    }

    public void setMiddlewareList(List<MiddlewareInfo> middlewareList) {
        this.middlewareList = middlewareList;
    }
}
