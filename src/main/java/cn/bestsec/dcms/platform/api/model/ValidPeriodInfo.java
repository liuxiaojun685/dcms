package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ValidPeriodInfo {
    private Integer fileLevel;
    private String validPeriod;
    
    public ValidPeriodInfo() {
    }

    public ValidPeriodInfo(Integer fileLevel, String validPeriod) {
        this.fileLevel = fileLevel;
        this.validPeriod = validPeriod;
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
}
