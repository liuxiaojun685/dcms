package cn.bestsec.dcms.platform.entity;
// Generated 2017-3-22 10:33:46 by Hibernate Tools 4.3.5.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StorageLocation generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)  
@Table(name = "StatisticsFile", catalog = "dcms_db")
public class StatisticsFile implements java.io.Serializable {

    private Integer id;
    private Long createTime;
    private Long classifiedNum;
    private Long issuedNum;
    private Long classifiedChangeNum;
    private Long declassifiedNum;
    private Long outsourceNum;

    public StatisticsFile() {
    }

    public StatisticsFile(Long createTime, Long classifiedNum, Long issuedNum, Long classifiedChangeNum,
            Long declassifiedNum, Long outsourceNum) {
        super();
        this.createTime = createTime;
        this.classifiedNum = classifiedNum;
        this.issuedNum = issuedNum;
        this.classifiedChangeNum = classifiedChangeNum;
        this.declassifiedNum = declassifiedNum;
        this.outsourceNum = outsourceNum;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "createTime")
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Column(name = "classifiedNum")
    public Long getClassifiedNum() {
        return classifiedNum;
    }

    public void setClassifiedNum(Long classifiedNum) {
        this.classifiedNum = classifiedNum;
    }

    @Column(name = "issuedNum")
    public Long getIssuedNum() {
        return issuedNum;
    }

    public void setIssuedNum(Long issuedNum) {
        this.issuedNum = issuedNum;
    }

    @Column(name = "classifiedChangeNum")
    public Long getClassifiedChangeNum() {
        return classifiedChangeNum;
    }

    public void setClassifiedChangeNum(Long classifiedChangeNum) {
        this.classifiedChangeNum = classifiedChangeNum;
    }

    @Column(name = "declassifiedNum")
    public Long getDeclassifiedNum() {
        return declassifiedNum;
    }

    public void setDeclassifiedNum(Long declassifiedNum) {
        this.declassifiedNum = declassifiedNum;
    }

    @Column(name = "outsourceNum")
    public Long getOutsourceNum() {
        return outsourceNum;
    }

    public void setOutsourceNum(Long outsourceNum) {
        this.outsourceNum = outsourceNum;
    }

}
