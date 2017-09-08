package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_DownloadFileByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String fid;
    
    public File_DownloadFileByIdRequest() {
    }

    public File_DownloadFileByIdRequest(String token, String fid) {
        this.token = token;
        this.fid = fid;
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
}
