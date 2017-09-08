package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Unit_QueryMajorListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String unitNo;
    private String name;
    private String description;
    
    public Unit_QueryMajorListResponse() {
    }

    public Unit_QueryMajorListResponse(String unitNo, String name, String description) {
        this.unitNo = unitNo;
        this.name = name;
        this.description = description;
    }

    public Unit_QueryMajorListResponse(Integer code, String msg, String unitNo, String name, String description) {
        this.code = code;
        this.msg = msg;
        this.unitNo = unitNo;
        this.name = name;
        this.description = description;
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
