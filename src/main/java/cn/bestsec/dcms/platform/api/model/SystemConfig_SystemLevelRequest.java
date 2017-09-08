package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_SystemLevelRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer level;
    
    public SystemConfig_SystemLevelRequest() {
    }

    public SystemConfig_SystemLevelRequest(String token, Integer level) {
        this.token = token;
        this.level = level;
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
     * 系统可处理的最高密级文件用户终端。2秘密 3机密 4绝密
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
