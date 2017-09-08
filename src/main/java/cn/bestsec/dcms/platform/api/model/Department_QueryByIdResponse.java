package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Department_QueryByIdResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private String did;
    private String name;
    private String description;
    private String parentName;
    
    public Department_QueryByIdResponse() {
    }

    public Department_QueryByIdResponse(String did, String name, String description, String parentName) {
        this.did = did;
        this.name = name;
        this.description = description;
        this.parentName = parentName;
    }

    public Department_QueryByIdResponse(Integer code, String msg, String did, String name, String description, String parentName) {
        this.code = code;
        this.msg = msg;
        this.did = did;
        this.name = name;
        this.description = description;
        this.parentName = parentName;
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
     * 部门描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 父部门名称
     */
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
