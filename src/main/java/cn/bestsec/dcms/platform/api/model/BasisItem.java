package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BasisItem {
    private Integer basisId;
    private String basisName;
    
    public BasisItem() {
    }

    public BasisItem(Integer basisId, String basisName) {
        this.basisId = basisId;
        this.basisName = basisName;
    }

    /**
     * 定密依据ID
     */
    public Integer getBasisId() {
        return basisId;
    }

    public void setBasisId(Integer basisId) {
        this.basisId = basisId;
    }

    /**
     * 定密依据依据名称
     */
    public String getBasisName() {
        return basisName;
    }

    public void setBasisName(String basisName) {
        this.basisName = basisName;
    }
}
