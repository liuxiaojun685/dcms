package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class BusinessNode {
    private Integer bId;
    private String name;
    private List<BusinessNode> bList;
    
    public BusinessNode() {
    }

    public BusinessNode(Integer bId, String name, List<BusinessNode> bList) {
        this.bId = bId;
        this.name = name;
        this.bList = bList;
    }

    /**
     * 业务属性ID
     */
    public Integer getBId() {
        return bId;
    }

    public void setBId(Integer bId) {
        this.bId = bId;
    }

    /**
     * 业务名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 业务属性列表
     */
    public List<BusinessNode> getBList() {
        return bList;
    }

    public void setBList(List<BusinessNode> bList) {
        this.bList = bList;
    }
}
