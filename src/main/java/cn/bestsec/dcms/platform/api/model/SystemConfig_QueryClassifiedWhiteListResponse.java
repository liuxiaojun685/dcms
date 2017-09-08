package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryClassifiedWhiteListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String classifiedWhiteList;
    
    public SystemConfig_QueryClassifiedWhiteListResponse() {
    }

    public SystemConfig_QueryClassifiedWhiteListResponse(String classifiedWhiteList) {
        this.classifiedWhiteList = classifiedWhiteList;
    }

    public SystemConfig_QueryClassifiedWhiteListResponse(Integer code, String msg, String classifiedWhiteList) {
        this.code = code;
        this.msg = msg;
        this.classifiedWhiteList = classifiedWhiteList;
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
     * 标密白名单 |分割
     */
    public String getClassifiedWhiteList() {
        return classifiedWhiteList;
    }

    public void setClassifiedWhiteList(String classifiedWhiteList) {
        this.classifiedWhiteList = classifiedWhiteList;
    }
}
