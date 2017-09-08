package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Role_QueryTreeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<RoleTypeInfo> roleTypeList;
    
    public Role_QueryTreeResponse() {
    }

    public Role_QueryTreeResponse(List<RoleTypeInfo> roleTypeList) {
        this.roleTypeList = roleTypeList;
    }

    public Role_QueryTreeResponse(Integer code, String msg, List<RoleTypeInfo> roleTypeList) {
        this.code = code;
        this.msg = msg;
        this.roleTypeList = roleTypeList;
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
     * 按角色类型分组
     */
    public List<RoleTypeInfo> getRoleTypeList() {
        return roleTypeList;
    }

    public void setRoleTypeList(List<RoleTypeInfo> roleTypeList) {
        this.roleTypeList = roleTypeList;
    }
}
