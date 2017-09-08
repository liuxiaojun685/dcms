package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ScopeInfo {
    private String varId;
    private Integer varType;
    private String varName;
    
    public ScopeInfo() {
    }

    public ScopeInfo(String varId, Integer varType, String varName) {
        this.varId = varId;
        this.varType = varType;
        this.varName = varName;
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

    /**
     * 负责的组织名称
     */
    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
