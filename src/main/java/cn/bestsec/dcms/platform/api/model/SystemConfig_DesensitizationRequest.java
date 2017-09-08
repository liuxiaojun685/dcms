package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_DesensitizationRequest extends CommonRequestFieldsSupport {
    private String token;
    private List<Desensity> desensityList;
    
    public SystemConfig_DesensitizationRequest() {
    }

    public SystemConfig_DesensitizationRequest(String token, List<Desensity> desensityList) {
        this.token = token;
        this.desensityList = desensityList;
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
     * 脱敏配置项
     */
    public List<Desensity> getDesensityList() {
        return desensityList;
    }

    public void setDesensityList(List<Desensity> desensityList) {
        this.desensityList = desensityList;
    }
}
