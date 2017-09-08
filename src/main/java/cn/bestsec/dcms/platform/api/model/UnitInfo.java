package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class UnitInfo {
    private String unitNo;
    private String name;
    
    public UnitInfo() {
    }

    public UnitInfo(String unitNo, String name) {
        this.unitNo = unitNo;
        this.name = name;
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
}
