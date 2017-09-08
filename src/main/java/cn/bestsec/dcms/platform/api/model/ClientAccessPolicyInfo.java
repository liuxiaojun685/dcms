package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ClientAccessPolicyInfo {
    private Integer clientLevel;
    private Integer userLevel;
    private Integer enable;
    
    public ClientAccessPolicyInfo() {
    }

    public ClientAccessPolicyInfo(Integer clientLevel, Integer userLevel, Integer enable) {
        this.clientLevel = clientLevel;
        this.userLevel = userLevel;
        this.enable = enable;
    }

    /**
     * 终端密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(Integer clientLevel) {
        this.clientLevel = clientLevel;
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
     * 是否允许登陆 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
