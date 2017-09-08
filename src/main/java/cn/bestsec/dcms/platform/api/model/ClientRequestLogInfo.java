package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ClientRequestLogInfo {
    private Integer clientRequestLogId;
    private Long createTime;
    private Long operateTime;
    private String operateType;
    private String operateDescription;
    private Integer operateResult;
    private UserSimpleInfo user;
    private ClientSimpleInfo client;
    private Integer riskLevel;
    private String reserve;
    
    public ClientRequestLogInfo() {
    }

    public ClientRequestLogInfo(Integer clientRequestLogId, Long createTime, Long operateTime, String operateType, String operateDescription, Integer operateResult, UserSimpleInfo user, ClientSimpleInfo client, Integer riskLevel, String reserve) {
        this.clientRequestLogId = clientRequestLogId;
        this.createTime = createTime;
        this.operateTime = operateTime;
        this.operateType = operateType;
        this.operateDescription = operateDescription;
        this.operateResult = operateResult;
        this.user = user;
        this.client = client;
        this.riskLevel = riskLevel;
        this.reserve = reserve;
    }

    /**
     * 终端请求日志ID
     */
    public Integer getClientRequestLogId() {
        return clientRequestLogId;
    }

    public void setClientRequestLogId(Integer clientRequestLogId) {
        this.clientRequestLogId = clientRequestLogId;
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
     * 操作类型 登陆 注销 修改密码 策略更新 文件下载
     */
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    /**
     * 操作详情 用户[xxx]登陆失败，原因账号或密码错误
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
     * 用户信息
     */
    public UserSimpleInfo getUser() {
        return user;
    }

    public void setUser(UserSimpleInfo user) {
        this.user = user;
    }

    /**
     * 终端信息
     */
    public ClientSimpleInfo getClient() {
        return client;
    }

    public void setClient(ClientSimpleInfo client) {
        this.client = client;
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
