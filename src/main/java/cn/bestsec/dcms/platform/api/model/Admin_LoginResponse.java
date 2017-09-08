package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_LoginResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String aid;
    private String account;
    private String name;
    private Integer adminType;
    private String description;
    private Integer createFrom;
    private String token;
    
    public Admin_LoginResponse() {
    }

    public Admin_LoginResponse(String aid, String account, String name, Integer adminType, String description, Integer createFrom, String token) {
        this.aid = aid;
        this.account = account;
        this.name = name;
        this.adminType = adminType;
        this.description = description;
        this.createFrom = createFrom;
        this.token = token;
    }

    public Admin_LoginResponse(Integer code, String msg, String aid, String account, String name, Integer adminType, String description, Integer createFrom, String token) {
        this.code = code;
        this.msg = msg;
        this.aid = aid;
        this.account = account;
        this.name = name;
        this.adminType = adminType;
        this.description = description;
        this.createFrom = createFrom;
        this.token = token;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    /**
     * 校验码
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
