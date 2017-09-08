package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileLevelInfo {
    private Integer fileLevel;
    private List<RoleSimpleInfo> userList;
    
    public FileLevelInfo() {
    }

    public FileLevelInfo(Integer fileLevel, List<RoleSimpleInfo> userList) {
        this.fileLevel = fileLevel;
        this.userList = userList;
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
     * 用户简要信息列表
     */
    public List<RoleSimpleInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<RoleSimpleInfo> userList) {
        this.userList = userList;
    }
}
