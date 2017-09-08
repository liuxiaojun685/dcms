package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryExistByIdResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer exist;
    private Integer outSync;
    
    public File_QueryExistByIdResponse() {
    }

    public File_QueryExistByIdResponse(Integer exist, Integer outSync) {
        this.exist = exist;
        this.outSync = outSync;
    }

    public File_QueryExistByIdResponse(Integer code, String msg, Integer exist, Integer outSync) {
        this.code = code;
        this.msg = msg;
        this.exist = exist;
        this.outSync = outSync;
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
     * 是否存在 1是 0否
     */
    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    /**
     * 是否不同步 1是 0否
     */
    public Integer getOutSync() {
        return outSync;
    }

    public void setOutSync(Integer outSync) {
        this.outSync = outSync;
    }
}
