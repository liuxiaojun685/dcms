package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_ApprovalRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowId;
    private Integer approveLevel;
    private String approveValidPeriod;
    private Integer durationType;
    private String durationDescription;
    private Long fileDecryptTime;
    private List<FileScopeItem> fileScope;
    private String fileScopeDesc;
    private List<BasisInfo> approveBasis;
    private Integer basisType;
    private String basisDesc;
    private String issueNumber;
    private Integer docNumber;
    private Integer duplicationAmount;
    private UnitInfo majorUnit;
    private List<UnitInfo> approveMinorUnit;
    private String approveComment;
    private Integer approveState;
    private PermissionBaseInfo permission;
    private Integer markVersion;
    private Integer urgency;
    private List<Integer> approverByStep;
    private String business;
    
    public WorkFlow_ApprovalRequest() {
    }

    public WorkFlow_ApprovalRequest(String token, Integer workFlowId, Integer approveLevel, String approveValidPeriod, Integer durationType, String durationDescription, Long fileDecryptTime, List<FileScopeItem> fileScope, String fileScopeDesc, List<BasisInfo> approveBasis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, UnitInfo majorUnit, List<UnitInfo> approveMinorUnit, String approveComment, Integer approveState, PermissionBaseInfo permission, Integer markVersion, Integer urgency, List<Integer> approverByStep, String business) {
        this.token = token;
        this.workFlowId = workFlowId;
        this.approveLevel = approveLevel;
        this.approveValidPeriod = approveValidPeriod;
        this.durationType = durationType;
        this.durationDescription = durationDescription;
        this.fileDecryptTime = fileDecryptTime;
        this.fileScope = fileScope;
        this.fileScopeDesc = fileScopeDesc;
        this.approveBasis = approveBasis;
        this.basisType = basisType;
        this.basisDesc = basisDesc;
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.majorUnit = majorUnit;
        this.approveMinorUnit = approveMinorUnit;
        this.approveComment = approveComment;
        this.approveState = approveState;
        this.permission = permission;
        this.markVersion = markVersion;
        this.urgency = urgency;
        this.approverByStep = approverByStep;
        this.business = business;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
     * 审批的文件密级
     */
    public Integer getApproveLevel() {
        return approveLevel;
    }

    public void setApproveLevel(Integer approveLevel) {
        this.approveLevel = approveLevel;
    }

    /**
     * 审批的保密期限 格式yymmdd
     */
    public String getApproveValidPeriod() {
        return approveValidPeriod;
    }

    public void setApproveValidPeriod(String approveValidPeriod) {
        this.approveValidPeriod = approveValidPeriod;
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
    public List<FileScopeItem> getFileScope() {
        return fileScope;
    }

    public void setFileScope(List<FileScopeItem> fileScope) {
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
     * 审批的定密依据
     */
    public List<BasisInfo> getApproveBasis() {
        return approveBasis;
    }

    public void setApproveBasis(List<BasisInfo> approveBasis) {
        this.approveBasis = approveBasis;
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
     * 审批的辅助定密单位
     */
    public List<UnitInfo> getApproveMinorUnit() {
        return approveMinorUnit;
    }

    public void setApproveMinorUnit(List<UnitInfo> approveMinorUnit) {
        this.approveMinorUnit = approveMinorUnit;
    }

    /**
     * 审批意见
     */
    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment;
    }

    /**
     * 审批状态 1通过 2否决
     */
    public Integer getApproveState() {
        return approveState;
    }

    public void setApproveState(Integer approveState) {
        this.approveState = approveState;
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
     * 文件重要紧急程度
     */
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    /**
     * 下一级审批人角色id
     */
    public List<Integer> getApproverByStep() {
        return approverByStep;
    }

    public void setApproverByStep(List<Integer> approverByStep) {
        this.approverByStep = approverByStep;
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
