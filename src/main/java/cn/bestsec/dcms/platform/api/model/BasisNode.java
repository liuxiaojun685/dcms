package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BasisNode {
    private String basisName;
    private List<BasisItemNode> basisItemList;
    
    public BasisNode() {
    }

    public BasisNode(String basisName, List<BasisItemNode> basisItemList) {
        this.basisName = basisName;
        this.basisItemList = basisItemList;
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

    /**
     * 定密依据事项列表
     */
    public List<BasisItemNode> getBasisItemList() {
        return basisItemList;
    }

    public void setBasisItemList(List<BasisItemNode> basisItemList) {
        this.basisItemList = basisItemList;
    }
}
