package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateOtherSecurityConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer preclassifiedForce;
    
    public SystemConfig_UpdateOtherSecurityConfigRequest() {
    }

    public SystemConfig_UpdateOtherSecurityConfigRequest(String token, Integer preclassifiedForce) {
        this.token = token;
        this.preclassifiedForce = preclassifiedForce;
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
     * 是否强制预定密 1是 0否
     */
    public Integer getPreclassifiedForce() {
        return preclassifiedForce;
    }

    public void setPreclassifiedForce(Integer preclassifiedForce) {
        this.preclassifiedForce = preclassifiedForce;
    }
}
