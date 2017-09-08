package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class FlowTrackInfo {
    private Integer approveStep;
    private Integer approveState;
    private String approveComment;
    private Long approveTime;
    private UserSimpleInfo approveUser;
    
    public FlowTrackInfo() {
    }

    public FlowTrackInfo(Integer approveStep, Integer approveState, String approveComment, Long approveTime, UserSimpleInfo approveUser) {
        this.approveStep = approveStep;
        this.approveState = approveState;
        this.approveComment = approveComment;
        this.approveTime = approveTime;
        this.approveUser = approveUser;
    }

    /**
     * 审批级数(次序号)
     */
    public Integer getApproveStep() {
        return approveStep;
    }

    public void setApproveStep(Integer approveStep) {
        this.approveStep = approveStep;
    }

    /**
     * 审批状态 0未审批 1通过 2否决
     */
    public Integer getApproveState() {
        return approveState;
    }

    public void setApproveState(Integer approveState) {
        this.approveState = approveState;
    }

    /**
     * 审批意见
     */
    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment;
    }

    /**
     * 审批时间
     */
    public Long getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Long approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 审批人信息
     */
    public UserSimpleInfo getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(UserSimpleInfo approveUser) {
        this.approveUser = approveUser;
    }
}
