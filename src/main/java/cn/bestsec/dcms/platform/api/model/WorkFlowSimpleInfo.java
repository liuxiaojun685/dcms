package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlowSimpleInfo {
    private Integer workFlowId;
    private Integer workFlowType;
    private FileSimpleInfo srcFile;
    private FileSimpleInfo desFile;
    private Integer applyFileLevel;
    private Integer flowState;
    private Long createTime;
    private UserSimpleInfo createUser;
    private Integer urgency;
    
    public WorkFlowSimpleInfo() {
    }

    public WorkFlowSimpleInfo(Integer workFlowId, Integer workFlowType, FileSimpleInfo srcFile, FileSimpleInfo desFile, Integer applyFileLevel, Integer flowState, Long createTime, UserSimpleInfo createUser, Integer urgency) {
        this.workFlowId = workFlowId;
        this.workFlowType = workFlowType;
        this.srcFile = srcFile;
        this.desFile = desFile;
        this.applyFileLevel = applyFileLevel;
        this.flowState = flowState;
        this.createTime = createTime;
        this.createUser = createUser;
        this.urgency = urgency;
    }

    /**
     * 流程ID
     */
    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }

    /**
     * 流程名称 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核
     */
    public Integer getWorkFlowType() {
        return workFlowType;
    }

    public void setWorkFlowType(Integer workFlowType) {
        this.workFlowType = workFlowType;
    }

    /**
     * 源文件信息
     */
    public FileSimpleInfo getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(FileSimpleInfo srcFile) {
        this.srcFile = srcFile;
    }

    /**
     * 目标文件信息
     */
    public FileSimpleInfo getDesFile() {
        return desFile;
    }

    public void setDesFile(FileSimpleInfo desFile) {
        this.desFile = desFile;
    }

    /**
     * 申请的文件密级
     */
    public Integer getApplyFileLevel() {
        return applyFileLevel;
    }

    public void setApplyFileLevel(Integer applyFileLevel) {
        this.applyFileLevel = applyFileLevel;
    }

    /**
     * 流程状态 1已完成 0未完成
     */
    public Integer getFlowState() {
        return flowState;
    }

    public void setFlowState(Integer flowState) {
        this.flowState = flowState;
    }

    /**
     * 发起时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 发起人信息
     */
    public UserSimpleInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserSimpleInfo createUser) {
        this.createUser = createUser;
    }

    /**
     * 文件重要紧急程度
     */
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }
}
