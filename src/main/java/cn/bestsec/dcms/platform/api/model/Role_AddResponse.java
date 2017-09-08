package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_AddResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String uid;
    private String account;
    private String name;
    private Integer level;
    private Integer online;
    private Integer roleId;
    private Integer roleType;
    private List<RoleLevelInfo> roleLevelList;
    private String roleDispScope;
    
    public Role_AddResponse() {
    }

    public Role_AddResponse(String uid, String account, String name, Integer level, Integer online, Integer roleId, Integer roleType, List<RoleLevelInfo> roleLevelList, String roleDispScope) {
        this.uid = uid;
        this.account = account;
        this.name = name;
        this.level = level;
        this.online = online;
        this.roleId = roleId;
        this.roleType = roleType;
        this.roleLevelList = roleLevelList;
        this.roleDispScope = roleDispScope;
    }

    public Role_AddResponse(Integer code, String msg, String uid, String account, String name, Integer level, Integer online, Integer roleId, Integer roleType, List<RoleLevelInfo> roleLevelList, String roleDispScope) {
        this.code = code;
        this.msg = msg;
        this.uid = uid;
        this.account = account;
        this.name = name;
        this.level = level;
        this.online = online;
        this.roleId = roleId;
        this.roleType = roleType;
        this.roleLevelList = roleLevelList;
        this.roleDispScope = roleDispScope;
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
     * 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 角色类型 2定密责任人 3文件签发人 7签入人(特权) 8签出人(特权)
     */
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    /**
     * 可负责的文件密级及状态
     */
    public List<RoleLevelInfo> getRoleLevelList() {
        return roleLevelList;
    }

    public void setRoleLevelList(List<RoleLevelInfo> roleLevelList) {
        this.roleLevelList = roleLevelList;
    }

    /**
     * 负责范围简述
     */
    public String getRoleDispScope() {
        return roleDispScope;
    }

    public void setRoleDispScope(String roleDispScope) {
        this.roleDispScope = roleDispScope;
    }
}
