package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_DeleteInBatchesRequest extends CommonRequestFieldsSupport {
    private String token;
    private List<String> cidList;
    
    public Client_DeleteInBatchesRequest() {
    }

    public Client_DeleteInBatchesRequest(String token, List<String> cidList) {
        this.token = token;
        this.cidList = cidList;
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
     * 终端ID数组
     */
    public List<String> getCidList() {
        return cidList;
    }

    public void setCidList(List<String> cidList) {
        this.cidList = cidList;
    }
}
