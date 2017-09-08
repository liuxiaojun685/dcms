package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_FileManyTrendRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer by;
    private Long startTime;
    private Long endTime;
    private Integer fileLevel;
    private List<String> scope;
    
    public Statistics_FileManyTrendRequest() {
    }

    public Statistics_FileManyTrendRequest(String token, Integer by, Long startTime, Long endTime, Integer fileLevel, List<String> scope) {
        this.token = token;
        this.by = by;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fileLevel = fileLevel;
        this.scope = scope;
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
     * 统计范围(部门或用户)
     */
    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
