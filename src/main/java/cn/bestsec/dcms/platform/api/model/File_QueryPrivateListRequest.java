package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryPrivateListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer filter;
    private Integer fileState;
    private Integer fileLevel;
    private String keyword;
    
    public File_QueryPrivateListRequest() {
    }

    public File_QueryPrivateListRequest(String token, Integer pageNumber, Integer pageSize, Integer filter, Integer fileState, Integer fileLevel, String keyword) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.filter = filter;
        this.fileState = fileState;
        this.fileLevel = fileLevel;
        this.keyword = keyword;
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
     * 1文件收件箱 2拟稿箱 3待解密(定密责任人专用) 4已解密(定密责任人专用) 5负责范围的文件(定密责任人专用)
     */
    public Integer getFilter() {
        return filter;
    }

    public void setFilter(Integer filter) {
        this.filter = filter;
    }

    /**
     * 文件状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
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
     * 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
