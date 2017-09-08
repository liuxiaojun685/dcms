package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_AddBasisResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer basisId;
    
    public Basis_AddBasisResponse() {
    }

    public Basis_AddBasisResponse(Integer basisId) {
        this.basisId = basisId;
    }

    public Basis_AddBasisResponse(Integer code, String msg, Integer basisId) {
        this.code = code;
        this.msg = msg;
        this.basisId = basisId;
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
     * 定密依据ID
     */
    public Integer getBasisId() {
        return basisId;
    }

    public void setBasisId(Integer basisId) {
        this.basisId = basisId;
    }
}
