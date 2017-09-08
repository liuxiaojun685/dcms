package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class AdminRole {
    private String aid;
    private String uid;
    private String account;
    private String name;
    private String parentAdmin;
    
    public AdminRole() {
    }

    public AdminRole(String aid, String uid, String account, String name, String parentAdmin) {
        this.aid = aid;
        this.uid = uid;
        this.account = account;
        this.name = name;
        this.parentAdmin = parentAdmin;
    }

    /**
     * 管理员id
     */
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    /**
     * 用户ID
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 登录名
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 姓名
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 派生人
     */
    public String getParentAdmin() {
        return parentAdmin;
    }

    public void setParentAdmin(String parentAdmin) {
        this.parentAdmin = parentAdmin;
    }
}
