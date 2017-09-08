package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Log_QueryClientLogRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    private List<String> operateTypes;
    private String keyword;
    private Long operateTimeStart;
    private Long operateTimeEnd;
    private Integer riskLevel;
    
    public Log_QueryClientLogRequest() {
    }

    public Log_QueryClientLogRequest(String token, Integer pageNumber, Integer pageSize, List<String> operateTypes, String keyword, Long operateTimeStart, Long operateTimeEnd, Integer riskLevel) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.operateTypes = operateTypes;
        this.keyword = keyword;
        this.operateTimeStart = operateTimeStart;
        this.operateTimeEnd = operateTimeEnd;
        this.riskLevel = riskLevel;
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
     * 页号
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 行数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 操作类型
     */
    public List<String> getOperateTypes() {
        return operateTypes;
    }

    public void setOperateTypes(List<String> operateTypes) {
        this.operateTypes = operateTypes;
    }

    /**
     * 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 操作时间起始
     */
    public Long getOperateTimeStart() {
        return operateTimeStart;
    }

    public void setOperateTimeStart(Long operateTimeStart) {
        this.operateTimeStart = operateTimeStart;
    }

    /**
     * 操作时间截止
     */
    public Long getOperateTimeEnd() {
        return operateTimeEnd;
    }

    public void setOperateTimeEnd(Long operateTimeEnd) {
        this.operateTimeEnd = operateTimeEnd;
    }

    /**
     * 重要程度 1一般 2重要 3严重
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }
}
