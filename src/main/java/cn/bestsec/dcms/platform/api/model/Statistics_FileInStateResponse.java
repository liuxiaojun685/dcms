package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_FileInStateResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<String> x;
    private List<LongPoint> y;
    
    public Statistics_FileInStateResponse() {
    }

    public Statistics_FileInStateResponse(List<String> x, List<LongPoint> y) {
        this.x = x;
        this.y = y;
    }

    public Statistics_FileInStateResponse(Integer code, String msg, List<String> x, List<LongPoint> y) {
        this.code = code;
        this.msg = msg;
        this.x = x;
        this.y = y;
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
     * 类型
     */
    public List<String> getX() {
        return x;
    }

    public void setX(List<String> x) {
        this.x = x;
    }

    /**
     * 数量
     */
    public List<LongPoint> getY() {
        return y;
    }

    public void setY(List<LongPoint> y) {
        this.y = y;
    }
}
