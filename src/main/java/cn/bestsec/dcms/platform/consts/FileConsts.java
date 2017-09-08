package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 上午9:47:42
 */
public class FileConsts {
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

        public static Level parse(Integer code) {
            for (Level c : values()) {
                if (c.getCode() == code) {
                    return c;
                }
            }
            return open;
        }

    }

    public enum State {
        unknown(0, "非密标"), preMakeSecret(1, "预定密"), makeSecret(2, "正式定密"), dispatch(3, "已签发"), changeSecret(4,
                "密级变更"), unSecret(5, "已解密");

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

        public static State parse(Integer code) {
            for (State c : values()) {
                if (c.getCode() == code) {
                    return c;
                }
            }
            return unknown;
        }

    }

    public enum ToFileLevelDecideUnitType {
        main(1, "主要"), minor(0, "辅助");

        private Integer code;
        private String description;

        private ToFileLevelDecideUnitType(Integer code, String description) {
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

    public enum UploadType {
        pre(1, "预定密"), makeSecret(2, "正式定密"), dispatch(3, "文件签发"), changeSecret(4, "密级变更"), unSecret(5,
                "文件解密"), update(6, "更新"), restore(9, "密文还原");

        private Integer code;
        private String description;

        private UploadType(Integer code, String description) {
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

        public static UploadType parse(Integer code) {
            for (UploadType c : values()) {
                if (c.getCode() == code) {
                    return c;
                }
            }
            return pre;
        }
    }

    public enum DurationType {
        unlimit(0, "不限"), longterm(1, "长期"), period(2, "保密期限"), declassifiedTime(3, "解密日期"), declassifiedCondition(4,
                "解密条件");

        private Integer code;
        private String description;

        private DurationType(Integer code, String description) {
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

    public enum downLoadType {
        attachment(1, "附件下载"), fileDownload(2, "文件下载");

        private Integer code;
        private String description;

        private downLoadType(Integer code, String description) {
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
