package cn.bestsec.dcms.platform.consts;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月15日 上午10:43:05
 */
public enum ResultType {
    success(1, "成功"), failed(2, "失败"), running(0, "正在运行");
    private Integer code;
    private String description;
    private ResultType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ResultType parse(Integer code) {
        for (ResultType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
