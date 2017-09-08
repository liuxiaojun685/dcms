package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Component_UpdateByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer componentId;
    private Integer enable;
    
    public Component_UpdateByIdRequest() {
    }

    public Component_UpdateByIdRequest(String token, Integer componentId, Integer enable) {
        this.token = token;
        this.componentId = componentId;
        this.enable = enable;
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
     * 组件ID
     */
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
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
