package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_ConnectTestRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer type;
    
    public SystemConfig_ConnectTestRequest() {
    }

    public SystemConfig_ConnectTestRequest(String token, Integer type) {
        this.token = token;
        this.type = type;
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
     * 1公开文件上传 2内部文件上传 3秘密文件上传 4机密文件上传 5绝密文件上传 6备份 7日志上传 8系统资源告警 9日志存储告警 10文件存储告警 11AD连接 12补丁上传 13配置定密责任人 14配置文件签发人 15配置流程策略
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
