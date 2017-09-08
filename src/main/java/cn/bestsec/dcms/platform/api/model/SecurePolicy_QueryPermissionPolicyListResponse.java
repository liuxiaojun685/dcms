package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryPermissionPolicyListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<PermissionPolicyInfo> policyList;
    
    public SecurePolicy_QueryPermissionPolicyListResponse() {
    }

    public SecurePolicy_QueryPermissionPolicyListResponse(List<PermissionPolicyInfo> policyList) {
        this.policyList = policyList;
    }

    public SecurePolicy_QueryPermissionPolicyListResponse(Integer code, String msg, List<PermissionPolicyInfo> policyList) {
        this.code = code;
        this.msg = msg;
        this.policyList = policyList;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     */
    public List<PermissionPolicyInfo> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<PermissionPolicyInfo> policyList) {
        this.policyList = policyList;
    }
}
