package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_QueryBasisClassListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<BasisClassItem> basisClassList;
    
    public Basis_QueryBasisClassListResponse() {
    }

    public Basis_QueryBasisClassListResponse(List<BasisClassItem> basisClassList) {
        this.basisClassList = basisClassList;
    }

    public Basis_QueryBasisClassListResponse(Integer code, String msg, List<BasisClassItem> basisClassList) {
        this.code = code;
        this.msg = msg;
        this.basisClassList = basisClassList;
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
     * 定密依据分类列表
     */
    public List<BasisClassItem> getBasisClassList() {
        return basisClassList;
    }

    public void setBasisClassList(List<BasisClassItem> basisClassList) {
        this.basisClassList = basisClassList;
    }
}
