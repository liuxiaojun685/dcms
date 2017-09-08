package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_DeleteBasisRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer basisId;
    
    public Basis_DeleteBasisRequest() {
    }

    public Basis_DeleteBasisRequest(String token, Integer basisId) {
        this.token = token;
        this.basisId = basisId;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
