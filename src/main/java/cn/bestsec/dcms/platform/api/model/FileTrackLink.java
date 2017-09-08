package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileTrackLink {
    private String source;
    private String target;
    private String label;
    
    public FileTrackLink() {
    }

    public FileTrackLink(String source, String target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    /**
     * 连线起点
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 连线终点
     */
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 连线标签
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
