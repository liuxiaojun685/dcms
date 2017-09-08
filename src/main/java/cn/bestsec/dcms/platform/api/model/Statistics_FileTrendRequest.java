package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_FileTrendRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer by;
    private Long startTime;
    private Long endTime;
    
    public Statistics_FileTrendRequest() {
    }

    public Statistics_FileTrendRequest(String token, Integer by, Long startTime, Long endTime) {
        this.token = token;
        this.by = by;
        this.startTime = startTime;
        this.endTime = endTime;
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
     * 1按年 2按月 3按天
     */
    public Integer getBy() {
        return by;
    }

    public void setBy(Integer by) {
        this.by = by;
    }

    /**
     * 开始时间
     */
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 截止时间
     */
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
