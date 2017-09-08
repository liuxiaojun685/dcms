package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class MarkKeyInfo {
    private String pubKey;
    private String priKey;
    private Integer markVersion;
    
    public MarkKeyInfo() {
    }

    public MarkKeyInfo(String pubKey, String priKey, Integer markVersion) {
        this.pubKey = pubKey;
        this.priKey = priKey;
        this.markVersion = markVersion;
    }

    /**
     * 公钥
     */
    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    /**
     * 私钥
     */
    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    /**
     * 标志版本
     */
    public Integer getMarkVersion() {
        return markVersion;
    }

    public void setMarkVersion(Integer markVersion) {
        this.markVersion = markVersion;
    }
}
