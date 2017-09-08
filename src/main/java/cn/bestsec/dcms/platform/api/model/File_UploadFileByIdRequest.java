package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_UploadFileByIdRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer uploadType;
    private String fid;
    private String fileName;
    private Integer fileLevel;
    private Integer urgency;
    private String fileValidPeriod;
    private Integer durationType;
    private String durationDescription;
    private Integer fileState;
    private String fileCreateUid;
    private String fileLevelDecideRoleUid;
    private String fileDispatchRoleUid;
    private Long fileCreateTime;
    private Long fileLevelDecideTime;
    private Long fileDispatchTime;
    private Long fileLevelChangeTime;
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
    
    public File_UploadFileByIdRequest() {
    }

    public File_UploadFileByIdRequest(String token, Integer uploadType, String fid, String fileName, Integer fileLevel, Integer urgency, String fileValidPeriod, Integer durationType, String durationDescription, Integer fileState, String fileCreateUid, String fileLevelDecideRoleUid, String fileDispatchRoleUid, Long fileCreateTime, Long fileLevelDecideTime, Long fileDispatchTime, Long fileLevelChangeTime, Long fileDecryptTime, UnitInfo fileMajorUnit, List<UnitInfo> fileMinorUnit, List<BasisInfo> basis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, List<FileScopeItem> fileScope, String fileScopeDesc, PermissionBaseInfo permission, Integer markVersion, String description, String business) {
        this.token = token;
        this.uploadType = uploadType;
        this.fid = fid;
        this.fileName = fileName;
        this.fileLevel = fileLevel;
        this.urgency = urgency;
        this.fileValidPeriod = fileValidPeriod;
        this.durationType = durationType;
        this.durationDescription = durationDescription;
        this.fileState = fileState;
        this.fileCreateUid = fileCreateUid;
        this.fileLevelDecideRoleUid = fileLevelDecideRoleUid;
        this.fileDispatchRoleUid = fileDispatchRoleUid;
        this.fileCreateTime = fileCreateTime;
        this.fileLevelDecideTime = fileLevelDecideTime;
        this.fileDispatchTime = fileDispatchTime;
        this.fileLevelChangeTime = fileLevelChangeTime;
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
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 上传类型 2正式定密 3文件签发 4密级变更 5文件解密 6更新
     */
    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
    }

    /**
     * 文件ID
     */
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * 文件重要紧急程度
     */
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    /**
     * 保密期限，格式yymmdd
     */
    public String getFileValidPeriod() {
        return fileValidPeriod;
    }

    public void setFileValidPeriod(String fileValidPeriod) {
        this.fileValidPeriod = fileValidPeriod;
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
     * 定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }

    /**
     * 文件起草人
     */
    public String getFileCreateUid() {
        return fileCreateUid;
    }

    public void setFileCreateUid(String fileCreateUid) {
        this.fileCreateUid = fileCreateUid;
    }

    /**
     * 定密责任人(末次)
     */
    public String getFileLevelDecideRoleUid() {
        return fileLevelDecideRoleUid;
    }

    public void setFileLevelDecideRoleUid(String fileLevelDecideRoleUid) {
        this.fileLevelDecideRoleUid = fileLevelDecideRoleUid;
    }

    /**
     * 文件签发人
     */
    public String getFileDispatchRoleUid() {
        return fileDispatchRoleUid;
    }

    public void setFileDispatchRoleUid(String fileDispatchRoleUid) {
        this.fileDispatchRoleUid = fileDispatchRoleUid;
    }

    /**
     * 文件起草日期
     */
    public Long getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(Long fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    /**
     * 正式定密日期
     */
    public Long getFileLevelDecideTime() {
        return fileLevelDecideTime;
    }

    public void setFileLevelDecideTime(Long fileLevelDecideTime) {
        this.fileLevelDecideTime = fileLevelDecideTime;
    }

    /**
     * 文件签发日期
     */
    public Long getFileDispatchTime() {
        return fileDispatchTime;
    }

    public void setFileDispatchTime(Long fileDispatchTime) {
        this.fileDispatchTime = fileDispatchTime;
    }

    /**
     * 文件密级变更日期(末次)
     */
    public Long getFileLevelChangeTime() {
        return fileLevelChangeTime;
    }

    public void setFileLevelChangeTime(Long fileLevelChangeTime) {
        this.fileLevelChangeTime = fileLevelChangeTime;
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
     * 主定密单位ID
     */
    public UnitInfo getFileMajorUnit() {
        return fileMajorUnit;
    }

    public void setFileMajorUnit(UnitInfo fileMajorUnit) {
        this.fileMajorUnit = fileMajorUnit;
    }

    /**
     * 辅助定密单位ID
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
     * 签发份数
     */
    public Integer getDuplicationAmount() {
        return duplicationAmount;
    }

    public void setDuplicationAmount(Integer duplicationAmount) {
        this.duplicationAmount = duplicationAmount;
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
