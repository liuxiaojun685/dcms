package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_QueryFileHeaderResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private FileInfo fileInfo;
    
    public Middleware_QueryFileHeaderResponse() {
    }

    public Middleware_QueryFileHeaderResponse(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public Middleware_QueryFileHeaderResponse(Integer code, String msg, FileInfo fileInfo) {
        this.code = code;
        this.msg = msg;
        this.fileInfo = fileInfo;
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
     * 文件密标属性信息
     */
    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
}
