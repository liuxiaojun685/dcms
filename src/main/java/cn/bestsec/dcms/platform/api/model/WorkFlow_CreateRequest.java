package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class WorkFlow_CreateRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer workFlowType;
    private String srcFid;
    private Integer fileState;
    private Integer applyFileLevel;
    private String applyValidPeriod;
    private Integer durationType;
    private String durationDescription;
    private Long fileDecryptTime;
    private List<FileScopeItem> fileScope;
    private String fileScopeDesc;
    private List<BasisInfo> applyBasis;
    private Integer basisType;
    private String basisDesc;
    private String issueNumber;
    private Integer docNumber;
    private Integer duplicationAmount;
    private UnitInfo applyMajorUnit;
    private List<UnitInfo> applyMinorUnit;
    private String createComment;
    private List<Integer> approverByStep;
    private PermissionBaseInfo permission;
    private Integer markVersion;
    private Integer urgency;
    private String business;
    
    public WorkFlow_CreateRequest() {
    }

    public WorkFlow_CreateRequest(String token, Integer workFlowType, String srcFid, Integer fileState, Integer applyFileLevel, String applyValidPeriod, Integer durationType, String durationDescription, Long fileDecryptTime, List<FileScopeItem> fileScope, String fileScopeDesc, List<BasisInfo> applyBasis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, UnitInfo applyMajorUnit, List<UnitInfo> applyMinorUnit, String createComment, List<Integer> approverByStep, PermissionBaseInfo permission, Integer markVersion, Integer urgency, String business) {
        this.token = token;
        this.workFlowType = workFlowType;
        this.srcFid = srcFid;
        this.fileState = fileState;
        this.applyFileLevel = applyFileLevel;
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
        this.applyMajorUnit = applyMajorUnit;
        this.applyMinorUnit = applyMinorUnit;
        this.createComment = createComment;
        this.approverByStep = approverByStep;
        this.permission = permission;
        this.markVersion = markVersion;
        this.urgency = urgency;
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
     * 流程名称 2正式定密审核 3文件签发审核 4密级变更审核 5文件解密审核
     */
    public Integer getWorkFlowType() {
        return workFlowType;
    }

    public void setWorkFlowType(Integer workFlowType) {
        this.workFlowType = workFlowType;
    }

    /**
     * 文件ID
     */
    public String getSrcFid() {
        return srcFid;
    }

    public void setSrcFid(String srcFid) {
        this.srcFid = srcFid;
    }

    /**
     * 定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
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
     * 申请的辅助定密单位ID
     */
    public UnitInfo getApplyMajorUnit() {
        return applyMajorUnit;
    }

    public void setApplyMajorUnit(UnitInfo applyMajorUnit) {
        this.applyMajorUnit = applyMajorUnit;
    }

    /**
     * 申请的辅助定密单位ID
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
     * 责任人角色信息ID 只有签发流程由签发人审核，其他均由定密责任人审核。现在只填1个下一级审批人角色id
     */
    public List<Integer> getApproverByStep() {
        return approverByStep;
    }

    public void setApproverByStep(List<Integer> approverByStep) {
        this.approverByStep = approverByStep;
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
     * 业务属性
     */
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
