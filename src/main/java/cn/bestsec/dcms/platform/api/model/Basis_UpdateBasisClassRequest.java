package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_UpdateBasisClassRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer basisId;
    private String basisClass;
    
    public Basis_UpdateBasisClassRequest() {
    }

    public Basis_UpdateBasisClassRequest(String token, Integer basisId, String basisClass) {
        this.token = token;
        this.basisId = basisId;
        this.basisClass = basisClass;
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

    /**
     * 定密依据分类
     */
    public String getBasisClass() {
        return basisClass;
    }

    public void setBasisClass(String basisClass) {
        this.basisClass = basisClass;
    }
}
