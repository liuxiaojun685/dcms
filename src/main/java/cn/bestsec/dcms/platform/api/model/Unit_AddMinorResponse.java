package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Unit_AddMinorResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String unitNo;
    
    public Unit_AddMinorResponse() {
    }

    public Unit_AddMinorResponse(String unitNo) {
        this.unitNo = unitNo;
    }

    public Unit_AddMinorResponse(Integer code, String msg, String unitNo) {
        this.code = code;
        this.msg = msg;
        this.unitNo = unitNo;
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
}
