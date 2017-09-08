package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileSimpleInfo {
    private String fid;
    private String fileName;
    private Integer urgency;
    private Integer fileLevel;
    
    public FileSimpleInfo() {
    }

    public FileSimpleInfo(String fid, String fileName, Integer urgency, Integer fileLevel) {
        this.fid = fid;
        this.fileName = fileName;
        this.urgency = urgency;
        this.fileLevel = fileLevel;
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
     * 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 文件重要紧急程度 0一般 1重要 2加急
     */
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    /**
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }
}
