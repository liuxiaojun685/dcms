package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class MarkKeyItem {
    private String keyId;
    private String keyName;
    private Integer keyLength;
    private Long createTime;
    private Integer createFrom;
    private Integer enable;
    private AdminSimpleInfo admin;
    private Integer markVersion;
    
    public MarkKeyItem() {
    }

    public MarkKeyItem(String keyId, String keyName, Integer keyLength, Long createTime, Integer createFrom, Integer enable, AdminSimpleInfo admin, Integer markVersion) {
        this.keyId = keyId;
        this.keyName = keyName;
        this.keyLength = keyLength;
        this.createTime = createTime;
        this.createFrom = createFrom;
        this.enable = enable;
        this.admin = admin;
        this.markVersion = markVersion;
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
     * 密钥长度
     */
    public Integer getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(Integer keyLength) {
        this.keyLength = keyLength;
    }

    /**
     * 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 来源 0系统预置 1手动创建 2导入创建
     */
    public Integer getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(Integer createFrom) {
        this.createFrom = createFrom;
    }

    /**
     * 密钥是否启用 1是 0否
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 创建人
     */
    public AdminSimpleInfo getAdmin() {
        return admin;
    }

    public void setAdmin(AdminSimpleInfo admin) {
        this.admin = admin;
    }

    /**
     * 密标密钥版本
     */
    public Integer getMarkVersion() {
        return markVersion;
    }

    public void setMarkVersion(Integer markVersion) {
        this.markVersion = markVersion;
    }
}
