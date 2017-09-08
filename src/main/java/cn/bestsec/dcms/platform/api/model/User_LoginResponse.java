package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_LoginResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String token;
    private Integer remainTimes;
    private String uid;
    private String name;
    private Integer level;
    private Integer state;
    private Integer passwdState;
    private Integer passwdForceChange;
    private String cid;
    private String roleType;
    
    public User_LoginResponse() {
    }

    public User_LoginResponse(String token, Integer remainTimes, String uid, String name, Integer level, Integer state, Integer passwdState, Integer passwdForceChange, String cid, String roleType) {
        this.token = token;
        this.remainTimes = remainTimes;
        this.uid = uid;
        this.name = name;
        this.level = level;
        this.state = state;
        this.passwdState = passwdState;
        this.passwdForceChange = passwdForceChange;
        this.cid = cid;
        this.roleType = roleType;
    }

    public User_LoginResponse(Integer code, String msg, String token, Integer remainTimes, String uid, String name, Integer level, Integer state, Integer passwdState, Integer passwdForceChange, String cid, String roleType) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.remainTimes = remainTimes;
        this.uid = uid;
        this.name = name;
        this.level = level;
        this.state = state;
        this.passwdState = passwdState;
        this.passwdForceChange = passwdForceChange;
        this.cid = cid;
        this.roleType = roleType;
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
     * 校验码
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 剩余尝试次数，code=2006时返回且只返回这一个字段。
     */
    public Integer getRemainTimes() {
        return remainTimes;
    }

    public void setRemainTimes(Integer remainTimes) {
        this.remainTimes = remainTimes;
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
     * 用户姓名
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 用户密级 1一般 2重要 3核心
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
     * 是否强制修改密码 1是 0否
     */
    public Integer getPasswdForceChange() {
        return passwdForceChange;
    }

    public void setPasswdForceChange(Integer passwdForceChange) {
        this.passwdForceChange = passwdForceChange;
    }

    /**
     * 终端ID
     */
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * 角色类型 0默认 2定密责任人 3文件签发人 7签入人(特权) 8签出人(特权) 2|3 0
     */
    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
