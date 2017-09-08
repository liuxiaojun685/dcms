package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryExistByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String fid;
    private String fileMd5;
    
    public File_QueryExistByIdRequest() {
    }

    public File_QueryExistByIdRequest(String token, String fid, String fileMd5) {
        this.token = token;
        this.fid = fid;
        this.fileMd5 = fileMd5;
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
     * 文件MD5
     */
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
}
