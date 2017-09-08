package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Group_AddUserInBatchesRequest extends CommonRequestFieldsSupport {
    private String token;
    private List<String> uidList;
    private String gid;
    
    public Group_AddUserInBatchesRequest() {
    }

    public Group_AddUserInBatchesRequest(String token, List<String> uidList, String gid) {
        this.token = token;
        this.uidList = uidList;
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
     * 用户ID数组
     */
    public List<String> getUidList() {
        return uidList;
    }

    public void setUidList(List<String> uidList) {
        this.uidList = uidList;
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
