package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_UpdateLevelRequest extends CommonRequestFieldsSupport {
    private String token;
    private String uid;
    private Integer level;
    
    public User_UpdateLevelRequest() {
    }

    public User_UpdateLevelRequest(String token, String uid, Integer level) {
        this.token = token;
        this.uid = uid;
        this.level = level;
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
     * 密级 1一般 2重要 3核心
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
