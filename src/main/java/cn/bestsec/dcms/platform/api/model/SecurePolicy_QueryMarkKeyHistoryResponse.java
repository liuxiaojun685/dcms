package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_QueryMarkKeyHistoryResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<MarkKeyHistoryItem> markKeyHistoryList;
    
    public SecurePolicy_QueryMarkKeyHistoryResponse() {
    }

    public SecurePolicy_QueryMarkKeyHistoryResponse(List<MarkKeyHistoryItem> markKeyHistoryList) {
        this.markKeyHistoryList = markKeyHistoryList;
    }

    public SecurePolicy_QueryMarkKeyHistoryResponse(Integer code, String msg, List<MarkKeyHistoryItem> markKeyHistoryList) {
        this.code = code;
        this.msg = msg;
        this.markKeyHistoryList = markKeyHistoryList;
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
    public List<MarkKeyHistoryItem> getMarkKeyHistoryList() {
        return markKeyHistoryList;
    }

    public void setMarkKeyHistoryList(List<MarkKeyHistoryItem> markKeyHistoryList) {
        this.markKeyHistoryList = markKeyHistoryList;
    }
}
