package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileTrendPoint {
    private List<LongPoint> y;
    private String z;
    
    public FileTrendPoint() {
    }

    public FileTrendPoint(List<LongPoint> y, String z) {
        this.y = y;
        this.z = z;
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

    /**
     * 类型 文件操作
     */
    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }
}
