package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_LoadPolicyRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer policyType;
    private Integer policyId;
    
    public Client_LoadPolicyRequest() {
    }

    public Client_LoadPolicyRequest(String token, Integer policyType, Integer policyId) {
        this.token = token;
        this.policyType = policyType;
        this.policyId = policyId;
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
     * 策略类型 0全部 1可信应用策略 2可访问文件策略 3密标密钥 4可标密的文件拓展名策略 5文件密级访问策略 6其他策略 7标密白名单
     */
    public Integer getPolicyType() {
        return policyType;
    }

    public void setPolicyType(Integer policyType) {
        this.policyType = policyType;
    }

    /**
     * 策略ID 预留
     */
    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }
}
