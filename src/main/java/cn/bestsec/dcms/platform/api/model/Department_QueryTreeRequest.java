package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Department_QueryTreeRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer hasUser;
    private Integer hasGroup;
    private String keyword;
    private Integer fileLevel;
    
    public Department_QueryTreeRequest() {
    }

    public Department_QueryTreeRequest(String token, Integer hasUser, Integer hasGroup, String keyword, Integer fileLevel) {
        this.token = token;
        this.hasUser = hasUser;
        this.hasGroup = hasGroup;
        this.keyword = keyword;
        this.fileLevel = fileLevel;
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
     * 包含用户节点 1包含 0不包含
     */
    public Integer getHasUser() {
        return hasUser;
    }

    public void setHasUser(Integer hasUser) {
        this.hasUser = hasUser;
    }

    /**
     * 包含组节点 1包含 0不包含
     */
    public Integer getHasGroup() {
        return hasGroup;
    }

    public void setHasGroup(Integer hasGroup) {
        this.hasGroup = hasGroup;
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
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密，筛选有访问该密级文件权限的用户
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }
}
