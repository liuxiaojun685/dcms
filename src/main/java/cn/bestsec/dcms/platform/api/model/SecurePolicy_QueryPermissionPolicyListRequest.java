package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryPermissionPolicyListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer fileState;
    private Integer fileLevel;
    private Integer roleType;
    
    public SecurePolicy_QueryPermissionPolicyListRequest() {
    }

    public SecurePolicy_QueryPermissionPolicyListRequest(String token, Integer fileState, Integer fileLevel, Integer roleType) {
        this.token = token;
        this.fileState = fileState;
        this.fileLevel = fileLevel;
        this.roleType = roleType;
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
     * 定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }

    /**
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 定密角色 1文件起草人 2定密责任人 3文件签发人 4分发使用人
     */
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
