package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class MiddlewareInfo {
    private Integer middlewareId;
    private String name;
    private String description;
    private Integer enable;
    private List<MiddlewareACLInfo> middlewareUserList;
    
    public MiddlewareInfo() {
    }

    public MiddlewareInfo(Integer middlewareId, String name, String description, Integer enable, List<MiddlewareACLInfo> middlewareUserList) {
        this.middlewareId = middlewareId;
        this.name = name;
        this.description = description;
        this.enable = enable;
        this.middlewareUserList = middlewareUserList;
    }

    /**
     * 中间件ID
     */
    public Integer getMiddlewareId() {
        return middlewareId;
    }

    public void setMiddlewareId(Integer middlewareId) {
        this.middlewareId = middlewareId;
    }

    /**
     * 中间件名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 中间件描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 中间件是否启用(被动显示) 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 中间件应用列表
     */
    public List<MiddlewareACLInfo> getMiddlewareUserList() {
        return middlewareUserList;
    }

    public void setMiddlewareUserList(List<MiddlewareACLInfo> middlewareUserList) {
        this.middlewareUserList = middlewareUserList;
    }
}
