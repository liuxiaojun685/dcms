package cn.bestsec.dcms.platform.consts;

/**
 * 部门所需枚举数据
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 上午11:05:44
 */
public class DepartmentConsts {

    public enum state {
        deleted(1, "已删除"), undelete(0, "未删除"), rootdoor(1, "根部门");
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
