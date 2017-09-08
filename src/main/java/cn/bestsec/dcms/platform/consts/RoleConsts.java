package cn.bestsec.dcms.platform.consts;

/**
* @author 何勇 email:heyong@bestsec.cn
* @time：2016年12月26日 上午9:51:23
*/
public class RoleConsts {
    public enum Type {
        draftsman(1, "文件起草人"), makeSecret(2, "定密责任人"), dispatchman(3, "文件签发人"), user(4, "分发使用人"), unsecret(5,
                "解绑人(特权)"), logOn(7, "签入人(特权)"), logOff(8, "签出人(特权)");

        private Integer code;
        private String description;

        private Type(Integer code, String description) {
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
        
        public static Type parse(Integer code) {
            for (Type type : values()) {
                if (type.getCode() == code) {
                    return type;
                }
            }
            return null;
        }

    }

}
