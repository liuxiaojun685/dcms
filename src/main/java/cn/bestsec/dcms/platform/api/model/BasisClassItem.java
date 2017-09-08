package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BasisClassItem {
    private Integer basisId;
    private String basisClass;
    
    public BasisClassItem() {
    }

    public BasisClassItem(Integer basisId, String basisClass) {
        this.basisId = basisId;
        this.basisClass = basisClass;
    }

    /**
     * 定密依据分类ID
     */
    public Integer getBasisId() {
        return basisId;
    }

    public void setBasisId(Integer basisId) {
        this.basisId = basisId;
    }

    /**
     * 定密依据分类
     */
    public String getBasisClass() {
        return basisClass;
    }

    public void setBasisClass(String basisClass) {
        this.basisClass = basisClass;
    }
}
