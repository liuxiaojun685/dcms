package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ComponentInfo {
    private Integer componentId;
    private String name;
    private String description;
    private Integer enable;
    
    public ComponentInfo() {
    }

    public ComponentInfo(Integer componentId, String name, String description, Integer enable) {
        this.componentId = componentId;
        this.name = name;
        this.description = description;
        this.enable = enable;
    }

    /**
     * 组件ID
     */
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    /**
     * 组件名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 组件描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 是否启用 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
