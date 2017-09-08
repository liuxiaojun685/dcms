package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BasisItemItem {
    private Integer basisId;
    private Integer basisLevel;
    private String basisItem;
    
    public BasisItemItem() {
    }

    public BasisItemItem(Integer basisId, Integer basisLevel, String basisItem) {
        this.basisId = basisId;
        this.basisLevel = basisLevel;
        this.basisItem = basisItem;
    }

    /**
     * 定密依据事项ID
     */
    public Integer getBasisId() {
        return basisId;
    }

    public void setBasisId(Integer basisId) {
        this.basisId = basisId;
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
     * 定密依据事项
     */
    public String getBasisItem() {
        return basisItem;
    }

    public void setBasisItem(String basisItem) {
        this.basisItem = basisItem;
    }
}
