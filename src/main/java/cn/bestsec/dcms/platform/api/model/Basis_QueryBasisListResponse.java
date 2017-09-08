package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_QueryBasisListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<BasisItem> basisList;
    
    public Basis_QueryBasisListResponse() {
    }

    public Basis_QueryBasisListResponse(List<BasisItem> basisList) {
        this.basisList = basisList;
    }

    public Basis_QueryBasisListResponse(Integer code, String msg, List<BasisItem> basisList) {
        this.code = code;
        this.msg = msg;
        this.basisList = basisList;
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
     * 定密依据列表
     */
    public List<BasisItem> getBasisList() {
        return basisList;
    }

    public void setBasisList(List<BasisItem> basisList) {
        this.basisList = basisList;
    }
}
