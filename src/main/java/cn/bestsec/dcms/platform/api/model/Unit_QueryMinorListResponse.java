package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Unit_QueryMinorListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<UnitItem> unitList;
    
    public Unit_QueryMinorListResponse() {
    }

    public Unit_QueryMinorListResponse(List<UnitItem> unitList) {
        this.unitList = unitList;
    }

    public Unit_QueryMinorListResponse(Integer code, String msg, List<UnitItem> unitList) {
        this.code = code;
        this.msg = msg;
        this.unitList = unitList;
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
     * 辅助定密单位列表
     */
    public List<UnitItem> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<UnitItem> unitList) {
        this.unitList = unitList;
    }
}
