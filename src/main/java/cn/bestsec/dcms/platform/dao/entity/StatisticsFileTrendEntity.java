package cn.bestsec.dcms.platform.dao.entity;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月20日 下午6:16:40
 */
public class StatisticsFileTrendEntity {
    public String time;
    public Long classifiedNum;
    public Long issuedNum;
    public Long classifiedChangeNum;
    public Long declassifiedNum;
    public Long outsourceNum;

    public StatisticsFileTrendEntity(String time, Long classifiedNum, Long issuedNum, Long classifiedChangeNum,
            Long declassifiedNum, Long outsourceNum) {
        this.time = time;
        this.classifiedNum = classifiedNum;
        this.issuedNum = issuedNum;
        this.classifiedChangeNum = classifiedChangeNum;
        this.declassifiedNum = declassifiedNum;
        this.outsourceNum = outsourceNum;
    }
}
