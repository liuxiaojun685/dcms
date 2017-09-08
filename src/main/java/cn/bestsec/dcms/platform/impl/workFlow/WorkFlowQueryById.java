package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.api.WorkFlow_QueryByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.BasisInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeInfo;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.FlowTrackInfo;
import cn.bestsec.dcms.platform.api.model.PermissionBaseInfo;
import cn.bestsec.dcms.platform.api.model.UnitInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryByIdRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryByIdResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 查询流程信息
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月20日下午4:02:18
 */
@Component
public class WorkFlowQueryById implements WorkFlow_QueryByIdApi {
    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    @Transactional
    public WorkFlow_QueryByIdResponse execute(WorkFlow_QueryByIdRequest workFlow_QueryByIdRequest) throws ApiException {
        Workflow workflow = workFlowDao.findById(workFlow_QueryByIdRequest.getWorkFlowId());
        if (workflow == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<BasisInfo> basis = JSON.parseArray(workflow.getApplyBasis(), BasisInfo.class);
        List<FileScopeInfo> fileScope = JSON.parseArray(workflow.getApplyDispatch(), FileScopeInfo.class);

        UnitInfo majorUnit = JSON.parseObject(workflow.getApplyMajorUnit(), UnitInfo.class);
        List<UnitInfo> minorUnit = JSON.parseArray(workflow.getApplyMinorUnit(), UnitInfo.class);
        PermissionBaseInfo permission = JSON.parseObject(workflow.getPermission(), PermissionBaseInfo.class);

        // 流程发起人
        User user = workflow.getUser();
        UserSimpleInfo userSimpleInfo = new UserSimpleInfo();
        userSimpleInfo.setAccount(user.getAccount());
        userSimpleInfo.setLevel(user.getUserLevel());
        userSimpleInfo.setName(user.getName());
        userSimpleInfo.setOnline(UserConsts.userOnline(user));
        userSimpleInfo.setUid(user.getPkUid());

        // 流程轨迹详情
        List<WorkflowTrack> workflowTracks = workflow.getWorkflowTracks();
        List<FlowTrackInfo> flowTrackInfos = new ArrayList<FlowTrackInfo>();
        FlowTrackInfo flowTrackInfo = null;
        UserSimpleInfo approveUser = null;
        for (WorkflowTrack workflowTrack : workflowTracks) {
            flowTrackInfo = new FlowTrackInfo();
            flowTrackInfo.setApproveComment(workflowTrack.getApproveComment());
            flowTrackInfo.setApproveState(workflowTrack.getApproveState());
            flowTrackInfo.setApproveStep(workflowTrack.getApproveStep());
            flowTrackInfo.setApproveTime(workflowTrack.getApproveTime());
            approveUser = new UserSimpleInfo();
            User approve = workflowTrack.getUser();
            if (approve != null) {

                approveUser.setAccount(approve.getAccount());
                approveUser.setLevel(approve.getUserLevel());
                approveUser.setName(approve.getName());
                approveUser.setOnline(UserConsts.userOnline(approve));
                approveUser.setUid(approve.getPkUid());
            }
            flowTrackInfo.setApproveUser(approveUser);
            flowTrackInfos.add(flowTrackInfo);
        }

        // 源文件
        FileSimpleInfo srcFile = new FileSimpleInfo();
        File fileByFkSrcFid = fileDao.findByPkFid(workflow.getFkSrcFid());
        String fileName = workflow.getFileName();

        // 如果是安全管理员
        Token token = tokenDao.findByToken(workFlow_QueryByIdRequest.getToken());
        if (token.getAdmin() != null) {
            if (logArchiveService.isFileDesensity(workflow.getApplyFileLevel())) {
                fileName = TextUtils.getDealWithName(fileName);
            }
        }

        srcFile.setFid(workflow.getFkSrcFid());
        srcFile.setFileName(fileName);
        if (fileByFkSrcFid != null) {
            srcFile.setFileLevel(fileByFkSrcFid.getFileLevel());
            srcFile.setUrgency(fileByFkSrcFid.getUrgency());
        }

        WorkFlow_QueryByIdResponse resp = new WorkFlow_QueryByIdResponse(workflow.getId(), workflow.getWorkFlowType(),
                srcFile, null, workflow.getApplyFileLevel(), workflow.getFlowState(), workflow.getCreateTime(),
                userSimpleInfo, workflow.getUrgency(), workflow.getApplyValidPeriod(), workflow.getDurationType(),
                workflow.getDurationDescription(), workflow.getFileDecryptTime(), fileScope,
                workflow.getApplyDispatchDesc(), basis, workflow.getApplyBasisType(), workflow.getApplyBasisDesc(),
                workflow.getIssueNumber(), workflow.getDocNumber(), workflow.getDuplicationAmount(), majorUnit,
                minorUnit, workflow.getCreateComment(), workflow.getTotalStep(), workflow.getCurrentStep(),
                flowTrackInfos, permission, workflow.getMarkVersion(), workflow.getBusiness());

        return resp;
    }

}
