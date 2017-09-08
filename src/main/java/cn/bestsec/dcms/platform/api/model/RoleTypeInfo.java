package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class RoleTypeInfo {
    private Integer roleType;
    private List<FileLevelInfo> fileLevelList;
    
    public RoleTypeInfo() {
    }

    public RoleTypeInfo(Integer roleType, List<FileLevelInfo> fileLevelList) {
        this.roleType = roleType;
        this.fileLevelList = fileLevelList;
    }

    /**
     * 角色类型 2定密责任人 3文件签发人 7签入人(特权) 8签出人(特权)
     */
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    /**
     * 按可操作的文件密级分组
     */
    public List<FileLevelInfo> getFileLevelList() {
        return fileLevelList;
    }

    public void setFileLevelList(List<FileLevelInfo> fileLevelList) {
        this.fileLevelList = fileLevelList;
    }
}
