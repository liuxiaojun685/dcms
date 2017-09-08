package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class StorageThreshold {
    private List<Long> free;
    
    public StorageThreshold() {
    }

    public StorageThreshold(List<Long> free) {
        this.free = free;
    }

    /**
     * 存储容量空闲率0-100 数组 下降到其中某值时触发
     */
    public List<Long> getFree() {
        return free;
    }

    public void setFree(List<Long> free) {
        this.free = free;
    }
}
