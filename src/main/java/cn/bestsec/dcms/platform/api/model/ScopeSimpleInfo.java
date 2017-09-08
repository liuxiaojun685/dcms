package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ScopeSimpleInfo {
    private String varId;
    private Integer varType;
    
    public ScopeSimpleInfo() {
    }

    public ScopeSimpleInfo(String varId, Integer varType) {
        this.varId = varId;
        this.varType = varType;
    }

    /**
     * 负责的组织ID
     */
    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }

    /**
     * varId类型 1用户ID 2组ID 3部门ID
     */
    public Integer getVarType() {
        return varType;
    }

    public void setVarType(Integer varType) {
        this.varType = varType;
    }
}
