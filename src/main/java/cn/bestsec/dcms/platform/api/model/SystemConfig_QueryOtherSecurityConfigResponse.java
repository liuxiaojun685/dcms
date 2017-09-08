package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryOtherSecurityConfigResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer preclassifiedForce;
    
    public SystemConfig_QueryOtherSecurityConfigResponse() {
    }

    public SystemConfig_QueryOtherSecurityConfigResponse(Integer preclassifiedForce) {
        this.preclassifiedForce = preclassifiedForce;
    }

    public SystemConfig_QueryOtherSecurityConfigResponse(Integer code, String msg, Integer preclassifiedForce) {
        this.code = code;
        this.msg = msg;
        this.preclassifiedForce = preclassifiedForce;
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
     * 是否强制预定密 1是 0否
     */
    public Integer getPreclassifiedForce() {
        return preclassifiedForce;
    }

    public void setPreclassifiedForce(Integer preclassifiedForce) {
        this.preclassifiedForce = preclassifiedForce;
    }
}
