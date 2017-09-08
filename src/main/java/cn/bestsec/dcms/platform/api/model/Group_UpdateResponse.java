package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Group_UpdateResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String gid;
    
    public Group_UpdateResponse() {
    }

    public Group_UpdateResponse(String gid) {
        this.gid = gid;
    }

    public Group_UpdateResponse(Integer code, String msg, String gid) {
        this.code = code;
        this.msg = msg;
        this.gid = gid;
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
     * 用户组ID
     */
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
