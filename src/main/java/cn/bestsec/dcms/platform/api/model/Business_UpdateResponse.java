package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Business_UpdateResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer bId;
    
    public Business_UpdateResponse() {
    }

    public Business_UpdateResponse(Integer bId) {
        this.bId = bId;
    }

    public Business_UpdateResponse(Integer code, String msg, Integer bId) {
        this.code = code;
        this.msg = msg;
        this.bId = bId;
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
     * 业务属性ID
     */
    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
    }
}
