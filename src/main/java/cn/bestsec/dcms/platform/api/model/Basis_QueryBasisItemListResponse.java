package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Basis_QueryBasisItemListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<BasisItemItem> basisItemList;
    
    public Basis_QueryBasisItemListResponse() {
    }

    public Basis_QueryBasisItemListResponse(List<BasisItemItem> basisItemList) {
        this.basisItemList = basisItemList;
    }

    public Basis_QueryBasisItemListResponse(Integer code, String msg, List<BasisItemItem> basisItemList) {
        this.code = code;
        this.msg = msg;
        this.basisItemList = basisItemList;
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
     * 定密依据事项列表
     */
    public List<BasisItemItem> getBasisItemList() {
        return basisItemList;
    }

    public void setBasisItemList(List<BasisItemItem> basisItemList) {
        this.basisItemList = basisItemList;
    }
}
