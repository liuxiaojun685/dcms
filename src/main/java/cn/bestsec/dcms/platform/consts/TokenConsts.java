package cn.bestsec.dcms.platform.consts;

/**
 * @author 徐泽威 email:xzw@bestsec.cn
 * @time：2016年12月26日 下午3:37:20
 */
public class TokenConsts {
    public enum LoginFrom {
        web(1, "Web后台"), client(2, "终端");

        private Integer code;
        private String description;

        private LoginFrom(Integer code, String description) {
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

    public enum UserRole {
        logadmin(1, "安全审计员"), sysadmin(2, "系统管理员"), secadmin(3, "安全保密管理员"), anyadmin(4, "任意管理员"), user(5,
                "终端用户"), any(6, "任意"), nocheck(0, "免token校验"), middleware(7, "中间件对接");

        private Integer code;
        private String description;

        private UserRole(Integer code, String description) {
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
