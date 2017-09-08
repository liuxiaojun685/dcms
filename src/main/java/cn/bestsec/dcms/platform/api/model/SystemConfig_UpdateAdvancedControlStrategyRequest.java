package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateAdvancedControlStrategyRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer prohibitRename;
    private Integer prohibitDelete;
    private Integer prohibitNetwork;
    private Integer prohibitMailSend;
    private Integer prohibitRightSend;
    
    public SystemConfig_UpdateAdvancedControlStrategyRequest() {
    }

    public SystemConfig_UpdateAdvancedControlStrategyRequest(String token, Integer prohibitRename, Integer prohibitDelete, Integer prohibitNetwork, Integer prohibitMailSend, Integer prohibitRightSend) {
        this.token = token;
        this.prohibitRename = prohibitRename;
        this.prohibitDelete = prohibitDelete;
        this.prohibitNetwork = prohibitNetwork;
        this.prohibitMailSend = prohibitMailSend;
        this.prohibitRightSend = prohibitRightSend;
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
     * 是否禁止改名 1是 0否
     */
    public Integer getProhibitRename() {
        return prohibitRename;
    }

    public void setProhibitRename(Integer prohibitRename) {
        this.prohibitRename = prohibitRename;
    }

    /**
     * 是否禁止删除 1是 0否
     */
    public Integer getProhibitDelete() {
        return prohibitDelete;
    }

    public void setProhibitDelete(Integer prohibitDelete) {
        this.prohibitDelete = prohibitDelete;
    }

    /**
     * 是否禁止网络发送 1是 0否
     */
    public Integer getProhibitNetwork() {
        return prohibitNetwork;
    }

    public void setProhibitNetwork(Integer prohibitNetwork) {
        this.prohibitNetwork = prohibitNetwork;
    }

    /**
     * 是否禁止邮件发送 1是 0否
     */
    public Integer getProhibitMailSend() {
        return prohibitMailSend;
    }

    public void setProhibitMailSend(Integer prohibitMailSend) {
        this.prohibitMailSend = prohibitMailSend;
    }

    /**
     * 是否禁止右键发送 1是 0否
     */
    public Integer getProhibitRightSend() {
        return prohibitRightSend;
    }

    public void setProhibitRightSend(Integer prohibitRightSend) {
        this.prohibitRightSend = prohibitRightSend;
    }
}
