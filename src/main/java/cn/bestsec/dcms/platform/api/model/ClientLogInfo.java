package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ClientLogInfo {
    private Long operateTime;
    private String operateType;
    private String operateDescription;
    private Integer operateResult;
    private UserSimpleInfo user;
    private ClientSimpleInfo client;
    private FileSimpleInfo srcFile;
    private FileSimpleInfo desFile;
    private String operateWay;
    private Integer riskLevel;
    private String reserve;
    
    public ClientLogInfo() {
    }

    public ClientLogInfo(Long operateTime, String operateType, String operateDescription, Integer operateResult, UserSimpleInfo user, ClientSimpleInfo client, FileSimpleInfo srcFile, FileSimpleInfo desFile, String operateWay, Integer riskLevel, String reserve) {
        this.operateTime = operateTime;
        this.operateType = operateType;
        this.operateDescription = operateDescription;
        this.operateResult = operateResult;
        this.user = user;
        this.client = client;
        this.srcFile = srcFile;
        this.desFile = desFile;
        this.operateWay = operateWay;
        this.riskLevel = riskLevel;
        this.reserve = reserve;
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
     * 操作类型 阅读 打印（有无水印） 编辑 截屏 内容复制 文件复制 删除 另存 重命名 发送 预定密 正式定密 签发 变更 解密 解绑 授权
     */
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    /**
     * 操作详情 用户[xxx]打印失败，原因没有权限。
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
     * 终端请求日志ID
     */
    public FileSimpleInfo getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(FileSimpleInfo srcFile) {
        this.srcFile = srcFile;
    }

    /**
     * 终端请求日志ID
     */
    public FileSimpleInfo getDesFile() {
        return desFile;
    }

    public void setDesFile(FileSimpleInfo desFile) {
        this.desFile = desFile;
    }

    /**
     * 操作方法 内容粘贴 U盘拷贝 光盘刻录 网络共享 邮件发送
     */
    public String getOperateWay() {
        return operateWay;
    }

    public void setOperateWay(String operateWay) {
        this.operateWay = operateWay;
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
