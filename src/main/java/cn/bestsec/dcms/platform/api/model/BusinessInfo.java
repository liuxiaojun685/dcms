package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BusinessInfo {
    private Integer bId;
    private String name;
    
    public BusinessInfo() {
    }

    public BusinessInfo(Integer bId, String name) {
        this.bId = bId;
        this.name = name;
    }

    /**
     * 业务属性ID
     */
    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
    }

    /**
     * 业务名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
