package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class HistoryInfo {
    private Long creatTime;
    private UserSimpleInfo user;
    private String description;
    
    public HistoryInfo() {
    }

    public HistoryInfo(Long creatTime, UserSimpleInfo user, String description) {
        this.creatTime = creatTime;
        this.user = user;
        this.description = description;
    }

    /**
     * 操作日期
     */
    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * 责任人
     */
    public UserSimpleInfo getUser() {
        return user;
    }

    public void setUser(UserSimpleInfo user) {
        this.user = user;
    }

    /**
     * 
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
