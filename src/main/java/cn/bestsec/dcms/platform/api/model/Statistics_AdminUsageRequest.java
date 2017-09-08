package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_AdminUsageRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer adminType;
    private Integer threshold;
    
    public Statistics_AdminUsageRequest() {
    }

    public Statistics_AdminUsageRequest(String token, Integer adminType, Integer threshold) {
        this.token = token;
        this.adminType = adminType;
        this.threshold = threshold;
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
     * 管理员类型。2系统管理员 3安全保密管理员
     */
    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    /**
     * 按时间统计 0总 1年 2月 3周 4日
     */
    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
