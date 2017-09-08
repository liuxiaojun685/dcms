package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class ResourceThreshold {
    private List<Long> cpuFree;
    private List<Long> memFree;
    private List<Long> hdFree;
    
    public ResourceThreshold() {
    }

    public ResourceThreshold(List<Long> cpuFree, List<Long> memFree, List<Long> hdFree) {
        this.cpuFree = cpuFree;
        this.memFree = memFree;
        this.hdFree = hdFree;
    }

    /**
     * cpu使用率0-100 数组 上升到其中某值时触发
     */
    public List<Long> getCpuFree() {
        return cpuFree;
    }

    public void setCpuFree(List<Long> cpuFree) {
        this.cpuFree = cpuFree;
    }

    /**
     * 内存空闲率0-100 数组 下降到其中某值时触发
     */
    public List<Long> getMemFree() {
        return memFree;
    }

    public void setMemFree(List<Long> memFree) {
        this.memFree = memFree;
    }

    /**
     * 硬盘空闲率0-100 数组 下降到其中某值时触发
     */
    public List<Long> getHdFree() {
        return hdFree;
    }

    public void setHdFree(List<Long> hdFree) {
        this.hdFree = hdFree;
    }
}
