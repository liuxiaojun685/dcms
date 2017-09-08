package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Unit_AddMinorRequest extends CommonRequestFieldsSupport {
    private String token;
    private String unitNo;
    private String name;
    private String description;
    
    public Unit_AddMinorRequest() {
    }

    public Unit_AddMinorRequest(String token, String unitNo, String name, String description) {
        this.token = token;
        this.unitNo = unitNo;
        this.name = name;
        this.description = description;
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

    /**
     * 定密单位名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 定密单位描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
