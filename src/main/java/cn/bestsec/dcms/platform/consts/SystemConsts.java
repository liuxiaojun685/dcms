package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 上午9:50:24
 */
public class SystemConsts {
    public enum Level {
        pub(0, "公开"), inside(1, "内部"), secret(2, "秘密"), confidential(3, "机密"), topSecret(4, "绝密");
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

    /**
     * 1公开文件上传 2内部文件上传 3秘密文件上传 4机密文件上传 5绝密文件上传 6备份 7日志上传 8系统资源告警 9日志存储告警
     * 10文件存储告警 11AD连接 12补丁上传 13配置定密责任人 14配置文件签发人 15配置流程策略
     * 
     * @author 刘强 email:liuqiang@bestsec.cn
     * @time 2017年6月30日 下午5:22:49
     */
    public enum ConnectTest {
        pubUpload(1, "公开文件上传"), insideUpload(2, "内部文件上传"), secretUpload(3, "秘密文件上传"), confidentialUpload(4,
                "机密文件上传"), topSecretUpload(5, "绝密文件上传"), backup(6, "备份"), logUpload(7, "日志上传"), systemResourse(8,
                        "系统资源告警"), logLocation(9,
                                "日志存储告警"), fileLocation(10, "文件存储告警"), adConnect(11, "AD连接"), clientPatch(12, "补丁上传"), makeSecret(13, "配置定密责任人"), dispatchman(14, "配置文件签发人"), workflowPolicy(15, "配置流程策略");
        private Integer code;
        private String description;

        private ConnectTest(Integer code, String description) {
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
