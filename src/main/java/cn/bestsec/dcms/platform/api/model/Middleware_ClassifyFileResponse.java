package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_ClassifyFileResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String returnUrl;
    private String fileName;
    
    public Middleware_ClassifyFileResponse() {
    }

    public Middleware_ClassifyFileResponse(String returnUrl, String fileName) {
        this.returnUrl = returnUrl;
        this.fileName = fileName;
    }

    public Middleware_ClassifyFileResponse(Integer code, String msg, String returnUrl, String fileName) {
        this.code = code;
        this.msg = msg;
        this.returnUrl = returnUrl;
        this.fileName = fileName;
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
     * 返回的文件地址，当文件返回类型为0时该字段为http下载地址，客户需要拼接上http://{host}:{port}/，当文件返回类型为1时，该字段为共享目录根的相对路径。<br>目标文件随机命名，客户在下载或拷贝时需自行重命名。
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * 文件名
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
