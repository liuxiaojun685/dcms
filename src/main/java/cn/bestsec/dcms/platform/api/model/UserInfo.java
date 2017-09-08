package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class UserInfo {
    private String uid;
    private String account;
    private String name;
    private Integer level;
    private Integer online;
    private Integer gender;
    private String phone;
    private String mail;
    private String position;
    private Integer state;
    private Integer passwdState;
    private Integer createFrom;
    private Long createTime;
    private Long firstLoginTime;
    private Long lastLoginTime;
    private Integer lastLoginType;
    private String lastLoginAddress;
    private String departmentName;
    private Integer adminType;
    private Integer isHaveRole;
    
    public UserInfo() {
    }

    public UserInfo(String uid, String account, String name, Integer level, Integer online, Integer gender, String phone, String mail, String position, Integer state, Integer passwdState, Integer createFrom, Long createTime, Long firstLoginTime, Long lastLoginTime, Integer lastLoginType, String lastLoginAddress, String departmentName, Integer adminType, Integer isHaveRole) {
        this.uid = uid;
        this.account = account;
        this.name = name;
        this.level = level;
        this.online = online;
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.position = position;
        this.state = state;
        this.passwdState = passwdState;
        this.createFrom = createFrom;
        this.createTime = createTime;
        this.firstLoginTime = firstLoginTime;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginType = lastLoginType;
        this.lastLoginAddress = lastLoginAddress;
        this.departmentName = departmentName;
        this.adminType = adminType;
        this.isHaveRole = isHaveRole;
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
     * 密级 1一般 2重要 3核心
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 在线状态 1在线 2离线
     */
    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    /**
     * 性别 1男 2女
     */
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 电话
     */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 邮箱
     */
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 职位
     */
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 用户状态 1已删除 4已锁定
     */
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 密码状态 1初始未修改 2已修改
     */
    public Integer getPasswdState() {
        return passwdState;
    }

    public void setPasswdState(Integer passwdState) {
        this.passwdState = passwdState;
    }

    /**
     * 创建方式 1手动创建 2导入创建 3同步创建
     */
    public Integer getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(Integer createFrom) {
        this.createFrom = createFrom;
    }

    /**
     * 创建时间(UTC)
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 首次登陆时间(UTC)
     */
    public Long getFirstLoginTime() {
        return firstLoginTime;
    }

    public void setFirstLoginTime(Long firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    /**
     * 末次登陆时间(UTC)
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 末次登陆方式 1WEB平台 2终端
     */
    public Integer getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(Integer lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    /**
     * 末次登陆地址
     */
    public String getLastLoginAddress() {
        return lastLoginAddress;
    }

    public void setLastLoginAddress(String lastLoginAddress) {
        this.lastLoginAddress = lastLoginAddress;
    }

    /**
     * 所属部门名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 管理员类型 1安全审计员 2系统管理员 3安全保密管理员
     */
    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    /**
     * 用户是否有角色 1是 0否
     */
    public Integer getIsHaveRole() {
        return isHaveRole;
    }

    public void setIsHaveRole(Integer isHaveRole) {
        this.isHaveRole = isHaveRole;
    }
}
