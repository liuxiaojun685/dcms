package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class MiddlewareFileAttr {
    private String uid;
    private Integer fileLevel;
    private Integer urgency;
    private String fileValidPeriod;
    private Integer durationType;
    private String durationDescription;
    private Long fileLevelDecideTime;
    private Long fileDecryptTime;
    private UnitInfo fileMajorUnit;
    private List<UnitInfo> fileMinorUnit;
    private List<BasisInfo> basis;
    private Integer basisType;
    private String basisDesc;
    private String issueNumber;
    private Integer docNumber;
    private Integer duplicationAmount;
    private List<FileScopeItem> fileScope;
    private String fileScopeDesc;
    private PermissionBaseInfo permission;
    private Integer markVersion;
    private String description;
    private String business;
    
    public MiddlewareFileAttr() {
    }

    public MiddlewareFileAttr(String uid, Integer fileLevel, Integer urgency, String fileValidPeriod, Integer durationType, String durationDescription, Long fileLevelDecideTime, Long fileDecryptTime, UnitInfo fileMajorUnit, List<UnitInfo> fileMinorUnit, List<BasisInfo> basis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, List<FileScopeItem> fileScope, String fileScopeDesc, PermissionBaseInfo permission, Integer markVersion, String description, String business) {
        this.uid = uid;
        this.fileLevel = fileLevel;
        this.urgency = urgency;
        this.fileValidPeriod = fileValidPeriod;
        this.durationType = durationType;
        this.durationDescription = durationDescription;
        this.fileLevelDecideTime = fileLevelDecideTime;
        this.fileDecryptTime = fileDecryptTime;
        this.fileMajorUnit = fileMajorUnit;
        this.fileMinorUnit = fileMinorUnit;
        this.basis = basis;
        this.basisType = basisType;
        this.basisDesc = basisDesc;
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.fileScope = fileScope;
        this.fileScopeDesc = fileScopeDesc;
        this.permission = permission;
        this.markVersion = markVersion;
        this.description = description;
        this.business = business;
    }

    /**
     * 责任人用户ID
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 文件密级 0公开 1内部 2秘密 3机密 4绝密
     */
    public Integer getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(Integer fileLevel) {
        this.fileLevel = fileLevel;
    }

    /**
     * 文件重要紧急程度 0一般 1重要 2加急
     */
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    /**
     * 保密期限(期限形式)，格式yymmdd
     */
    public String getFileValidPeriod() {
        return fileValidPeriod;
    }

    public void setFileValidPeriod(String fileValidPeriod) {
        this.fileValidPeriod = fileValidPeriod;
    }

    /**
     * 保密期限类型 0不限 1长期 2期限 3解密日期 4条件
     */
    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    /**
     * 保密期限(描述形式)
     */
    public String getDurationDescription() {
        return durationDescription;
    }

    public void setDurationDescription(String durationDescription) {
        this.durationDescription = durationDescription;
    }

    /**
     * 正式定密日期，时间戳毫秒
     */
    public Long getFileLevelDecideTime() {
        return fileLevelDecideTime;
    }

    public void setFileLevelDecideTime(Long fileLevelDecideTime) {
        this.fileLevelDecideTime = fileLevelDecideTime;
    }

    /**
     * 保密期限(日期形式)，文件解密日期，时间戳毫秒
     */
    public Long getFileDecryptTime() {
        return fileDecryptTime;
    }

    public void setFileDecryptTime(Long fileDecryptTime) {
        this.fileDecryptTime = fileDecryptTime;
    }

    /**
     * 主定密单位
     */
    public UnitInfo getFileMajorUnit() {
        return fileMajorUnit;
    }

    public void setFileMajorUnit(UnitInfo fileMajorUnit) {
        this.fileMajorUnit = fileMajorUnit;
    }

    /**
     * 辅助定密单位
     */
    public List<UnitInfo> getFileMinorUnit() {
        return fileMinorUnit;
    }

    public void setFileMinorUnit(List<UnitInfo> fileMinorUnit) {
        this.fileMinorUnit = fileMinorUnit;
    }

    /**
     * 申请的定密依据
     */
    public List<BasisInfo> getBasis() {
        return basis;
    }

    public void setBasis(List<BasisInfo> basis) {
        this.basis = basis;
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
     * 份数
     */
    public Integer getDuplicationAmount() {
        return duplicationAmount;
    }

    public void setDuplicationAmount(Integer duplicationAmount) {
        this.duplicationAmount = duplicationAmount;
    }

    /**
     * 知悉范围
     */
    public List<FileScopeItem> getFileScope() {
        return fileScope;
    }

    public void setFileScope(List<FileScopeItem> fileScope) {
        this.fileScope = fileScope;
    }

    /**
     * 知悉范围描述
     */
    public String getFileScopeDesc() {
        return fileScopeDesc;
    }

    public void setFileScopeDesc(String fileScopeDesc) {
        this.fileScopeDesc = fileScopeDesc;
    }

    /**
     * 权限管控
     */
    public PermissionBaseInfo getPermission() {
        return permission;
    }

    public void setPermission(PermissionBaseInfo permission) {
        this.permission = permission;
    }

    /**
     * 密级标志版本
     */
    public Integer getMarkVersion() {
        return markVersion;
    }

    public void setMarkVersion(Integer markVersion) {
        this.markVersion = markVersion;
    }

    /**
     * 原因/意见描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
