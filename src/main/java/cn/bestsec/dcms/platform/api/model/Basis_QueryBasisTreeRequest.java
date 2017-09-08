package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_QueryBasisTreeRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer basisType;
    private Integer basisLevel;
    
    public Basis_QueryBasisTreeRequest() {
    }

    public Basis_QueryBasisTreeRequest(String token, Integer basisType, Integer basisLevel) {
        this.token = token;
        this.basisType = basisType;
        this.basisLevel = basisLevel;
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

    /**
     * 定密依据密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getBasisLevel() {
        return basisLevel;
    }

    public void setBasisLevel(Integer basisLevel) {
        this.basisLevel = basisLevel;
    }
}
