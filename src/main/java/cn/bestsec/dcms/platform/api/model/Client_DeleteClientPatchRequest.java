package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_DeleteClientPatchRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer patchId;
    
    public Client_DeleteClientPatchRequest() {
    }

    public Client_DeleteClientPatchRequest(String token, Integer patchId) {
        this.token = token;
        this.patchId = patchId;
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
     * 补丁ID
     */
    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }
}
