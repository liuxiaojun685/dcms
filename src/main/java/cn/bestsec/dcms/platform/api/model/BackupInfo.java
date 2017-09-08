package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BackupInfo {
    private Integer backupId;
    private String name;
    private String description;
    private Long createTime;
    private Integer createFrom;
    private Long size;
    private String location;
    private String md5;
    
    public BackupInfo() {
    }

    public BackupInfo(Integer backupId, String name, String description, Long createTime, Integer createFrom, Long size, String location, String md5) {
        this.backupId = backupId;
        this.name = name;
        this.description = description;
        this.createTime = createTime;
        this.createFrom = createFrom;
        this.size = size;
        this.location = location;
        this.md5 = md5;
    }

    /**
     * 备份历史ID
     */
    public Integer getBackupId() {
        return backupId;
    }

    public void setBackupId(Integer backupId) {
        this.backupId = backupId;
    }

    /**
     * 备份名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 备份描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 备份时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 备份方式 1手动 2自动
     */
    public Integer getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(Integer createFrom) {
        this.createFrom = createFrom;
    }

    /**
     * 备份文件大小 字节
     */
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * 备份位置URL
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 文件MD5
     */
    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
