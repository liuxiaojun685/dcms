package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_UploadCirculationLogRequest extends CommonRequestFieldsSupport {
    private String mwMac;
    private String mwPasswd;
    
    public Middleware_UploadCirculationLogRequest() {
    }

    public Middleware_UploadCirculationLogRequest(String mwMac, String mwPasswd) {
        this.mwMac = mwMac;
        this.mwPasswd = mwPasswd;
    }

    /**
     * 调用者MAC真实地址 包含冒号
     */
    public String getMwMac() {
        return mwMac;
    }

    public void setMwMac(String mwMac) {
        this.mwMac = mwMac;
    }

    /**
     * 调用接口安全密码
     */
    public String getMwPasswd() {
        return mwPasswd;
    }

    public void setMwPasswd(String mwPasswd) {
        this.mwPasswd = mwPasswd;
    }
}
