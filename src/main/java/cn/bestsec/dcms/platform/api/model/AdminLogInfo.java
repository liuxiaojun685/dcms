package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class AdminLogInfo {
    private Integer adminLogId;
    private Long createTime;
    private Long operateTime;
    private String operateType;
    private String operateDescription;
    private Integer operateResult;
    private AdminSimpleInfo admin;
    private String ip;
    private Integer riskLevel;
    private String reserve;
    
    public AdminLogInfo() {
    }

    public AdminLogInfo(Integer adminLogId, Long createTime, Long operateTime, String operateType, String operateDescription, Integer operateResult, AdminSimpleInfo admin, String ip, Integer riskLevel, String reserve) {
        this.adminLogId = adminLogId;
        this.createTime = createTime;
        this.operateTime = operateTime;
        this.operateType = operateType;
        this.operateDescription = operateDescription;
        this.operateResult = operateResult;
        this.admin = admin;
        this.ip = ip;
        this.riskLevel = riskLevel;
        this.reserve = reserve;
    }

    /**
     * 管理员日志ID
     */
    public Integer getAdminLogId() {
        return adminLogId;
    }

    public void setAdminLogId(Integer adminLogId) {
        this.adminLogId = adminLogId;
    }

    /**
     * 入库时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 操作时间
     */
    public Long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 操作类型
     */
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    /**
     * 操作详情 概要 关键词搜索
     */
    public String getOperateDescription() {
        return operateDescription;
    }

    public void setOperateDescription(String operateDescription) {
        this.operateDescription = operateDescription;
    }

    /**
     * 操作结果 1成功 2失败
     */
    public Integer getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(Integer operateResult) {
        this.operateResult = operateResult;
    }

    /**
     * 管理员信息
     */
    public AdminSimpleInfo getAdmin() {
        return admin;
    }

    public void setAdmin(AdminSimpleInfo admin) {
        this.admin = admin;
    }

    /**
     * 操作机器IP地址
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 重要程度 1一般 2重要 3严重
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 预留
     */
    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
