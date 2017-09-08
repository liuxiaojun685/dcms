package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 上午9:44:26
 */
public class ClientConsts {
    public enum Level {

        open(0, "公开"), inside(1, "内部"), secret(2, "秘密"), confidential(3, "机密"), topSecret(4, "绝密");

        private Integer code;
        private String description;

        private Level(Integer code, String description) {
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

    public enum PatchVersionType {
        network(1, "成功"), offline(2, "失败");

        private Integer code;
        private String description;

        private PatchVersionType(Integer code, String description) {
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

    public enum RequestLogOperateResult {
        success(1, "成功"), failure(2, "失败");

        private Integer code;
        private String description;

        private RequestLogOperateResult(Integer code, String description) {
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

    public enum State {
        deleted(1, "已删除"), locked(2, "已锁定");

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

    public enum VersionType {
        network(1, "网络版"), offline(2, "单机版");

        private Integer code;
        private String description;

        private VersionType(Integer code, String description) {
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

    public enum PolicyType {
        all(0, "全部策略"), trustApp(1, "可信应用策略"), accessFile(2, "可访问文件策略"), markKey(3, "标志密钥策略"), classExtension(4,
                "可标密的文件拓展名策略"), levelAccessPolicy(5, "文件密级访问策略"), otherPolicy(6, "其他策略"), classifiedWhiteList(7, "标密白名单");

        public Integer code;
        public String description;

        private PolicyType(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public static PolicyType valueOf(Integer code) {
            for (PolicyType c : values()) {
                if (c.code == code) {
                    return c;
                }
            }
            return all;
        }
    }
}
