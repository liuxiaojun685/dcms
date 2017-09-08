package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class GroupNode {
    private String gid;
    private String name;
    private List<UserSimpleInfo> childUserList;
    
    public GroupNode() {
    }

    public GroupNode(String gid, String name, List<UserSimpleInfo> childUserList) {
        this.gid = gid;
        this.name = name;
        this.childUserList = childUserList;
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
     * 子用户姓名数组
     */
    public List<UserSimpleInfo> getChildUserList() {
        return childUserList;
    }

    public void setChildUserList(List<UserSimpleInfo> childUserList) {
        this.childUserList = childUserList;
    }
}
