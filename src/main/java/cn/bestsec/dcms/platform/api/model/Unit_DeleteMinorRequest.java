package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Unit_DeleteMinorRequest extends CommonRequestFieldsSupport {
    private String token;
    private String unitNo;
    
    public Unit_DeleteMinorRequest() {
    }

    public Unit_DeleteMinorRequest(String token, String unitNo) {
        this.token = token;
        this.unitNo = unitNo;
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
     * 定密单位编号
     */
    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
}
