package cn.bestsec.dcms.platform.dao.entity;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月14日 下午3:53:59
 */
public class StatisticsWorkflowEntity {
    Long finish;
    Long unfinish;

    public StatisticsWorkflowEntity() {
    }

    public StatisticsWorkflowEntity(Long finish, Long unfinish) {
        this.finish = finish;
        this.unfinish = unfinish;
    }

    public Long getFinish() {
        return finish;
    }

    public void setFinish(Long finish) {
        this.finish = finish;
    }

    public Long getUnfinish() {
        return unfinish;
    }

    public void setUnfinish(Long unfinish) {
        this.unfinish = unfinish;
    }

}
