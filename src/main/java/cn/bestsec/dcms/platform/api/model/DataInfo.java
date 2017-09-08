package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class DataInfo {
    private List<String> x;
    private List<LongPoint> y;
    
    public DataInfo() {
    }

    public DataInfo(List<String> x, List<LongPoint> y) {
        this.x = x;
        this.y = y;
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
