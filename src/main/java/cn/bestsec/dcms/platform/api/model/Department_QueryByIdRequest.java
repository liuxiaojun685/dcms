package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Department_QueryByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private String did;
    
    public Department_QueryByIdRequest() {
    }

    public Department_QueryByIdRequest(String token, String did) {
        this.token = token;
        this.did = did;
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
     * 部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
