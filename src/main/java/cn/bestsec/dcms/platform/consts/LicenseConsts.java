package cn.bestsec.dcms.platform.consts;
/**
* @author 何勇 email:heyong@bestsec.cn
* @time：2016年12月26日 上午9:55:02
*/
public class LicenseConsts {
    public enum State {
        regular(1, "正式版"), trial(2, "试用版");

        private Integer code;
        private String description;

        private State(Integer code, String description) {
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
}
