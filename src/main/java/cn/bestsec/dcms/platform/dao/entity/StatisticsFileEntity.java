package cn.bestsec.dcms.platform.dao.entity;

/**
 * 统计辅助类
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月13日 下午2:58:52
 */
public class StatisticsFileEntity {

    private String time;
    private Long classified;
    private Long issued;
    private Long classifiedChange;
    private Long declassified;

    public StatisticsFileEntity() {
        super();
    }

    public StatisticsFileEntity(String time, Long classified, Long issued, Long classifiedChange, Long declassified) {
        super();
        this.time = time;
        this.classified = classified;
        this.issued = issued;
        this.classifiedChange = classifiedChange;
        this.declassified = declassified;
    }

    public String getTime() {
        return time;
    }

    public Long getClassified() {
        return classified;
    }

    public Long getIssued() {
        return issued;
    }

    public Long getClassifiedChange() {
        return classifiedChange;
    }

    public Long getDeclassified() {
        return declassified;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setClassified(Long classified) {
        this.classified = classified;
    }

    public void setIssued(Long issued) {
        this.issued = issued;
    }

    public void setClassifiedChange(Long classifiedChange) {
        this.classifiedChange = classifiedChange;
    }

    public void setDeclassified(Long declassified) {
        this.declassified = declassified;
    }

}
