package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_AddBasisRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer basisId;
    private String basisName;
    
    public Basis_AddBasisRequest() {
    }

    public Basis_AddBasisRequest(String token, Integer basisId, String basisName) {
        this.token = token;
        this.basisId = basisId;
        this.basisName = basisName;
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
     * 定密依据分类ID
     */
    public Integer getBasisId() {
        return basisId;
    }

    public void setBasisId(Integer basisId) {
        this.basisId = basisId;
    }

    /**
     * 定密依据依据名称
     */
    public String getBasisName() {
        return basisName;
    }

    public void setBasisName(String basisName) {
        this.basisName = basisName;
    }
}
