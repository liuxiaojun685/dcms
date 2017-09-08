package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_QueryRoleTypeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<Integer> roleTypes;
    
    public User_QueryRoleTypeResponse() {
    }

    public User_QueryRoleTypeResponse(List<Integer> roleTypes) {
        this.roleTypes = roleTypes;
    }

    public User_QueryRoleTypeResponse(Integer code, String msg, List<Integer> roleTypes) {
        this.code = code;
        this.msg = msg;
        this.roleTypes = roleTypes;
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
     * 角色类型 0默认 2定密责任人 3文件签发人 7签入人(特权) 8签出人(特权)
     */
    public List<Integer> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(List<Integer> roleTypes) {
        this.roleTypes = roleTypes;
    }
}
