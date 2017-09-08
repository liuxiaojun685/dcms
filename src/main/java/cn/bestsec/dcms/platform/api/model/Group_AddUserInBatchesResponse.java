package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Group_AddUserInBatchesResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    
    public Group_AddUserInBatchesResponse() {
    }

    public Group_AddUserInBatchesResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
}