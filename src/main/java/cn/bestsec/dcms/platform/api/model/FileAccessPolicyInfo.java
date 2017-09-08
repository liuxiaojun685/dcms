package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileAccessPolicyInfo {
    private Integer fileLevel;
    private Integer userLevel;
    private Integer enable;
    
    public FileAccessPolicyInfo() {
    }

    public FileAccessPolicyInfo(Integer fileLevel, Integer userLevel, Integer enable) {
        this.fileLevel = fileLevel;
        this.userLevel = userLevel;
        this.enable = enable;
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
     * 用户密级 1一般 2重要 3核心
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * 是否允许操作 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
