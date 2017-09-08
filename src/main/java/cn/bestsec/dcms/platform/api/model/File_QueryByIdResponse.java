package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryByIdResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String fid;
    private String fileName;
    private Integer urgency;
    private Integer fileLevel;
    private String fileValidPeriod;
    private Integer durationType;
    private String durationDescription;
    private Integer fileState;
    private String fileLocation;
    private Long fileSize;
    private String fileMd5;
    private Long fileLevelDecideTime;
    private Long fileDecryptTime;
    private List<BasisInfo> basis;
    private Integer basisType;
    private String basisDesc;
    private String issueNumber;
    private Integer docNumber;
    private Integer duplicationAmount;
    private List<FileScopeInfo> fileScope;
    private String fileScopeDesc;
    private String fileCreateUserName;
    private String fileLevelDecideRoleName;
    private String fileDispatchRoleName;
    private Long fileCreateTime;
    private Long fileDispatchTime;
    private Long fileLevelChangeTime;
    private String description;
    private UnitInfo fileMajorUnit;
    private List<UnitInfo> fileMinorUnit;
    private PermissionBaseInfo permission;
    private Integer markVersion;
    private List<HistoryInfo> historyList;
    private String business;
    
    public File_QueryByIdResponse() {
    }

    public File_QueryByIdResponse(String fid, String fileName, Integer urgency, Integer fileLevel, String fileValidPeriod, Integer durationType, String durationDescription, Integer fileState, String fileLocation, Long fileSize, String fileMd5, Long fileLevelDecideTime, Long fileDecryptTime, List<BasisInfo> basis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, List<FileScopeInfo> fileScope, String fileScopeDesc, String fileCreateUserName, String fileLevelDecideRoleName, String fileDispatchRoleName, Long fileCreateTime, Long fileDispatchTime, Long fileLevelChangeTime, String description, UnitInfo fileMajorUnit, List<UnitInfo> fileMinorUnit, PermissionBaseInfo permission, Integer markVersion, List<HistoryInfo> historyList, String business) {
        this.fid = fid;
        this.fileName = fileName;
        this.urgency = urgency;
        this.fileLevel = fileLevel;
        this.fileValidPeriod = fileValidPeriod;
        this.durationType = durationType;
        this.durationDescription = durationDescription;
        this.fileState = fileState;
        this.fileLocation = fileLocation;
        this.fileSize = fileSize;
        this.fileMd5 = fileMd5;
        this.fileLevelDecideTime = fileLevelDecideTime;
        this.fileDecryptTime = fileDecryptTime;
        this.basis = basis;
        this.basisType = basisType;
        this.basisDesc = basisDesc;
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.fileScope = fileScope;
        this.fileScopeDesc = fileScopeDesc;
        this.fileCreateUserName = fileCreateUserName;
        this.fileLevelDecideRoleName = fileLevelDecideRoleName;
        this.fileDispatchRoleName = fileDispatchRoleName;
        this.fileCreateTime = fileCreateTime;
        this.fileDispatchTime = fileDispatchTime;
        this.fileLevelChangeTime = fileLevelChangeTime;
        this.description = description;
        this.fileMajorUnit = fileMajorUnit;
        this.fileMinorUnit = fileMinorUnit;
        this.permission = permission;
        this.markVersion = markVersion;
        this.historyList = historyList;
        this.business = business;
    }

    public File_QueryByIdResponse(Integer code, String msg, String fid, String fileName, Integer urgency, Integer fileLevel, String fileValidPeriod, Integer durationType, String durationDescription, Integer fileState, String fileLocation, Long fileSize, String fileMd5, Long fileLevelDecideTime, Long fileDecryptTime, List<BasisInfo> basis, Integer basisType, String basisDesc, String issueNumber, Integer docNumber, Integer duplicationAmount, List<FileScopeInfo> fileScope, String fileScopeDesc, String fileCreateUserName, String fileLevelDecideRoleName, String fileDispatchRoleName, Long fileCreateTime, Long fileDispatchTime, Long fileLevelChangeTime, String description, UnitInfo fileMajorUnit, List<UnitInfo> fileMinorUnit, PermissionBaseInfo permission, Integer markVersion, List<HistoryInfo> historyList, String business) {
        this.code = code;
        this.msg = msg;
        this.fid = fid;
        this.fileName = fileName;
        this.urgency = urgency;
        this.fileLevel = fileLevel;
        this.fileValidPeriod = fileValidPeriod;
        this.durationType = durationType;
        this.durationDescription = durationDescription;
        this.fileState = fileState;
        this.fileLocation = fileLocation;
        this.fileSize = fileSize;
        this.fileMd5 = fileMd5;
        this.fileLevelDecideTime = fileLevelDecideTime;
        this.fileDecryptTime = fileDecryptTime;
        this.basis = basis;
        this.basisType = basisType;
        this.basisDesc = basisDesc;
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.fileScope = fileScope;
        this.fileScopeDesc = fileScopeDesc;
        this.fileCreateUserName = fileCreateUserName;
        this.fileLevelDecideRoleName = fileLevelDecideRoleName;
        this.fileDispatchRoleName = fileDispatchRoleName;
        this.fileCreateTime = fileCreateTime;
        this.fileDispatchTime = fileDispatchTime;
        this.fileLevelChangeTime = fileLevelChangeTime;
        this.description = description;
        this.fileMajorUnit = fileMajorUnit;
        this.fileMinorUnit = fileMinorUnit;
        this.permission = permission;
        this.markVersion = markVersion;
        this.historyList = historyList;
        this.business = business;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
     * 文件重要紧急程度 0一般 1重要 2加急
     */
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
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
     * 文件状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     */
    public Integer getFileState() {
        return fileState;
    }

    public void setFileState(Integer fileState) {
        this.fileState = fileState;
    }

    /**
     * 文件存储位置
     */
    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * 文件大小，字节
     */
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 文件MD5
     */
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
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
     * 定密依据
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
    public List<FileScopeInfo> getFileScope() {
        return fileScope;
    }

    public void setFileScope(List<FileScopeInfo> fileScope) {
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
     * 文件起草人
     */
    public String getFileCreateUserName() {
        return fileCreateUserName;
    }

    public void setFileCreateUserName(String fileCreateUserName) {
        this.fileCreateUserName = fileCreateUserName;
    }

    /**
     * 定密责任人(末次)
     */
    public String getFileLevelDecideRoleName() {
        return fileLevelDecideRoleName;
    }

    public void setFileLevelDecideRoleName(String fileLevelDecideRoleName) {
        this.fileLevelDecideRoleName = fileLevelDecideRoleName;
    }

    /**
     * 文件签发人
     */
    public String getFileDispatchRoleName() {
        return fileDispatchRoleName;
    }

    public void setFileDispatchRoleName(String fileDispatchRoleName) {
        this.fileDispatchRoleName = fileDispatchRoleName;
    }

    /**
     * 文件起草日期，时间戳毫秒
     */
    public Long getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(Long fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    /**
     * 文件签发日期，时间戳毫秒
     */
    public Long getFileDispatchTime() {
        return fileDispatchTime;
    }

    public void setFileDispatchTime(Long fileDispatchTime) {
        this.fileDispatchTime = fileDispatchTime;
    }

    /**
     * 文件密级变更日期(末次)，时间戳毫秒
     */
    public Long getFileLevelChangeTime() {
        return fileLevelChangeTime;
    }

    public void setFileLevelChangeTime(Long fileLevelChangeTime) {
        this.fileLevelChangeTime = fileLevelChangeTime;
    }

    /**
     * 文件属性详情
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
     * 定密历史
     */
    public List<HistoryInfo> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryInfo> historyList) {
        this.historyList = historyList;
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
