package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SystemConfig_UpdateWatermarkConfigRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer type;
    private Integer left;
    private Integer top;
    private Integer right;
    private Integer bottom;
    private Integer fontSize;
    private Integer colorA;
    private String colorRGB;
    private Integer fontEscapement;
    private Integer markLocal;
    private Integer markRows;
    private Integer markCols;
    private Integer compNameType;
    private Integer userNameType;
    private Integer hostAddrType;
    private String fontName;
    private String text;
    private Integer markTop;
    private Integer markLeft;
    private Integer markRight;
    private Integer markBottom;
    private Integer printTimeType;
    private List<Desensity> showWatermark;
    
    public SystemConfig_UpdateWatermarkConfigRequest() {
    }

    public SystemConfig_UpdateWatermarkConfigRequest(String token, Integer type, Integer left, Integer top, Integer right, Integer bottom, Integer fontSize, Integer colorA, String colorRGB, Integer fontEscapement, Integer markLocal, Integer markRows, Integer markCols, Integer compNameType, Integer userNameType, Integer hostAddrType, String fontName, String text, Integer markTop, Integer markLeft, Integer markRight, Integer markBottom, Integer printTimeType, List<Desensity> showWatermark) {
        this.token = token;
        this.type = type;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.fontSize = fontSize;
        this.colorA = colorA;
        this.colorRGB = colorRGB;
        this.fontEscapement = fontEscapement;
        this.markLocal = markLocal;
        this.markRows = markRows;
        this.markCols = markCols;
        this.compNameType = compNameType;
        this.userNameType = userNameType;
        this.hostAddrType = hostAddrType;
        this.fontName = fontName;
        this.text = text;
        this.markTop = markTop;
        this.markLeft = markLeft;
        this.markRight = markRight;
        this.markBottom = markBottom;
        this.printTimeType = printTimeType;
        this.showWatermark = showWatermark;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 1 打印，2屏幕
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 左页边距。[0,+)
     */
    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    /**
     * 上页边距
     */
    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    /**
     * 右页边距
     */
    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    /**
     * 下页边距
     */
    public Integer getBottom() {
        return bottom;
    }

    public void setBottom(Integer bottom) {
        this.bottom = bottom;
    }

    /**
     * 字体大小
     */
    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * 水印颜色-A通道
     */
    public Integer getColorA() {
        return colorA;
    }

    public void setColorA(Integer colorA) {
        this.colorA = colorA;
    }

    /**
     * 水印颜色-RGB
     */
    public String getColorRGB() {
        return colorRGB;
    }

    public void setColorRGB(String colorRGB) {
        this.colorRGB = colorRGB;
    }

    /**
     * 倾斜度
     */
    public Integer getFontEscapement() {
        return fontEscapement;
    }

    public void setFontEscapement(Integer fontEscapement) {
        this.fontEscapement = fontEscapement;
    }

    /**
     * 水印位置。0浮于内容上方，1衬于内容下方
     */
    public Integer getMarkLocal() {
        return markLocal;
    }

    public void setMarkLocal(Integer markLocal) {
        this.markLocal = markLocal;
    }

    /**
     * 水印行数
     */
    public Integer getMarkRows() {
        return markRows;
    }

    public void setMarkRows(Integer markRows) {
        this.markRows = markRows;
    }

    /**
     * 水印列数
     */
    public Integer getMarkCols() {
        return markCols;
    }

    public void setMarkCols(Integer markCols) {
        this.markCols = markCols;
    }

    /**
     * 计算机名的显示方式。0不选，1页眉，2页脚，4左侧，8右侧，16跟随水印
     */
    public Integer getCompNameType() {
        return compNameType;
    }

    public void setCompNameType(Integer compNameType) {
        this.compNameType = compNameType;
    }

    /**
     * 用户名的显示方式
     */
    public Integer getUserNameType() {
        return userNameType;
    }

    public void setUserNameType(Integer userNameType) {
        this.userNameType = userNameType;
    }

    /**
     * ip地址的显示方式
     */
    public Integer getHostAddrType() {
        return hostAddrType;
    }

    public void setHostAddrType(Integer hostAddrType) {
        this.hostAddrType = hostAddrType;
    }

    /**
     * 字体
     */
    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    /**
     * 水印内容
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 页眉。0左对齐，1居中，2右对齐，3不显示
     */
    public Integer getMarkTop() {
        return markTop;
    }

    public void setMarkTop(Integer markTop) {
        this.markTop = markTop;
    }

    /**
     * 页左。0上对齐，1居中，2下对齐，3不显示
     */
    public Integer getMarkLeft() {
        return markLeft;
    }

    public void setMarkLeft(Integer markLeft) {
        this.markLeft = markLeft;
    }

    /**
     * 页右。0上对齐，1居中，2下对齐，3不显示
     */
    public Integer getMarkRight() {
        return markRight;
    }

    public void setMarkRight(Integer markRight) {
        this.markRight = markRight;
    }

    /**
     * 页脚。0左对齐，1居中，2右对齐，3不显示
     */
    public Integer getMarkBottom() {
        return markBottom;
    }

    public void setMarkBottom(Integer markBottom) {
        this.markBottom = markBottom;
    }

    /**
     * 打印时间的显示方式。0不选，1页眉，2页脚，4左侧，8右侧，16跟随水印
     */
    public Integer getPrintTimeType() {
        return printTimeType;
    }

    public void setPrintTimeType(Integer printTimeType) {
        this.printTimeType = printTimeType;
    }

    /**
     * 配置显示屏幕水印密级
     */
    public List<Desensity> getShowWatermark() {
        return showWatermark;
    }

    public void setShowWatermark(List<Desensity> showWatermark) {
        this.showWatermark = showWatermark;
    }
}
