package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_ImportRequest extends CommonRequestFieldsSupport {
    private String token;
    private String fileStream;
    
    public User_ImportRequest() {
    }

    public User_ImportRequest(String token, String fileStream) {
        this.token = token;
        this.fileStream = fileStream;
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
     * 文件流(Base64)
     */
    public String getFileStream() {
        return fileStream;
    }

    public void setFileStream(String fileStream) {
        this.fileStream = fileStream;
    }
}
