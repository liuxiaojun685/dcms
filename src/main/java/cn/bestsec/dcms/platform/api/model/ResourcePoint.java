package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ResourcePoint {
    private Integer type;
    private String resourceName;
    private String percentage;
    
    public ResourcePoint() {
    }

    public ResourcePoint(Integer type, String resourceName, String percentage) {
        this.type = type;
        this.resourceName = resourceName;
        this.percentage = percentage;
    }

    /**
     * 类型 1CPU 2内存 3硬盘
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 名称
     */
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * 占有率
     */
    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
