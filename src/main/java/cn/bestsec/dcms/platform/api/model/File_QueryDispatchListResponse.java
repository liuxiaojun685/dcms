package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryDispatchListResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<DRMInfo> drmList;
    
    public File_QueryDispatchListResponse() {
    }

    public File_QueryDispatchListResponse(List<DRMInfo> drmList) {
        this.drmList = drmList;
    }

    public File_QueryDispatchListResponse(Integer code, String msg, List<DRMInfo> drmList) {
        this.code = code;
        this.msg = msg;
        this.drmList = drmList;
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
     * 授权表
     */
    public List<DRMInfo> getDrmList() {
        return drmList;
    }

    public void setDrmList(List<DRMInfo> drmList) {
        this.drmList = drmList;
    }
}
