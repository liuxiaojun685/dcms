package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 上午9:57:11
 */
public class BackupHistoryConsts {
    public enum CreateFrom {
        manual(1, "手动"), auto(2, "自动");

        private Integer code;
        private String description;

        private CreateFrom(Integer code, String description) {
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
