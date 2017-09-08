package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Department_AddResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String did;
    
    public Department_AddResponse() {
    }

    public Department_AddResponse(String did) {
        this.did = did;
    }

    public Department_AddResponse(Integer code, String msg, String did) {
        this.code = code;
        this.msg = msg;
        this.did = did;
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
     * 部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
