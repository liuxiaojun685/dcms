package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_QueryBasisClassListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer basisType;
    
    public Basis_QueryBasisClassListRequest() {
    }

    public Basis_QueryBasisClassListRequest(String token, Integer basisType) {
        this.token = token;
        this.basisType = basisType;
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
     * 定密依据类型 0确定性 1不明确 2无权定密 3派生
     */
    public Integer getBasisType() {
        return basisType;
    }

    public void setBasisType(Integer basisType) {
        this.basisType = basisType;
    }
}
