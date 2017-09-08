package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Client_LoadPolicyResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer systemTopLevel;
    private String uninstallPasswd;
    private Integer preclassifiedForce;
    private List<TrustedAppInfo> trustAppList;
    private List<String> accessFileList;
    private List<MarkKeyInfo> markKeyList;
    private MarkKeyInfo currentMarkKey;
    private String classExtension;
    private List<Integer> fileLevelAccessPolicy;
    private String classifiedWhiteList;
    
    public Client_LoadPolicyResponse() {
    }

    public Client_LoadPolicyResponse(Integer systemTopLevel, String uninstallPasswd, Integer preclassifiedForce, List<TrustedAppInfo> trustAppList, List<String> accessFileList, List<MarkKeyInfo> markKeyList, MarkKeyInfo currentMarkKey, String classExtension, List<Integer> fileLevelAccessPolicy, String classifiedWhiteList) {
        this.systemTopLevel = systemTopLevel;
        this.uninstallPasswd = uninstallPasswd;
        this.preclassifiedForce = preclassifiedForce;
        this.trustAppList = trustAppList;
        this.accessFileList = accessFileList;
        this.markKeyList = markKeyList;
        this.currentMarkKey = currentMarkKey;
        this.classExtension = classExtension;
        this.fileLevelAccessPolicy = fileLevelAccessPolicy;
        this.classifiedWhiteList = classifiedWhiteList;
    }

    public Client_LoadPolicyResponse(Integer code, String msg, Integer systemTopLevel, String uninstallPasswd, Integer preclassifiedForce, List<TrustedAppInfo> trustAppList, List<String> accessFileList, List<MarkKeyInfo> markKeyList, MarkKeyInfo currentMarkKey, String classExtension, List<Integer> fileLevelAccessPolicy, String classifiedWhiteList) {
        this.code = code;
        this.msg = msg;
        this.systemTopLevel = systemTopLevel;
        this.uninstallPasswd = uninstallPasswd;
        this.preclassifiedForce = preclassifiedForce;
        this.trustAppList = trustAppList;
        this.accessFileList = accessFileList;
        this.markKeyList = markKeyList;
        this.currentMarkKey = currentMarkKey;
        this.classExtension = classExtension;
        this.fileLevelAccessPolicy = fileLevelAccessPolicy;
        this.classifiedWhiteList = classifiedWhiteList;
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
     * 系统最高支持密级
     */
    public Integer getSystemTopLevel() {
        return systemTopLevel;
    }

    public void setSystemTopLevel(Integer systemTopLevel) {
        this.systemTopLevel = systemTopLevel;
    }

    /**
     * 卸载密码
     */
    public String getUninstallPasswd() {
        return uninstallPasswd;
    }

    public void setUninstallPasswd(String uninstallPasswd) {
        this.uninstallPasswd = uninstallPasswd;
    }

    /**
     * 是否强制预定密 1是 0否
     */
    public Integer getPreclassifiedForce() {
        return preclassifiedForce;
    }

    public void setPreclassifiedForce(Integer preclassifiedForce) {
        this.preclassifiedForce = preclassifiedForce;
    }

    /**
     * 可信应用策略
     */
    public List<TrustedAppInfo> getTrustAppList() {
        return trustAppList;
    }

    public void setTrustAppList(List<TrustedAppInfo> trustAppList) {
        this.trustAppList = trustAppList;
    }

    /**
     * 可访问文件策略
     */
    public List<String> getAccessFileList() {
        return accessFileList;
    }

    public void setAccessFileList(List<String> accessFileList) {
        this.accessFileList = accessFileList;
    }

    /**
     * 标志密钥
     */
    public List<MarkKeyInfo> getMarkKeyList() {
        return markKeyList;
    }

    public void setMarkKeyList(List<MarkKeyInfo> markKeyList) {
        this.markKeyList = markKeyList;
    }

    /**
     * 当前使用的标志密钥
     */
    public MarkKeyInfo getCurrentMarkKey() {
        return currentMarkKey;
    }

    public void setCurrentMarkKey(MarkKeyInfo currentMarkKey) {
        this.currentMarkKey = currentMarkKey;
    }

    /**
     * 可标密的文件拓展名策略 |分割
     */
    public String getClassExtension() {
        return classExtension;
    }

    public void setClassExtension(String classExtension) {
        this.classExtension = classExtension;
    }

    /**
     * 文件密级访问策略，用户可访问的文件密级列表
     */
    public List<Integer> getFileLevelAccessPolicy() {
        return fileLevelAccessPolicy;
    }

    public void setFileLevelAccessPolicy(List<Integer> fileLevelAccessPolicy) {
        this.fileLevelAccessPolicy = fileLevelAccessPolicy;
    }

    /**
     * 标密白名单 |分割
     */
    public String getClassifiedWhiteList() {
        return classifiedWhiteList;
    }

    public void setClassifiedWhiteList(String classifiedWhiteList) {
        this.classifiedWhiteList = classifiedWhiteList;
    }
}
