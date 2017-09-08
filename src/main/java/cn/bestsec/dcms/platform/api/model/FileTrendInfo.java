package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileTrendInfo {
    private List<String> x;
    private List<FileTrendPoint> points;
    
    public FileTrendInfo() {
    }

    public FileTrendInfo(List<String> x, List<FileTrendPoint> points) {
        this.x = x;
        this.points = points;
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
