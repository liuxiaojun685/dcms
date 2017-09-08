package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_QueryScopeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<ScopeInfo> scopeList;
    
    public Role_QueryScopeResponse() {
    }

    public Role_QueryScopeResponse(List<ScopeInfo> scopeList) {
        this.scopeList = scopeList;
    }

    public Role_QueryScopeResponse(Integer code, String msg, List<ScopeInfo> scopeList) {
        this.code = code;
        this.msg = msg;
        this.scopeList = scopeList;
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
     * 负责的范围
     */
    public List<ScopeInfo> getScopeList() {
        return scopeList;
    }

    public void setScopeList(List<ScopeInfo> scopeList) {
        this.scopeList = scopeList;
    }
}
