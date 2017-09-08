package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryMarkKeyListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<MarkKeyItem> markKeyList;
    
    public SecurePolicy_QueryMarkKeyListResponse() {
    }

    public SecurePolicy_QueryMarkKeyListResponse(List<MarkKeyItem> markKeyList) {
        this.markKeyList = markKeyList;
    }

    public SecurePolicy_QueryMarkKeyListResponse(Integer code, String msg, List<MarkKeyItem> markKeyList) {
        this.code = code;
        this.msg = msg;
        this.markKeyList = markKeyList;
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
     * 密钥列表
     */
    public List<MarkKeyItem> getMarkKeyList() {
        return markKeyList;
    }

    public void setMarkKeyList(List<MarkKeyItem> markKeyList) {
        this.markKeyList = markKeyList;
    }
}
