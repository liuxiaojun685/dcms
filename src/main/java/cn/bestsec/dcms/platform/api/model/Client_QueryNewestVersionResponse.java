package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_QueryNewestVersionResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private PatchInfo patchInfo;
    
    public Client_QueryNewestVersionResponse() {
    }

    public Client_QueryNewestVersionResponse(PatchInfo patchInfo) {
        this.patchInfo = patchInfo;
    }

    public Client_QueryNewestVersionResponse(Integer code, String msg, PatchInfo patchInfo) {
        this.code = code;
        this.msg = msg;
        this.patchInfo = patchInfo;
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
     * 
     */
    public PatchInfo getPatchInfo() {
        return patchInfo;
    }

    public void setPatchInfo(PatchInfo patchInfo) {
        this.patchInfo = patchInfo;
    }
}
