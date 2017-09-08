package cn.bestsec.dcms.platform.consts;

/**
 * 查询个人文件台账
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月6日 下午12:36:43
 */
public enum FilterEnum {

    inbox(1, "文件收件箱"), draftbox(2, "拟稿箱"), decrypt(3, "待解密"), declassified(4, "已解密"), jurisdiction(5, "管辖范围");
    private Integer code;
    private String description;

    private FilterEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
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
