package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_UpdateBaseRequest extends CommonRequestFieldsSupport {
    private String token;
    private String uid;
    private String name;
    private Integer gender;
    private String phone;
    private String mail;
    private String position;
    private String did;
    
    public User_UpdateBaseRequest() {
    }

    public User_UpdateBaseRequest(String token, String uid, String name, Integer gender, String phone, String mail, String position, String did) {
        this.token = token;
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.position = position;
        this.did = did;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
     * 姓名
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
     * 移动到新部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
