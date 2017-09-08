package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryAdvancedControlStrategyResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer prohibitRename;
    private Integer prohibitDelete;
    private Integer prohibitNetwork;
    private Integer prohibitMailSend;
    private Integer prohibitRightSend;
    
    public SystemConfig_QueryAdvancedControlStrategyResponse() {
    }

    public SystemConfig_QueryAdvancedControlStrategyResponse(Integer prohibitRename, Integer prohibitDelete, Integer prohibitNetwork, Integer prohibitMailSend, Integer prohibitRightSend) {
        this.prohibitRename = prohibitRename;
        this.prohibitDelete = prohibitDelete;
        this.prohibitNetwork = prohibitNetwork;
        this.prohibitMailSend = prohibitMailSend;
        this.prohibitRightSend = prohibitRightSend;
    }

    public SystemConfig_QueryAdvancedControlStrategyResponse(Integer code, String msg, Integer prohibitRename, Integer prohibitDelete, Integer prohibitNetwork, Integer prohibitMailSend, Integer prohibitRightSend) {
        this.code = code;
        this.msg = msg;
        this.prohibitRename = prohibitRename;
        this.prohibitDelete = prohibitDelete;
        this.prohibitNetwork = prohibitNetwork;
        this.prohibitMailSend = prohibitMailSend;
        this.prohibitRightSend = prohibitRightSend;
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
