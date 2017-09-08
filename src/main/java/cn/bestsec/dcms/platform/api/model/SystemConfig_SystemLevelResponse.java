package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_SystemLevelResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer level;
    
    public SystemConfig_SystemLevelResponse() {
    }

    public SystemConfig_SystemLevelResponse(Integer level) {
        this.level = level;
    }

    public SystemConfig_SystemLevelResponse(Integer code, String msg, Integer level) {
        this.code = code;
        this.msg = msg;
        this.level = level;
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
     * 系统可处理的最高密级文件用户终端。2秘密 3机密 4绝密
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
