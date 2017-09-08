package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class AdminInfo {
    private String aid;
    private String account;
    private String name;
    private Integer adminType;
    private String description;
    private Integer createFrom;
    
    public AdminInfo() {
    }

    public AdminInfo(String aid, String account, String name, Integer adminType, String description, Integer createFrom) {
        this.aid = aid;
        this.account = account;
        this.name = name;
        this.adminType = adminType;
        this.description = description;
        this.createFrom = createFrom;
    }

    /**
     * 登录名
     */
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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
     * 显示名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 管理员类型。1安全审计员 2系统管理员 3安全保密管理员
     */
    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
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
     * 来源 1内置 2派生
     */
    public Integer getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(Integer createFrom) {
        this.createFrom = createFrom;
    }
}
