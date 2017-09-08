package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class PermissionInfo {
    private Integer contentRead;
    private Integer contentPrint;
    private Integer contentPrintHideWater;
    private Integer contentModify;
    private Integer contentScreenShot;
    private Integer contentCopy;
    private Integer fileCopy;
    private Integer fileSaveCopy;
    private Integer fileAuthorization;
    private Integer fileLevelDecide;
    private Integer fileLevelChange;
    private Integer fileDispatch;
    private Integer fileDecrypt;
    private Integer fileUnbunding;
    
    public PermissionInfo() {
    }

    public PermissionInfo(Integer contentRead, Integer contentPrint, Integer contentPrintHideWater, Integer contentModify, Integer contentScreenShot, Integer contentCopy, Integer fileCopy, Integer fileSaveCopy, Integer fileAuthorization, Integer fileLevelDecide, Integer fileLevelChange, Integer fileDispatch, Integer fileDecrypt, Integer fileUnbunding) {
        this.contentRead = contentRead;
        this.contentPrint = contentPrint;
        this.contentPrintHideWater = contentPrintHideWater;
        this.contentModify = contentModify;
        this.contentScreenShot = contentScreenShot;
        this.contentCopy = contentCopy;
        this.fileCopy = fileCopy;
        this.fileSaveCopy = fileSaveCopy;
        this.fileAuthorization = fileAuthorization;
        this.fileLevelDecide = fileLevelDecide;
        this.fileLevelChange = fileLevelChange;
        this.fileDispatch = fileDispatch;
        this.fileDecrypt = fileDecrypt;
        this.fileUnbunding = fileUnbunding;
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

    /**
     * 是否允许文件授权 1是 0否 2允许提审批
     */
    public Integer getFileAuthorization() {
        return fileAuthorization;
    }

    public void setFileAuthorization(Integer fileAuthorization) {
        this.fileAuthorization = fileAuthorization;
    }

    /**
     * 是否允许文件定密 1是 0否 2允许提审批
     */
    public Integer getFileLevelDecide() {
        return fileLevelDecide;
    }

    public void setFileLevelDecide(Integer fileLevelDecide) {
        this.fileLevelDecide = fileLevelDecide;
    }

    /**
     * 是否允许密级变更 1是 0否 2允许提审批
     */
    public Integer getFileLevelChange() {
        return fileLevelChange;
    }

    public void setFileLevelChange(Integer fileLevelChange) {
        this.fileLevelChange = fileLevelChange;
    }

    /**
     * 是否允许文件签发 1是 0否 2允许提审批
     */
    public Integer getFileDispatch() {
        return fileDispatch;
    }

    public void setFileDispatch(Integer fileDispatch) {
        this.fileDispatch = fileDispatch;
    }

    /**
     * 是否允许文件解密 1是 0否 2允许提审批
     */
    public Integer getFileDecrypt() {
        return fileDecrypt;
    }

    public void setFileDecrypt(Integer fileDecrypt) {
        this.fileDecrypt = fileDecrypt;
    }

    /**
     * 是否允许文件解绑 1是 0否 2允许提审批
     */
    public Integer getFileUnbunding() {
        return fileUnbunding;
    }

    public void setFileUnbunding(Integer fileUnbunding) {
        this.fileUnbunding = fileUnbunding;
    }
}
