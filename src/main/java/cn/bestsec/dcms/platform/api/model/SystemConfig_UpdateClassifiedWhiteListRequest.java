package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateClassifiedWhiteListRequest extends CommonRequestFieldsSupport {
    private String token;
    private String classifiedWhiteList;
    
    public SystemConfig_UpdateClassifiedWhiteListRequest() {
    }

    public SystemConfig_UpdateClassifiedWhiteListRequest(String token, String classifiedWhiteList) {
        this.token = token;
        this.classifiedWhiteList = classifiedWhiteList;
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
     * 标密白名单 |分割
     */
    public String getClassifiedWhiteList() {
        return classifiedWhiteList;
    }

    public void setClassifiedWhiteList(String classifiedWhiteList) {
        this.classifiedWhiteList = classifiedWhiteList;
    }
}
