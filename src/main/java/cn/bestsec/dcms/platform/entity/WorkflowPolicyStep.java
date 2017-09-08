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
 * WorkflowPolicyStep generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)  
@Table(name = "WorkflowPolicyStep", catalog = "dcms_db")
public class WorkflowPolicyStep implements java.io.Serializable {

    private Integer id;
    private Role role;
    private Integer step;
    private Integer fkWorkFlowPolicyId;

    public WorkflowPolicyStep() {
    }

    public WorkflowPolicyStep(Role role, Integer step, Integer fkWorkFlowPolicyId) {
        this.role = role;
        this.step = step;
        this.fkWorkFlowPolicyId = fkWorkFlowPolicyId;
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
    @JoinColumn(name = "fkRoleId")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "step")
    public Integer getStep() {
        return this.step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Column(name = "fkWorkFlowPolicyId")
    public Integer getFkWorkFlowPolicyId() {
        return this.fkWorkFlowPolicyId;
    }

    public void setFkWorkFlowPolicyId(Integer fkWorkFlowPolicyId) {
        this.fkWorkFlowPolicyId = fkWorkFlowPolicyId;
    }

}