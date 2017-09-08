package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class RoleLevelInfo {
    private Integer roleLevel;
    private Integer enable;
    private Integer care;
    
    public RoleLevelInfo() {
    }

    public RoleLevelInfo(Integer roleLevel, Integer enable, Integer care) {
        this.roleLevel = roleLevel;
        this.enable = enable;
        this.care = care;
    }

    /**
     * 文件密级
     */
    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    /**
     * 是否负责该文件密级
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 是否可以负责此密级
     */
    public Integer getCare() {
        return care;
    }

    public void setCare(Integer care) {
        this.care = care;
    }
}
