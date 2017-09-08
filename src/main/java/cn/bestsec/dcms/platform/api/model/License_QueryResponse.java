package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class License_QueryResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String source;
    private String customId;
    private String customName;
    private Integer state;
    private Integer onlineMax;
    private Integer offlineMax;
    private Integer middlewareMax;
    private Long startTime;
    private Long endTime;
    
    public License_QueryResponse() {
    }

    public License_QueryResponse(String source, String customId, String customName, Integer state, Integer onlineMax, Integer offlineMax, Integer middlewareMax, Long startTime, Long endTime) {
        this.source = source;
        this.customId = customId;
        this.customName = customName;
        this.state = state;
        this.onlineMax = onlineMax;
        this.offlineMax = offlineMax;
        this.middlewareMax = middlewareMax;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public License_QueryResponse(Integer code, String msg, String source, String customId, String customName, Integer state, Integer onlineMax, Integer offlineMax, Integer middlewareMax, Long startTime, Long endTime) {
        this.code = code;
        this.msg = msg;
        this.source = source;
        this.customId = customId;
        this.customName = customName;
        this.state = state;
        this.onlineMax = onlineMax;
        this.offlineMax = offlineMax;
        this.middlewareMax = middlewareMax;
        this.startTime = startTime;
        this.endTime = endTime;
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
     * 授权源
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 客户编号
     */
    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    /**
     * 客户名称
     */
    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * 授权状态 1正式版 2试用版
     */
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 网络版最大个数
     */
    public Integer getOnlineMax() {
        return onlineMax;
    }

    public void setOnlineMax(Integer onlineMax) {
        this.onlineMax = onlineMax;
    }

    /**
     * 单机版最大个数
     */
    public Integer getOfflineMax() {
        return offlineMax;
    }

    public void setOfflineMax(Integer offlineMax) {
        this.offlineMax = offlineMax;
    }

    /**
     * 中间件最大个数
     */
    public Integer getMiddlewareMax() {
        return middlewareMax;
    }

    public void setMiddlewareMax(Integer middlewareMax) {
        this.middlewareMax = middlewareMax;
    }

    /**
     * 导入授权日期
     */
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 授权到期日期
     */
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
