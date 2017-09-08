package cn.bestsec.dcms.platform.dao.entity;

import java.util.List;

public class DepartmentInLevel {

    private List<Integer> fileLevels;
    private String VarId;

    public DepartmentInLevel() {
        super();
    }

    public DepartmentInLevel(List<Integer> fileLevels, String varId) {
        super();
        this.fileLevels = fileLevels;
        VarId = varId;
    }

    public List<Integer> getFileLevels() {
        return fileLevels;
    }

    public void setFileLevels(List<Integer> fileLevels) {
        this.fileLevels = fileLevels;
    }

    public String getVarId() {
        return VarId;
    }

    public void setVarId(String varId) {
        VarId = varId;
    }

}
