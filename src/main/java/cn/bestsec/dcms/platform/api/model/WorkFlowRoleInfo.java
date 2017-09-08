package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlowRoleInfo {
    private List<RoleSimpleInfo> roleList;
    private Integer step;
    
    public WorkFlowRoleInfo() {
    }

    public WorkFlowRoleInfo(List<RoleSimpleInfo> roleList, Integer step) {
        this.roleList = roleList;
        this.step = step;
    }

    /**
     * 流程策略ID
     */
    public List<RoleSimpleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleSimpleInfo> roleList) {
        this.roleList = roleList;
    }

    /**
     * 审批级数
     */
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
