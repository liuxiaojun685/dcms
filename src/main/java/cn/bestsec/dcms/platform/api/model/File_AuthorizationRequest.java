package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_AuthorizationRequest extends CommonRequestFieldsSupport {
    private String token;
    private String fid;
    private List<DRMInfo> drmList;
    
    public File_AuthorizationRequest() {
    }

    public File_AuthorizationRequest(String token, String fid, List<DRMInfo> drmList) {
        this.token = token;
        this.fid = fid;
        this.drmList = drmList;
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
     * 文件ID
     */
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * 授权表
     */
    public List<DRMInfo> getDrmList() {
        return drmList;
    }

    public void setDrmList(List<DRMInfo> drmList) {
        this.drmList = drmList;
    }
}
