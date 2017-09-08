package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryLogArchiveKeepTimeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String riskLevel1;
    private String riskLevel2;
    private String riskLevel3;
    private Integer autoLogArchiveEnable;
    
    public SystemConfig_QueryLogArchiveKeepTimeResponse() {
    }

    public SystemConfig_QueryLogArchiveKeepTimeResponse(String riskLevel1, String riskLevel2, String riskLevel3, Integer autoLogArchiveEnable) {
        this.riskLevel1 = riskLevel1;
        this.riskLevel2 = riskLevel2;
        this.riskLevel3 = riskLevel3;
        this.autoLogArchiveEnable = autoLogArchiveEnable;
    }

    public SystemConfig_QueryLogArchiveKeepTimeResponse(Integer code, String msg, String riskLevel1, String riskLevel2, String riskLevel3, Integer autoLogArchiveEnable) {
        this.code = code;
        this.msg = msg;
        this.riskLevel1 = riskLevel1;
        this.riskLevel2 = riskLevel2;
        this.riskLevel3 = riskLevel3;
        this.autoLogArchiveEnable = autoLogArchiveEnable;
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
     * 一般日志保留时间
     */
    public String getRiskLevel1() {
        return riskLevel1;
    }

    public void setRiskLevel1(String riskLevel1) {
        this.riskLevel1 = riskLevel1;
    }

    /**
     * 重要日志保留时间
     */
    public String getRiskLevel2() {
        return riskLevel2;
    }

    public void setRiskLevel2(String riskLevel2) {
        this.riskLevel2 = riskLevel2;
    }

    /**
     * 严重日志保留时间
     */
    public String getRiskLevel3() {
        return riskLevel3;
    }

    public void setRiskLevel3(String riskLevel3) {
        this.riskLevel3 = riskLevel3;
    }

    /**
     * 是否允许自动归档 1是 0否
     */
    public Integer getAutoLogArchiveEnable() {
        return autoLogArchiveEnable;
    }

    public void setAutoLogArchiveEnable(Integer autoLogArchiveEnable) {
        this.autoLogArchiveEnable = autoLogArchiveEnable;
    }
}
