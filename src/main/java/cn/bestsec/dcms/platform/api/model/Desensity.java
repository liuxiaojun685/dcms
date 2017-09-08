package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Desensity {
    private Integer fileLevel;
    private Integer isDesensity;
    
    public Desensity() {
    }

    public Desensity(Integer fileLevel, Integer isDesensity) {
        this.fileLevel = fileLevel;
        this.isDesensity = isDesensity;
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
     * 是否脱敏/显示 0否 1是
     */
    public Integer getIsDesensity() {
        return isDesensity;
    }

    public void setIsDesensity(Integer isDesensity) {
        this.isDesensity = isDesensity;
    }
}
