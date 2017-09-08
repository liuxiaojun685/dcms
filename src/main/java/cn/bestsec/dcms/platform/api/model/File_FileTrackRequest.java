package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_FileTrackRequest extends CommonRequestFieldsSupport {
    private String token;
    private String fid;
    private String name;
    
    public File_FileTrackRequest() {
    }

    public File_FileTrackRequest(String token, String fid, String name) {
        this.token = token;
        this.fid = fid;
        this.name = name;
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
     * 文件名
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
