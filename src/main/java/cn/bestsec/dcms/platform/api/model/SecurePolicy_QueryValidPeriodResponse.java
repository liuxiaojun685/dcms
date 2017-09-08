package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryValidPeriodResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer fileLevel;
    private String validPeriod;
    private Integer durationType;
    
    public SecurePolicy_QueryValidPeriodResponse() {
    }

    public SecurePolicy_QueryValidPeriodResponse(Integer fileLevel, String validPeriod, Integer durationType) {
        this.fileLevel = fileLevel;
        this.validPeriod = validPeriod;
        this.durationType = durationType;
    }

    public SecurePolicy_QueryValidPeriodResponse(Integer code, String msg, Integer fileLevel, String validPeriod, Integer durationType) {
        this.code = code;
        this.msg = msg;
        this.fileLevel = fileLevel;
        this.validPeriod = validPeriod;
        this.durationType = durationType;
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
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 格式yymmdd
     */
    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    /**
     * 保密期限 类型 0不限 2期限
     */
    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }
}
