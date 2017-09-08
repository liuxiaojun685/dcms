package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_ApplyNewFidResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String fid;
    
    public File_ApplyNewFidResponse() {
    }

    public File_ApplyNewFidResponse(String fid) {
        this.fid = fid;
    }

    public File_ApplyNewFidResponse(Integer code, String msg, String fid) {
        this.code = code;
        this.msg = msg;
        this.fid = fid;
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
     * 文件ID
     */
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
