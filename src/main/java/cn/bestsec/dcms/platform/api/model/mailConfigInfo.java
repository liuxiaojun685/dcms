package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class mailConfigInfo {
    private String mailSenderAccount;
    private String mailSenderPasswd;
    private String mailSenderSmtpAddr;
    private String mailSenderSmtpPort;
    private Integer mailSenderSSLEnable;
    private String mailRecvResourceAccount;
    private ResourceThreshold mailRecvResourceThreshold;
    private String mailRecvLogStorageAddr;
    private StorageThreshold mailRecvLogStorageThreshold;
    private String mailRecvFileStorageAddr;
    private StorageThreshold mailRecvFileStorageThreshold;
    
    public mailConfigInfo() {
    }

    public mailConfigInfo(String mailSenderAccount, String mailSenderPasswd, String mailSenderSmtpAddr, String mailSenderSmtpPort, Integer mailSenderSSLEnable, String mailRecvResourceAccount, ResourceThreshold mailRecvResourceThreshold, String mailRecvLogStorageAddr, StorageThreshold mailRecvLogStorageThreshold, String mailRecvFileStorageAddr, StorageThreshold mailRecvFileStorageThreshold) {
        this.mailSenderAccount = mailSenderAccount;
        this.mailSenderPasswd = mailSenderPasswd;
        this.mailSenderSmtpAddr = mailSenderSmtpAddr;
        this.mailSenderSmtpPort = mailSenderSmtpPort;
        this.mailSenderSSLEnable = mailSenderSSLEnable;
        this.mailRecvResourceAccount = mailRecvResourceAccount;
        this.mailRecvResourceThreshold = mailRecvResourceThreshold;
        this.mailRecvLogStorageAddr = mailRecvLogStorageAddr;
        this.mailRecvLogStorageThreshold = mailRecvLogStorageThreshold;
        this.mailRecvFileStorageAddr = mailRecvFileStorageAddr;
        this.mailRecvFileStorageThreshold = mailRecvFileStorageThreshold;
    }

    /**
     * 邮件告警发件人邮箱
     */
    public String getMailSenderAccount() {
        return mailSenderAccount;
    }

    public void setMailSenderAccount(String mailSenderAccount) {
        this.mailSenderAccount = mailSenderAccount;
    }

    /**
     * 邮件告警发件人密码
     */
    public String getMailSenderPasswd() {
        return mailSenderPasswd;
    }

    public void setMailSenderPasswd(String mailSenderPasswd) {
        this.mailSenderPasswd = mailSenderPasswd;
    }

    /**
     * 邮件告警SMTP服务器地址
     */
    public String getMailSenderSmtpAddr() {
        return mailSenderSmtpAddr;
    }

    public void setMailSenderSmtpAddr(String mailSenderSmtpAddr) {
        this.mailSenderSmtpAddr = mailSenderSmtpAddr;
    }

    /**
     * 邮件告警SMTP服务器端口号
     */
    public String getMailSenderSmtpPort() {
        return mailSenderSmtpPort;
    }

    public void setMailSenderSmtpPort(String mailSenderSmtpPort) {
        this.mailSenderSmtpPort = mailSenderSmtpPort;
    }

    /**
     * 邮件告警SMTP是否启用SSL
     */
    public Integer getMailSenderSSLEnable() {
        return mailSenderSSLEnable;
    }

    public void setMailSenderSSLEnable(Integer mailSenderSSLEnable) {
        this.mailSenderSSLEnable = mailSenderSSLEnable;
    }

    /**
     * 系统资源告警收件地址 ;号分割
     */
    public String getMailRecvResourceAccount() {
        return mailRecvResourceAccount;
    }

    public void setMailRecvResourceAccount(String mailRecvResourceAccount) {
        this.mailRecvResourceAccount = mailRecvResourceAccount;
    }

    /**
     * 系统资源告警阈值
     */
    public ResourceThreshold getMailRecvResourceThreshold() {
        return mailRecvResourceThreshold;
    }

    public void setMailRecvResourceThreshold(ResourceThreshold mailRecvResourceThreshold) {
        this.mailRecvResourceThreshold = mailRecvResourceThreshold;
    }

    /**
     * 日志存储容量告警收件地址 ;号分割
     */
    public String getMailRecvLogStorageAddr() {
        return mailRecvLogStorageAddr;
    }

    public void setMailRecvLogStorageAddr(String mailRecvLogStorageAddr) {
        this.mailRecvLogStorageAddr = mailRecvLogStorageAddr;
    }

    /**
     * 日志存储容量告警阈值
     */
    public StorageThreshold getMailRecvLogStorageThreshold() {
        return mailRecvLogStorageThreshold;
    }

    public void setMailRecvLogStorageThreshold(StorageThreshold mailRecvLogStorageThreshold) {
        this.mailRecvLogStorageThreshold = mailRecvLogStorageThreshold;
    }

    /**
     * 文件存储容量告警收件地址 ;号分割
     */
    public String getMailRecvFileStorageAddr() {
        return mailRecvFileStorageAddr;
    }

    public void setMailRecvFileStorageAddr(String mailRecvFileStorageAddr) {
        this.mailRecvFileStorageAddr = mailRecvFileStorageAddr;
    }

    /**
     * 文件存储容量告警阈值
     */
    public StorageThreshold getMailRecvFileStorageThreshold() {
        return mailRecvFileStorageThreshold;
    }

    public void setMailRecvFileStorageThreshold(StorageThreshold mailRecvFileStorageThreshold) {
        this.mailRecvFileStorageThreshold = mailRecvFileStorageThreshold;
    }
}
