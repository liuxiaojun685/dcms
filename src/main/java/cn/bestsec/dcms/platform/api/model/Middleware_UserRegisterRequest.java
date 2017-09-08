package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_UserRegisterRequest extends CommonRequestFieldsSupport {
    private String mwMac;
    private String mwPasswd;
    private String account;
    private String name;
    private String passwd;
    private Integer gender;
    private String phone;
    private String mail;
    private String position;
    private Integer level;
    private String did;
    private String uid;
    
    public Middleware_UserRegisterRequest() {
    }

    public Middleware_UserRegisterRequest(String mwMac, String mwPasswd, String account, String name, String passwd, Integer gender, String phone, String mail, String position, Integer level, String did, String uid) {
        this.mwMac = mwMac;
        this.mwPasswd = mwPasswd;
        this.account = account;
        this.name = name;
        this.passwd = passwd;
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.position = position;
        this.level = level;
        this.did = did;
        this.uid = uid;
    }

    /**
     * 调用者MAC真实地址 包含冒号
     */
    public String getMwMac() {
        return mwMac;
    }

    public void setMwMac(String mwMac) {
        this.mwMac = mwMac;
    }

    /**
     * 调用接口安全密码
     */
    public String getMwPasswd() {
        return mwPasswd;
    }

    public void setMwPasswd(String mwPasswd) {
        this.mwPasswd = mwPasswd;
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
     * 密码
     */
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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
     * 密级 1一般 2重要 3核心
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
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
}