package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_AddResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String cid;
    
    public Client_AddResponse() {
    }

    public Client_AddResponse(String cid) {
        this.cid = cid;
    }

    public Client_AddResponse(Integer code, String msg, String cid) {
        this.code = code;
        this.msg = msg;
        this.cid = cid;
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
     * 终端ID
     */
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
