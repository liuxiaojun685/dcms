package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class Statistics_FileManyTrendResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<String> x;
    private List<FileTrendPoint> points;
    
    public Statistics_FileManyTrendResponse() {
    }

    public Statistics_FileManyTrendResponse(List<String> x, List<FileTrendPoint> points) {
        this.x = x;
        this.points = points;
    }

    public Statistics_FileManyTrendResponse(Integer code, String msg, List<String> x, List<FileTrendPoint> points) {
        this.code = code;
        this.msg = msg;
        this.x = x;
        this.points = points;
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
     * 类型 时间点
     */
    public List<String> getX() {
        return x;
    }

    public void setX(List<String> x) {
        this.x = x;
    }

    /**
     * 数据点
     */
    public List<FileTrendPoint> getPoints() {
        return points;
    }

    public void setPoints(List<FileTrendPoint> points) {
        this.points = points;
    }
}
