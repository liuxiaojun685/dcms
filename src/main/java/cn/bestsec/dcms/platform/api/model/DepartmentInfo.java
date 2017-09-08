package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class DepartmentInfo {
    private String did;
    private String name;
    private String description;
    private String parentName;
    
    public DepartmentInfo() {
    }

    public DepartmentInfo(String did, String name, String description, String parentName) {
        this.did = did;
        this.name = name;
        this.description = description;
        this.parentName = parentName;
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

    /**
     * 部门描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 父部门名称
     */
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
