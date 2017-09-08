package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class UninstallPasswdInfo {
    private Integer passwdId;
    private String passwd;
    private Long createTime;
    private String description;
    private Integer syncNum;
    private Integer unsyncNum;
    
    public UninstallPasswdInfo() {
    }

    public UninstallPasswdInfo(Integer passwdId, String passwd, Long createTime, String description, Integer syncNum, Integer unsyncNum) {
        this.passwdId = passwdId;
        this.passwd = passwd;
        this.createTime = createTime;
        this.description = description;
        this.syncNum = syncNum;
        this.unsyncNum = unsyncNum;
    }

    /**
     * 卸载密码ID
     */
    public Integer getPasswdId() {
        return passwdId;
    }

    public void setPasswdId(Integer passwdId) {
        this.passwdId = passwdId;
    }

    /**
     * 卸载密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 已被同步的客户端数
     */
    public Integer getSyncNum() {
        return syncNum;
    }

    public void setSyncNum(Integer syncNum) {
        this.syncNum = syncNum;
    }

    /**
     * 未被同步的客户端数
     */
    public Integer getUnsyncNum() {
        return unsyncNum;
    }

    public void setUnsyncNum(Integer unsyncNum) {
        this.unsyncNum = unsyncNum;
    }
}
