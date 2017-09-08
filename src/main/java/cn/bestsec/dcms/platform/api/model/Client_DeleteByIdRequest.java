package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_DeleteByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String cid;
    
    public Client_DeleteByIdRequest() {
    }

    public Client_DeleteByIdRequest(String token, String cid) {
        this.token = token;
        this.cid = cid;
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
     * 终端ID
     */
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
