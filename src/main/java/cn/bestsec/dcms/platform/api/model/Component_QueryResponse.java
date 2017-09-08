package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Component_QueryResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<ComponentInfo> componentList;
    
    public Component_QueryResponse() {
    }

    public Component_QueryResponse(List<ComponentInfo> componentList) {
        this.componentList = componentList;
    }

    public Component_QueryResponse(Integer code, String msg, List<ComponentInfo> componentList) {
        this.code = code;
        this.msg = msg;
        this.componentList = componentList;
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
     * 组件列表
     */
    public List<ComponentInfo> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<ComponentInfo> componentList) {
        this.componentList = componentList;
    }
}
