package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 上午9:26:55
 */
public class AdminConsts {
    public enum Type {
        logadmin(1, "安全审计员"), sysadmin(2, "系统管理员"), secadmin(3, "安全保密管理员");
        private Integer code;
        private String description;

        private Type(Integer code, String description) {
            this.code = code;
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

    public enum From {
        inner(1, "内置"), manual(2, "派生");
        private Integer code;
        private String description;

        private From(Integer code, String description) {
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

    public enum LogOperateResult {
        success(1, "成功"), failure(2, "失败");

        private Integer code;
        private String description;

        private LogOperateResult(Integer code, String description) {
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

    public enum Derive {
        yes(1, "是"), no(0, "否");
        private Integer code;
        private String description;

        private Derive(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public enum AdminState {
        undelete(0, "未删除"), deleted(1, "已删除");
        private Integer code;
        private String description;

        private AdminState(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

}
