package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 上午9:52:20
 */
public class WorkFlowConsts {
    public enum Type {
        makeSecret(2, "正式定密流程"), dispatch(3, "文件签发流程"), changeSecret(4, "密级变更流程"), unSecret(5, "文件解密流程"), restore(9,
                "密文还原流程");

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

    public enum State {
        complete(1, "完成"), notcomplete(0, "未完成");
        private Integer code;
        private String flowState;

        private State(Integer code, String flowState) {
            this.code = code;
            this.flowState = flowState;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getFlowState() {
            return flowState;
        }

        public void setFlowState(String flowState) {
            this.flowState = flowState;
        }

    }
}
