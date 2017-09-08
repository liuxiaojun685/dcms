package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryValidPeriodListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<ValidPeriodInfo> validPeriodList;
    
    public SecurePolicy_QueryValidPeriodListResponse() {
    }

    public SecurePolicy_QueryValidPeriodListResponse(List<ValidPeriodInfo> validPeriodList) {
        this.validPeriodList = validPeriodList;
    }

    public SecurePolicy_QueryValidPeriodListResponse(Integer code, String msg, List<ValidPeriodInfo> validPeriodList) {
        this.code = code;
        this.msg = msg;
        this.validPeriodList = validPeriodList;
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
     * 按文件级别区分
     */
    public List<ValidPeriodInfo> getValidPeriodList() {
        return validPeriodList;
    }

    public void setValidPeriodList(List<ValidPeriodInfo> validPeriodList) {
        this.validPeriodList = validPeriodList;
    }
}
