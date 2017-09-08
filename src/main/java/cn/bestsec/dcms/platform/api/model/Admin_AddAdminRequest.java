package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Admin_AddAdminRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer adminType;
    private List<String> userIdList;
    
    public Admin_AddAdminRequest() {
    }

    public Admin_AddAdminRequest(String token, Integer adminType, List<String> userIdList) {
        this.token = token;
        this.adminType = adminType;
        this.userIdList = userIdList;
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
     * 管理员类型 1安全审计员 2系统管理员 3安全保密管理员
     */
    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    /**
     * 用户id
     */
    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
