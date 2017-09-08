package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Middleware_QueryFileHeaderRequest extends CommonRequestFieldsSupport {
    private String mwMac;
    private String mwPasswd;
    private Integer fileTransType;
    private String fid;
    private String shareAddr;
    private String shareAccount;
    private String sharePasswd;
    private String sharePath;
    private String url;
    
    public Middleware_QueryFileHeaderRequest() {
    }

    public Middleware_QueryFileHeaderRequest(String mwMac, String mwPasswd, Integer fileTransType, String fid, String shareAddr, String shareAccount, String sharePasswd, String sharePath, String url) {
        this.mwMac = mwMac;
        this.mwPasswd = mwPasswd;
        this.fileTransType = fileTransType;
        this.fid = fid;
        this.shareAddr = shareAddr;
        this.shareAccount = shareAccount;
        this.sharePasswd = sharePasswd;
        this.sharePath = sharePath;
        this.url = url;
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

    /**
     * 文件传输类型 默认0 0通过fid免传输文件 1通过http上传文件 2通过共享目录方式上传文件 3URL下载
     */
    public Integer getFileTransType() {
        return fileTransType;
    }

    public void setFileTransType(Integer fileTransType) {
        this.fileTransType = fileTransType;
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

    /**
     * 共享目录地址
     */
    public String getShareAddr() {
        return shareAddr;
    }

    public void setShareAddr(String shareAddr) {
        this.shareAddr = shareAddr;
    }

    /**
     * 共享目录访问账号
     */
    public String getShareAccount() {
        return shareAccount;
    }

    public void setShareAccount(String shareAccount) {
        this.shareAccount = shareAccount;
    }

    /**
     * 共享目录访问密码
     */
    public String getSharePasswd() {
        return sharePasswd;
    }

    public void setSharePasswd(String sharePasswd) {
        this.sharePasswd = sharePasswd;
    }

    /**
     * 文件路径，共享目录根的相对路径，文件名带后缀
     */
    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    /**
     * URL下载路径
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
