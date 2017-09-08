package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_QueryDesensityResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<Desensity> desensityList;
    
    public SystemConfig_QueryDesensityResponse() {
    }

    public SystemConfig_QueryDesensityResponse(List<Desensity> desensityList) {
        this.desensityList = desensityList;
    }

    public SystemConfig_QueryDesensityResponse(Integer code, String msg, List<Desensity> desensityList) {
        this.code = code;
        this.msg = msg;
        this.desensityList = desensityList;
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
     * 脱敏配置项
     */
    public List<Desensity> getDesensityList() {
        return desensityList;
    }

    public void setDesensityList(List<Desensity> desensityList) {
        this.desensityList = desensityList;
    }
}
