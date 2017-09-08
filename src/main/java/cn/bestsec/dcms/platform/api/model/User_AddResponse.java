package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_AddResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String uid;
    
    public User_AddResponse() {
    }

    public User_AddResponse(String uid) {
        this.uid = uid;
    }

    public User_AddResponse(Integer code, String msg, String uid) {
        this.code = code;
        this.msg = msg;
        this.uid = uid;
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
     * 用户ID
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
