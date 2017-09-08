package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BasisInfo {
    private Integer basisLevel;
    private String basisContent;
    
    public BasisInfo() {
    }

    public BasisInfo(Integer basisLevel, String basisContent) {
        this.basisLevel = basisLevel;
        this.basisContent = basisContent;
    }

    /**
     * 定密依据密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getBasisLevel() {
        return basisLevel;
    }

    public void setBasisLevel(Integer basisLevel) {
        this.basisLevel = basisLevel;
    }

    /**
     * 定密依据内容
     */
    public String getBasisContent() {
        return basisContent;
    }

    public void setBasisContent(String basisContent) {
        this.basisContent = basisContent;
    }
}
