package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateLocalAuthConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer localAuthEnable;
    private Integer localAuthPasswdMin;
    private Integer localAuthPasswdMax;
    private Integer localAuthPasswdContainsNumber;
    private Integer localAuthPasswdContainsLetter;
    private Integer localAuthPasswdContainsSpecial;
    private Integer localAuthPasswdLockThreshold;
    private Long localAuthPasswdLockTime;
    private Integer localAuthPasswdForceChange;
    private Long localAuthPasswdPeriod;
    private Integer localAuthUnlockByAdmin;
    private String localAuthInitPasswd;
    private Integer localAuthInitPasswdForceChange;
    
    public SystemConfig_UpdateLocalAuthConfigRequest() {
    }

    public SystemConfig_UpdateLocalAuthConfigRequest(String token, Integer localAuthEnable, Integer localAuthPasswdMin, Integer localAuthPasswdMax, Integer localAuthPasswdContainsNumber, Integer localAuthPasswdContainsLetter, Integer localAuthPasswdContainsSpecial, Integer localAuthPasswdLockThreshold, Long localAuthPasswdLockTime, Integer localAuthPasswdForceChange, Long localAuthPasswdPeriod, Integer localAuthUnlockByAdmin, String localAuthInitPasswd, Integer localAuthInitPasswdForceChange) {
        this.token = token;
        this.localAuthEnable = localAuthEnable;
        this.localAuthPasswdMin = localAuthPasswdMin;
        this.localAuthPasswdMax = localAuthPasswdMax;
        this.localAuthPasswdContainsNumber = localAuthPasswdContainsNumber;
        this.localAuthPasswdContainsLetter = localAuthPasswdContainsLetter;
        this.localAuthPasswdContainsSpecial = localAuthPasswdContainsSpecial;
        this.localAuthPasswdLockThreshold = localAuthPasswdLockThreshold;
        this.localAuthPasswdLockTime = localAuthPasswdLockTime;
        this.localAuthPasswdForceChange = localAuthPasswdForceChange;
        this.localAuthPasswdPeriod = localAuthPasswdPeriod;
        this.localAuthUnlockByAdmin = localAuthUnlockByAdmin;
        this.localAuthInitPasswd = localAuthInitPasswd;
        this.localAuthInitPasswdForceChange = localAuthInitPasswdForceChange;
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
     * 本地认证是否启用 1是 0否
     */
    public Integer getLocalAuthEnable() {
        return localAuthEnable;
    }

    public void setLocalAuthEnable(Integer localAuthEnable) {
        this.localAuthEnable = localAuthEnable;
    }

    /**
     * 本地认证密码最小长度
     */
    public Integer getLocalAuthPasswdMin() {
        return localAuthPasswdMin;
    }

    public void setLocalAuthPasswdMin(Integer localAuthPasswdMin) {
        this.localAuthPasswdMin = localAuthPasswdMin;
    }

    /**
     * 本地认证密码最大长度
     */
    public Integer getLocalAuthPasswdMax() {
        return localAuthPasswdMax;
    }

    public void setLocalAuthPasswdMax(Integer localAuthPasswdMax) {
        this.localAuthPasswdMax = localAuthPasswdMax;
    }

    /**
     * 密码复杂度(数字) 1必须包含 0可不包含
     */
    public Integer getLocalAuthPasswdContainsNumber() {
        return localAuthPasswdContainsNumber;
    }

    public void setLocalAuthPasswdContainsNumber(Integer localAuthPasswdContainsNumber) {
        this.localAuthPasswdContainsNumber = localAuthPasswdContainsNumber;
    }

    /**
     * 密码复杂度(字母大小写) 1必须包含 0可不包含
     */
    public Integer getLocalAuthPasswdContainsLetter() {
        return localAuthPasswdContainsLetter;
    }

    public void setLocalAuthPasswdContainsLetter(Integer localAuthPasswdContainsLetter) {
        this.localAuthPasswdContainsLetter = localAuthPasswdContainsLetter;
    }

    /**
     * 密码复杂度(特殊字符) 1必须包含 0可不包含
     */
    public Integer getLocalAuthPasswdContainsSpecial() {
        return localAuthPasswdContainsSpecial;
    }

    public void setLocalAuthPasswdContainsSpecial(Integer localAuthPasswdContainsSpecial) {
        this.localAuthPasswdContainsSpecial = localAuthPasswdContainsSpecial;
    }

    /**
     * 密码连续错误次数阈值
     */
    public Integer getLocalAuthPasswdLockThreshold() {
        return localAuthPasswdLockThreshold;
    }

    public void setLocalAuthPasswdLockThreshold(Integer localAuthPasswdLockThreshold) {
        this.localAuthPasswdLockThreshold = localAuthPasswdLockThreshold;
    }

    /**
     * 密码错误锁定时间设置 毫秒
     */
    public Long getLocalAuthPasswdLockTime() {
        return localAuthPasswdLockTime;
    }

    public void setLocalAuthPasswdLockTime(Long localAuthPasswdLockTime) {
        this.localAuthPasswdLockTime = localAuthPasswdLockTime;
    }

    /**
     * 是否强制定期修改密码 1是 0否
     */
    public Integer getLocalAuthPasswdForceChange() {
        return localAuthPasswdForceChange;
    }

    public void setLocalAuthPasswdForceChange(Integer localAuthPasswdForceChange) {
        this.localAuthPasswdForceChange = localAuthPasswdForceChange;
    }

    /**
     * 定期修改密码时间设置 毫秒
     */
    public Long getLocalAuthPasswdPeriod() {
        return localAuthPasswdPeriod;
    }

    public void setLocalAuthPasswdPeriod(Long localAuthPasswdPeriod) {
        this.localAuthPasswdPeriod = localAuthPasswdPeriod;
    }

    /**
     * 是否必须管理员解锁 1是 0否
     */
    public Integer getLocalAuthUnlockByAdmin() {
        return localAuthUnlockByAdmin;
    }

    public void setLocalAuthUnlockByAdmin(Integer localAuthUnlockByAdmin) {
        this.localAuthUnlockByAdmin = localAuthUnlockByAdmin;
    }

    /**
     * 新用户初始密码
     */
    public String getLocalAuthInitPasswd() {
        return localAuthInitPasswd;
    }

    public void setLocalAuthInitPasswd(String localAuthInitPasswd) {
        this.localAuthInitPasswd = localAuthInitPasswd;
    }

    /**
     * 是否强制修改初始密码
     */
    public Integer getLocalAuthInitPasswdForceChange() {
        return localAuthInitPasswdForceChange;
    }

    public void setLocalAuthInitPasswdForceChange(Integer localAuthInitPasswdForceChange) {
        this.localAuthInitPasswdForceChange = localAuthInitPasswdForceChange;
    }
}
