package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FileTrackNode {
    private String name;
    private String label;
    private String symbol;
    private Integer x;
    private Integer y;
    
    public FileTrackNode() {
    }

    public FileTrackNode(String name, String label, String symbol, Integer x, Integer y) {
        this.name = name;
        this.label = label;
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    /**
     * 节点连线依据
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 图形上的文本标签(文本名称)
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 图标的路径
     */
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 节点x坐标
     */
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * 节点y坐标
     */
    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
