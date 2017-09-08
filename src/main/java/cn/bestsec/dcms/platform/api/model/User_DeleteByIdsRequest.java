package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_DeleteByIdsRequest extends CommonRequestFieldsSupport {
    private String token;
    private List<String> uids;
    
    public User_DeleteByIdsRequest() {
    }

    public User_DeleteByIdsRequest(String token, List<String> uids) {
        this.token = token;
        this.uids = uids;
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
    public List<String> getUids() {
        return uids;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }
}
