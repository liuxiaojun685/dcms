package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BasisClassNode {
    private String basisClass;
    private List<BasisNode> basisList;
    
    public BasisClassNode() {
    }

    public BasisClassNode(String basisClass, List<BasisNode> basisList) {
        this.basisClass = basisClass;
        this.basisList = basisList;
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

    /**
     * 定密依据依据列表
     */
    public List<BasisNode> getBasisList() {
        return basisList;
    }

    public void setBasisList(List<BasisNode> basisList) {
        this.basisList = basisList;
    }
}
