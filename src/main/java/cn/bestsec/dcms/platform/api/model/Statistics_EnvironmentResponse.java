package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_EnvironmentResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<ResourcePoint> resourceList;
    
    public Statistics_EnvironmentResponse() {
    }

    public Statistics_EnvironmentResponse(List<ResourcePoint> resourceList) {
        this.resourceList = resourceList;
    }

    public Statistics_EnvironmentResponse(Integer code, String msg, List<ResourcePoint> resourceList) {
        this.code = code;
        this.msg = msg;
        this.resourceList = resourceList;
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
     * 系统资源
     */
    public List<ResourcePoint> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourcePoint> resourceList) {
        this.resourceList = resourceList;
    }
}
