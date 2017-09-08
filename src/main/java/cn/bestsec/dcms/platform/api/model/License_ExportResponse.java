package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class License_ExportResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String source;
    
    public License_ExportResponse() {
    }

    public License_ExportResponse(String source) {
        this.source = source;
    }

    public License_ExportResponse(Integer code, String msg, String source) {
        this.code = code;
        this.msg = msg;
        this.source = source;
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
     * 授权源
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
