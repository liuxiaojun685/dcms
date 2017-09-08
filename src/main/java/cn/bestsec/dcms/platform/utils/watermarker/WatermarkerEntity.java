package cn.bestsec.dcms.platform.utils.watermarker;

public class WatermarkerEntity {
    private int left = 1;
    private int top = 1;
    private int right = 1;
    private int bottom = 1;
    private int fontSize = 22;
    private int colorA = 78;
    private String colorR = "78";
    private String colorG = "78";
    private String colorB = "78";
    private int fontEscapement = -45;
    private int markLocal = 0;
    private int markRows = 6;
    private int markCols = 6;
    private int markTop = 3;
    private int markLeft = 3;
    private int markRight = 3;
    private int markBottom = 3;
    private int compNameType = 0;
    private int userNameType = 0;
    private int hostAddrType = 0;
    private int printTimeType = 0;
    private String fontName = "宋体";
    private String text = "";
    ///////////////////// 前端未使用////////////////////
    private int fontTilt = 2;
    private int markLayout = 0;
    private int markQuality = 0;
    private int markType = 0;
    private long printTime = 0L;
    private int custInfoType = 0;
    private String pictPath = "";
    private String compName = "";
    private String userName = "";
    private String hostAddr = "";
    private String custInfo = "";

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getColorA() {
        return colorA;
    }

    public void setColorA(int colorA) {
        this.colorA = colorA;
    }

    public String getColorR() {
        return colorR;
    }

    public void setColorR(String colorR) {
        this.colorR = colorR;
    }

    public String getColorG() {
        return colorG;
    }

    public void setColorG(String colorG) {
        this.colorG = colorG;
    }

    public String getColorB() {
        return colorB;
    }

    public void setColorB(String colorB) {
        this.colorB = colorB;
    }

    public int getFontEscapement() {
        return fontEscapement;
    }

    public void setFontEscapement(int fontEscapement) {
        this.fontEscapement = fontEscapement;
    }

    public int getFontTilt() {
        return fontTilt;
    }

    public void setFontTilt(int fontTilt) {
        this.fontTilt = fontTilt;
    }

    public int getMarkLocal() {
        return markLocal;
    }

    public void setMarkLocal(int markLocal) {
        this.markLocal = markLocal;
    }

    public int getMarkLayout() {
        return markLayout;
    }

    public void setMarkLayout(int markLayout) {
        this.markLayout = markLayout;
    }

    public int getMarkRows() {
        return markRows;
    }

    public void setMarkRows(int markRows) {
        this.markRows = markRows;
    }

    public int getMarkCols() {
        return markCols;
    }

    public void setMarkCols(int markCols) {
        this.markCols = markCols;
    }

    public int getMarkTop() {
        return markTop;
    }

    public void setMarkTop(int markTop) {
        this.markTop = markTop;
    }

    public int getMarkLeft() {
        return markLeft;
    }

    public void setMarkLeft(int markLeft) {
        this.markLeft = markLeft;
    }

    public int getMarkRight() {
        return markRight;
    }

    public void setMarkRight(int markRight) {
        this.markRight = markRight;
    }

    public int getMarkBottom() {
        return markBottom;
    }

    public void setMarkBottom(int markBottom) {
        this.markBottom = markBottom;
    }

    public int getMarkQuality() {
        return markQuality;
    }

    public void setMarkQuality(int markQuality) {
        this.markQuality = markQuality;
    }

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public long getPrintTime() {
        return printTime;
    }

    public void setPrintTime(long printTime) {
        this.printTime = printTime;
    }

    public int getCompNameType() {
        return compNameType;
    }

    public void setCompNameType(int compNameType) {
        this.compNameType = compNameType;
    }

    public int getUserNameType() {
        return userNameType;
    }

    public void setUserNameType(int userNameType) {
        this.userNameType = userNameType;
    }

    public int getHostAddrType() {
        return hostAddrType;
    }

    public void setHostAddrType(int hostAddrType) {
        this.hostAddrType = hostAddrType;
    }

    public int getCustInfoType() {
        return custInfoType;
    }

    public void setCustInfoType(int custInfoType) {
        this.custInfoType = custInfoType;
    }

    public int getPrintTimeType() {
        return printTimeType;
    }

    public void setPrintTimeType(int printTimeType) {
        this.printTimeType = printTimeType;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPictPath() {
        return pictPath;
    }

    public void setPictPath(String pictPath) {
        this.pictPath = pictPath;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHostAddr() {
        return hostAddr;
    }

    public void setHostAddr(String hostAddr) {
        this.hostAddr = hostAddr;
    }

    public String getCustInfo() {
        return custInfo;
    }

    public void setCustInfo(String custInfo) {
        this.custInfo = custInfo;
    }

}