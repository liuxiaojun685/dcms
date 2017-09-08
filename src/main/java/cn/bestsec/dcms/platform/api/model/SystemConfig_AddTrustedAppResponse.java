package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_AddTrustedAppResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer trustedAppId;
    
    public SystemConfig_AddTrustedAppResponse() {
    }

    public SystemConfig_AddTrustedAppResponse(Integer trustedAppId) {
        this.trustedAppId = trustedAppId;
    }

    public SystemConfig_AddTrustedAppResponse(Integer code, String msg, Integer trustedAppId) {
        this.code = code;
        this.msg = msg;
        this.trustedAppId = trustedAppId;
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
     * 可信应用程序ID
     */
    public Integer getTrustedAppId() {
        return trustedAppId;
    }

    public void setTrustedAppId(Integer trustedAppId) {
        this.trustedAppId = trustedAppId;
    }
}
