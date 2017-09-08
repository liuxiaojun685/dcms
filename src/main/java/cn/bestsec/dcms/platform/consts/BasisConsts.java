package cn.bestsec.dcms.platform.consts;

/**
* @author bada email:bada@bestsec.cn
* @time 2017年3月7日 上午11:04:37
*/
public class BasisConsts {
    public enum Type {

        nomal(0, "确定性"), undefined(1, "不明确"), incapable(2, "无权定密"), derived(3, "派生");

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
            for (Type c : values()) {
                if (c.getCode() == code) {
                    return c;
                }
            }
            return null;
        }
    }
}
