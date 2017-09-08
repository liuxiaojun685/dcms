package cn.bestsec.dcms.platform.consts;

/**
 * 流程审批状态 0未审批 1通过 2否决'
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月24日 下午2:11:29
 */
public class WorkflowTrackConsts {

    public enum state {
        notApproved(0, "未审批"), pass(1, "通过"), notPassed(2, "否决");
        private Integer code;
        private String description;

        private state(Integer code, String description) {
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
