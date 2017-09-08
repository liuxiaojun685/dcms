package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_CheckRoleScopeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<ScopeInfo> checkList;
    
    public Role_CheckRoleScopeResponse() {
    }

    public Role_CheckRoleScopeResponse(List<ScopeInfo> checkList) {
        this.checkList = checkList;
    }

    public Role_CheckRoleScopeResponse(Integer code, String msg, List<ScopeInfo> checkList) {
        this.code = code;
        this.msg = msg;
        this.checkList = checkList;
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
     * 检测出缺少责任人的组织部门
     */
    public List<ScopeInfo> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<ScopeInfo> checkList) {
        this.checkList = checkList;
    }
}
