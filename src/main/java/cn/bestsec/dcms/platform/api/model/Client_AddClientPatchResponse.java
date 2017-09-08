package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_AddClientPatchResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer patchId;
    
    public Client_AddClientPatchResponse() {
    }

    public Client_AddClientPatchResponse(Integer patchId) {
        this.patchId = patchId;
    }

    public Client_AddClientPatchResponse(Integer code, String msg, Integer patchId) {
        this.code = code;
        this.msg = msg;
        this.patchId = patchId;
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
     * 补丁ID
     */
    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }
}
