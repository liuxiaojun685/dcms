package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class PermissionBaseInfo {
    private Integer contentRead;
    private Integer contentPrint;
    private Integer contentPrintHideWater;
    private Integer contentModify;
    private Integer contentScreenShot;
    private Integer contentCopy;
    private Integer fileCopy;
    private Integer fileSaveCopy;
    
    public PermissionBaseInfo() {
    }

    public PermissionBaseInfo(Integer contentRead, Integer contentPrint, Integer contentPrintHideWater, Integer contentModify, Integer contentScreenShot, Integer contentCopy, Integer fileCopy, Integer fileSaveCopy) {
        this.contentRead = contentRead;
        this.contentPrint = contentPrint;
        this.contentPrintHideWater = contentPrintHideWater;
        this.contentModify = contentModify;
        this.contentScreenShot = contentScreenShot;
        this.contentCopy = contentCopy;
        this.fileCopy = fileCopy;
        this.fileSaveCopy = fileSaveCopy;
    }

    /**
     * 是否允许内容阅读 1是 0否
     */
    public Integer getContentRead() {
        return contentRead;
    }

    public void setContentRead(Integer contentRead) {
        this.contentRead = contentRead;
    }

    /**
     * 是否允许内容打印 1是 0否
     */
    public Integer getContentPrint() {
        return contentPrint;
    }

    public void setContentPrint(Integer contentPrint) {
        this.contentPrint = contentPrint;
    }

    /**
     * 是否允许打印隐藏水印 1是 0否
     */
    public Integer getContentPrintHideWater() {
        return contentPrintHideWater;
    }

    public void setContentPrintHideWater(Integer contentPrintHideWater) {
        this.contentPrintHideWater = contentPrintHideWater;
    }

    /**
     * 是否允许内容修改 1是 0否
     */
    public Integer getContentModify() {
        return contentModify;
    }

    public void setContentModify(Integer contentModify) {
        this.contentModify = contentModify;
    }

    /**
     * 是否允许内容截屏 1是 0否
     */
    public Integer getContentScreenShot() {
        return contentScreenShot;
    }

    public void setContentScreenShot(Integer contentScreenShot) {
        this.contentScreenShot = contentScreenShot;
    }

    /**
     * 是否允许内容拷贝 1是 0否
     */
    public Integer getContentCopy() {
        return contentCopy;
    }

    public void setContentCopy(Integer contentCopy) {
        this.contentCopy = contentCopy;
    }

    /**
     * 是否允许文件拷贝 1是 0否
     */
    public Integer getFileCopy() {
        return fileCopy;
    }

    public void setFileCopy(Integer fileCopy) {
        this.fileCopy = fileCopy;
    }

    /**
     * 是否允许文件另存副本 1是 0否
     */
    public Integer getFileSaveCopy() {
        return fileSaveCopy;
    }

    public void setFileSaveCopy(Integer fileSaveCopy) {
        this.fileSaveCopy = fileSaveCopy;
    }
}
