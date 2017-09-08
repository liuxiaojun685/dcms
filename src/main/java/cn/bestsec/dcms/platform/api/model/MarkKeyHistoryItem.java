package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class MarkKeyHistoryItem {
    private String keyId;
    private String keyName;
    private Long enableTime;
    private Long disableTime;
    
    public MarkKeyHistoryItem() {
    }

    public MarkKeyHistoryItem(String keyId, String keyName, Long enableTime, Long disableTime) {
        this.keyId = keyId;
        this.keyName = keyName;
        this.enableTime = enableTime;
        this.disableTime = disableTime;
    }

    /**
     * 密钥ID
     */
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    /**
     * 密钥名称
     */
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * 启用时间
     */
    public Long getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Long enableTime) {
        this.enableTime = enableTime;
    }

    /**
     * 禁用时间
     */
    public Long getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(Long disableTime) {
        this.disableTime = disableTime;
    }
}
