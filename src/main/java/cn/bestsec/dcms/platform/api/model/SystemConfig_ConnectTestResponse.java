package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_ConnectTestResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<String> resultList;
    
    public SystemConfig_ConnectTestResponse() {
    }

    public SystemConfig_ConnectTestResponse(List<String> resultList) {
        this.resultList = resultList;
    }

    public SystemConfig_ConnectTestResponse(Integer code, String msg, List<String> resultList) {
        this.code = code;
        this.msg = msg;
        this.resultList = resultList;
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
     * 检测结果
     */
    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}
