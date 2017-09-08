package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class GroupSimpleInfo {
    private String gid;
    private String name;
    
    public GroupSimpleInfo() {
    }

    public GroupSimpleInfo(String gid, String name) {
        this.gid = gid;
        this.name = name;
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
}
