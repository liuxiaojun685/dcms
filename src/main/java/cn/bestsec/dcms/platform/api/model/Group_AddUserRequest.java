package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Group_AddUserRequest extends CommonRequestFieldsSupport {
    private String token;
    private String uid;
    private String gid;
    
    public Group_AddUserRequest() {
    }

    public Group_AddUserRequest(String token, String uid, String gid) {
        this.token = token;
        this.uid = uid;
        this.gid = gid;
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
     * 用户组ID
     */
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
