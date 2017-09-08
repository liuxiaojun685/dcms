package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class TrustedAppInfo {
    private Integer trustedAppId;
    private String processName;
    private String description;
    private String extensionName;
    
    public TrustedAppInfo() {
    }

    public TrustedAppInfo(Integer trustedAppId, String processName, String description, String extensionName) {
        this.trustedAppId = trustedAppId;
        this.processName = processName;
        this.description = description;
        this.extensionName = extensionName;
    }

    /**
     * 可信应用程序ID
     */
    public Integer getTrustedAppId() {
        return trustedAppId;
    }

    public void setTrustedAppId(Integer trustedAppId) {
        this.trustedAppId = trustedAppId;
    }

    /**
     * 应用程序名称或进程名
     */
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * 可信应用程序描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 可信应用程序扩展名
     */
    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }
}
