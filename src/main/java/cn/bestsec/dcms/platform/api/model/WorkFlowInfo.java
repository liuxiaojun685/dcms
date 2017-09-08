package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlowInfo {
    private Integer workFlowId;
    private Integer workFlowType;
    private FileSimpleInfo srcFile;
    private FileSimpleInfo desFile;
    private Integer applyFileLevel;
    private Integer flowState;
    private Long createTime;
    private UserSimpleInfo createUser;
    private Integer urgency;
    private String applyValidPeriod;
    private Integer durationType;
    private String durationDescription;
    private Long fileDecryptTime;
    private List<FileScopeInfo> fileScope;
    private String fileScopeDesc;
    private List<BasisInfo> applyBasis;
    private Integer basisType;
    private String basisDesc;
    private String issueNumber;
    private Integer docNumber;
    private Integer duplicationAmount;
    private UnitInfo majorUnit;
    private List<UnitInfo> applyMinorUnit;
    private String createComment;
    private Integer totalStep;
    private Integer currentStep;
    private List<FlowTrackInfo> flowTrack;
    private PermissionBaseInfo permission;
    private Integer markVersion;
    private String business;
    
    public WorkFlowInfo() {
    }

    public WorkFlowInfo(Integer workFlowId, Integer workFlowType, FileSimpleInfo srcFile, FileSimpleInfo desFile, Integer applyFileLevel, Integer flowState, Long createTime, UserSimpleInfo createUser, Integer urgency, String applyValidPeriod, Integer durationType, String durationDescription, Long fileDecryptTime, List<FileScopeInfo> fileScope, String fileScopeDesc, List<BasisInfo> applyBasis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, UnitInfo majorUnit, List<UnitInfo> applyMinorUnit, String createComment, Integer totalStep, Integer currentStep, List<FlowTrackInfo> flowTrack, PermissionBaseInfo permission, Integer markVersion, String business) {
        this.workFlowId = workFlowId;
        this.workFlowType = workFlowType;
        this.srcFile = srcFile;
        this.desFile = desFile;
        this.applyFileLevel = applyFileLevel;
        this.flowState = flowState;
        this.createTime = createTime;
        this.createUser = createUser;
        this.urgency = urgency;
        this.applyValidPeriod = applyValidPeriod;
        this.durationType = durationType;
        this.durationDescription = durationDescription;
        this.fileDecryptTime = fileDecryptTime;
        this.fileScope = fileScope;
        this.fileScopeDesc = fileScopeDesc;
        this.applyBasis = applyBasis;
        this.basisType = basisType;
        this.basisDesc = basisDesc;
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.majorUnit = majorUnit;
        this.applyMinorUnit = applyMinorUnit;
        this.createComment = createComment;
        this.totalStep = totalStep;
        this.currentStep = currentStep;
        this.flowTrack = flowTrack;
        this.permission = permission;
        this.markVersion = markVersion;
        this.business = business;
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

    /**
     * 申请的保密期限 格式yymmdd
     */
    public String getApplyValidPeriod() {
        return applyValidPeriod;
    }

    public void setApplyValidPeriod(String applyValidPeriod) {
        this.applyValidPeriod = applyValidPeriod;
    }

    /**
     * 保密期限 类型 0不限 1长期 2期限 3解密日期 4条件
     */
    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    /**
     * 保密期限 描述
     */
    public String getDurationDescription() {
        return durationDescription;
    }

    public void setDurationDescription(String durationDescription) {
        this.durationDescription = durationDescription;
    }

    /**
     * 文件解密日期
     */
    public Long getFileDecryptTime() {
        return fileDecryptTime;
    }

    public void setFileDecryptTime(Long fileDecryptTime) {
        this.fileDecryptTime = fileDecryptTime;
    }

    /**
     * 分发范围
     */
    public List<FileScopeInfo> getFileScope() {
        return fileScope;
    }

    public void setFileScope(List<FileScopeInfo> fileScope) {
        this.fileScope = fileScope;
    }

    /**
     * 分发范围描述
     */
    public String getFileScopeDesc() {
        return fileScopeDesc;
    }

    public void setFileScopeDesc(String fileScopeDesc) {
        this.fileScopeDesc = fileScopeDesc;
    }

    /**
     * 申请的定密依据
     */
    public List<BasisInfo> getApplyBasis() {
        return applyBasis;
    }

    public void setApplyBasis(List<BasisInfo> applyBasis) {
        this.applyBasis = applyBasis;
    }

    /**
     * 定密依据类型 0确定性定密 1不明确事项 2无权定密事项 3派生定密
     */
    public Integer getBasisType() {
        return basisType;
    }

    public void setBasisType(Integer basisType) {
        this.basisType = basisType;
    }

    /**
     * 定密依据描述
     */
    public String getBasisDesc() {
        return basisDesc;
    }

    public void setBasisDesc(String basisDesc) {
        this.basisDesc = basisDesc;
    }

    /**
     * 文号
     */
    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    /**
     * 份号
     */
    public Integer getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * 签发份数
     */
    public Integer getDuplicationAmount() {
        return duplicationAmount;
    }

    public void setDuplicationAmount(Integer duplicationAmount) {
        this.duplicationAmount = duplicationAmount;
    }

    /**
     * 主定密单位
     */
    public UnitInfo getMajorUnit() {
        return majorUnit;
    }

    public void setMajorUnit(UnitInfo majorUnit) {
        this.majorUnit = majorUnit;
    }

    /**
     * 申请的辅助定密单位
     */
    public List<UnitInfo> getApplyMinorUnit() {
        return applyMinorUnit;
    }

    public void setApplyMinorUnit(List<UnitInfo> applyMinorUnit) {
        this.applyMinorUnit = applyMinorUnit;
    }

    /**
     * 申请理由描述
     */
    public String getCreateComment() {
        return createComment;
    }

    public void setCreateComment(String createComment) {
        this.createComment = createComment;
    }

    /**
     * 审批总级数
     */
    public Integer getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(Integer totalStep) {
        this.totalStep = totalStep;
    }

    /**
     * 审批当前级数
     */
    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * 流程轨迹详情
     */
    public List<FlowTrackInfo> getFlowTrack() {
        return flowTrack;
    }

    public void setFlowTrack(List<FlowTrackInfo> flowTrack) {
        this.flowTrack = flowTrack;
    }

    /**
     * 权限详情
     */
    public PermissionBaseInfo getPermission() {
        return permission;
    }

    public void setPermission(PermissionBaseInfo permission) {
        this.permission = permission;
    }

    /**
     * 密标版本
     */
    public Integer getMarkVersion() {
        return markVersion;
    }

    public void setMarkVersion(Integer markVersion) {
        this.markVersion = markVersion;
    }

    /**
     * 业务属性
     */
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
