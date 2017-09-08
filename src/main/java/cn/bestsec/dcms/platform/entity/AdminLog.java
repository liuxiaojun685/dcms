package cn.bestsec.dcms.platform.entity;
// Generated 2017-3-22 10:33:46 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AdminLog generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)  
@Table(name = "AdminLog", catalog = "dcms_db")
public class AdminLog implements java.io.Serializable {

    private Integer id;
    private Admin admin;
    private Long createTime;
    private Long operateTime;
    private String operateType;
    private String operateDescription;
    private Integer operateResult;
    private String ip;
    private String reserve;

    public AdminLog() {
    }

    public AdminLog(Admin admin, Long createTime, Long operateTime, String operateType, String operateDescription,
            Integer operateResult, String ip, String reserve) {
        this.admin = admin;
        this.createTime = createTime;
        this.operateTime = operateTime;
        this.operateType = operateType;
        this.operateDescription = operateDescription;
        this.operateResult = operateResult;
        this.ip = ip;
        this.reserve = reserve;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkAid")
    public Admin getAdmin() {
        return this.admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Column(name = "createTime")
    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Column(name = "operateTime")
    public Long getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    @Column(name = "operateType", length = 256, columnDefinition = "")
    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Column(name = "operateDescription", length = 256, columnDefinition = "")
    public String getOperateDescription() {
        return this.operateDescription;
    }

    public void setOperateDescription(String operateDescription) {
        this.operateDescription = operateDescription;
    }

    @Column(name = "operateResult")
    public Integer getOperateResult() {
        return this.operateResult;
    }

    public void setOperateResult(Integer operateResult) {
        this.operateResult = operateResult;
    }

    @Column(name = "ip", length = 256, columnDefinition = "")
    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "reserve", length = 256, columnDefinition = "")
    public String getReserve() {
        return this.reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

}