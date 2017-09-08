package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class DepartmentSimpleInfo {
    private String did;
    private String name;
    
    public DepartmentSimpleInfo() {
    }

    public DepartmentSimpleInfo(String did, String name) {
        this.did = did;
        this.name = name;
    }

    /**
     * 部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    /**
     * 部门名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
