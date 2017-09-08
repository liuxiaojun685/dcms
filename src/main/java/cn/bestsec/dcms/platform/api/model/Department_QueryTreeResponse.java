package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Department_QueryTreeResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String did;
    private String name;
    private List<DepartmentNode> childDepartmentList;
    private List<GroupNode> childGroupList;
    private List<UserSimpleInfo> childUserList;
    
    public Department_QueryTreeResponse() {
    }

    public Department_QueryTreeResponse(String did, String name, List<DepartmentNode> childDepartmentList, List<GroupNode> childGroupList, List<UserSimpleInfo> childUserList) {
        this.did = did;
        this.name = name;
        this.childDepartmentList = childDepartmentList;
        this.childGroupList = childGroupList;
        this.childUserList = childUserList;
    }

    public Department_QueryTreeResponse(Integer code, String msg, String did, String name, List<DepartmentNode> childDepartmentList, List<GroupNode> childGroupList, List<UserSimpleInfo> childUserList) {
        this.code = code;
        this.msg = msg;
        this.did = did;
        this.name = name;
        this.childDepartmentList = childDepartmentList;
        this.childGroupList = childGroupList;
        this.childUserList = childUserList;
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
     * 部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    /**
     * 部门名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 子部门数组
     */
    public List<DepartmentNode> getChildDepartmentList() {
        return childDepartmentList;
    }

    public void setChildDepartmentList(List<DepartmentNode> childDepartmentList) {
        this.childDepartmentList = childDepartmentList;
    }

    /**
     * 子组名数组
     */
    public List<GroupNode> getChildGroupList() {
        return childGroupList;
    }

    public void setChildGroupList(List<GroupNode> childGroupList) {
        this.childGroupList = childGroupList;
    }

    /**
     * 子用户姓名数组
     */
    public List<UserSimpleInfo> getChildUserList() {
        return childUserList;
    }

    public void setChildUserList(List<UserSimpleInfo> childUserList) {
        this.childUserList = childUserList;
    }
}
