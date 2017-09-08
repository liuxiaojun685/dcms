package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryEnvironmentResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String osType;
    private String platformVersion;
    private String datebase;
    private String cpuFree;
    private String cpuInfo;
    private String memFree;
    private String memTotal;
    private String hdFree;
    private String hdTotal;
    private String logFree;
    private String logTotal;
    private String fileFree;
    private String fileTotal;
    
    public SystemConfig_QueryEnvironmentResponse() {
    }

    public SystemConfig_QueryEnvironmentResponse(String osType, String platformVersion, String datebase, String cpuFree, String cpuInfo, String memFree, String memTotal, String hdFree, String hdTotal, String logFree, String logTotal, String fileFree, String fileTotal) {
        this.osType = osType;
        this.platformVersion = platformVersion;
        this.datebase = datebase;
        this.cpuFree = cpuFree;
        this.cpuInfo = cpuInfo;
        this.memFree = memFree;
        this.memTotal = memTotal;
        this.hdFree = hdFree;
        this.hdTotal = hdTotal;
        this.logFree = logFree;
        this.logTotal = logTotal;
        this.fileFree = fileFree;
        this.fileTotal = fileTotal;
    }

    public SystemConfig_QueryEnvironmentResponse(Integer code, String msg, String osType, String platformVersion, String datebase, String cpuFree, String cpuInfo, String memFree, String memTotal, String hdFree, String hdTotal, String logFree, String logTotal, String fileFree, String fileTotal) {
        this.code = code;
        this.msg = msg;
        this.osType = osType;
        this.platformVersion = platformVersion;
        this.datebase = datebase;
        this.cpuFree = cpuFree;
        this.cpuInfo = cpuInfo;
        this.memFree = memFree;
        this.memTotal = memTotal;
        this.hdFree = hdFree;
        this.hdTotal = hdTotal;
        this.logFree = logFree;
        this.logTotal = logTotal;
        this.fileFree = fileFree;
        this.fileTotal = fileTotal;
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
     * 操作系统
     */
    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * 平台服务版本
     */
    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    /**
     * 数据库
     */
    public String getDatebase() {
        return datebase;
    }

    public void setDatebase(String datebase) {
        this.datebase = datebase;
    }

    /**
     * cpu使用率0-100
     */
    public String getCpuFree() {
        return cpuFree;
    }

    public void setCpuFree(String cpuFree) {
        this.cpuFree = cpuFree;
    }

    /**
     * cpu信息，型号，频率
     */
    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    /**
     * 内存剩余字节
     */
    public String getMemFree() {
        return memFree;
    }

    public void setMemFree(String memFree) {
        this.memFree = memFree;
    }

    /**
     * 内存总字节
     */
    public String getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(String memTotal) {
        this.memTotal = memTotal;
    }

    /**
     * 硬盘剩余字节
     */
    public String getHdFree() {
        return hdFree;
    }

    public void setHdFree(String hdFree) {
        this.hdFree = hdFree;
    }

    /**
     * 硬盘总字节
     */
    public String getHdTotal() {
        return hdTotal;
    }

    public void setHdTotal(String hdTotal) {
        this.hdTotal = hdTotal;
    }

    /**
     * 日志存储剩余字节
     */
    public String getLogFree() {
        return logFree;
    }

    public void setLogFree(String logFree) {
        this.logFree = logFree;
    }

    /**
     * 日志存储总字节
     */
    public String getLogTotal() {
        return logTotal;
    }

    public void setLogTotal(String logTotal) {
        this.logTotal = logTotal;
    }

    /**
     * 文件存储剩余字节
     */
    public String getFileFree() {
        return fileFree;
    }

    public void setFileFree(String fileFree) {
        this.fileFree = fileFree;
    }

    /**
     * 文件存储总字节
     */
    public String getFileTotal() {
        return fileTotal;
    }

    public void setFileTotal(String fileTotal) {
        this.fileTotal = fileTotal;
    }
}
