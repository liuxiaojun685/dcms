package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_LoadWatermarkConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer type;
    
    public Client_LoadWatermarkConfigRequest() {
    }

    public Client_LoadWatermarkConfigRequest(String token, Integer type) {
        this.token = token;
        this.type = type;
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
     * 1 打印， 2屏幕
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
