package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class GroupInfo {
    private String gid;
    private String name;
    private String description;
    private String departmentName;
    private Long createTime;
    
    public GroupInfo() {
    }

    public GroupInfo(String gid, String name, String description, String departmentName, Long createTime) {
        this.gid = gid;
        this.name = name;
        this.description = description;
        this.departmentName = departmentName;
        this.createTime = createTime;
    }

    /**
     * 用户组ID
     */
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    /**
     * 用户组名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 用户组描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 所属部门名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
