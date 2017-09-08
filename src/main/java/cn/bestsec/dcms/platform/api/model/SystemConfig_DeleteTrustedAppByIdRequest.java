package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_DeleteTrustedAppByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer trustedAppId;
    
    public SystemConfig_DeleteTrustedAppByIdRequest() {
    }

    public SystemConfig_DeleteTrustedAppByIdRequest(String token, Integer trustedAppId) {
        this.token = token;
        this.trustedAppId = trustedAppId;
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
     * 可信应用程序ID
     */
    public Integer getTrustedAppId() {
        return trustedAppId;
    }

    public void setTrustedAppId(Integer trustedAppId) {
        this.trustedAppId = trustedAppId;
    }
}
