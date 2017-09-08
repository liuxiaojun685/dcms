package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Log_QueryFileLevelManagementLogRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    private String operateType;
    private Long operateTimeStart;
    private Long operateTimeEnd;
    
    public Log_QueryFileLevelManagementLogRequest() {
    }

    public Log_QueryFileLevelManagementLogRequest(String token, Integer pageNumber, Integer pageSize, String operateType, Long operateTimeStart, Long operateTimeEnd) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.operateType = operateType;
        this.operateTimeStart = operateTimeStart;
        this.operateTimeEnd = operateTimeEnd;
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
     * 操作类型 预定密 正式定密 文件签发 密级变更 文件解密
     */
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
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
}
