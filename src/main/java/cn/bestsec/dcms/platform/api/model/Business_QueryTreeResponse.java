package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Business_QueryTreeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<BusinessNode> bList;
    
    public Business_QueryTreeResponse() {
    }

    public Business_QueryTreeResponse(List<BusinessNode> bList) {
        this.bList = bList;
    }

    public Business_QueryTreeResponse(Integer code, String msg, List<BusinessNode> bList) {
        this.code = code;
        this.msg = msg;
        this.bList = bList;
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
     * 业务属性列表
     */
    public List<BusinessNode> getBList() {
        return bList;
    }

    public void setBList(List<BusinessNode> bList) {
        this.bList = bList;
    }
}
