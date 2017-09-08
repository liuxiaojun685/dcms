package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileScopeItem {
    private String unitNo;
    private String uid;
    
    public FileScopeItem() {
    }

    public FileScopeItem(String unitNo, String uid) {
        this.unitNo = unitNo;
        this.uid = uid;
    }

    /**
     * 定密单位编号
     */
    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    /**
     * 用户ID
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
