package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateLogArchiveKeepTimeRequest extends CommonRequestFieldsSupport {
    private String token;
    private String riskLevel1;
    private String riskLevel2;
    private String riskLevel3;
    private Integer autoLogArchiveEnable;
    
    public SystemConfig_UpdateLogArchiveKeepTimeRequest() {
    }

    public SystemConfig_UpdateLogArchiveKeepTimeRequest(String token, String riskLevel1, String riskLevel2, String riskLevel3, Integer autoLogArchiveEnable) {
        this.token = token;
        this.riskLevel1 = riskLevel1;
        this.riskLevel2 = riskLevel2;
        this.riskLevel3 = riskLevel3;
        this.autoLogArchiveEnable = autoLogArchiveEnable;
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
