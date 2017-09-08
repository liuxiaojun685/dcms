package cn.bestsec.dcms.platform.impl.workFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.api.WorkFlow_ApprovalApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.ClassificationInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeItem;
import cn.bestsec.dcms.platform.api.model.WorkFlow_ApprovalRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_ApprovalResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.consts.WorkflowTrackConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;
import cn.bestsec.dcms.platform.mqtt.MqttMessageHandler;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 流程审批
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日上午10:53:19
 */
@Component
public class WorkFlowApproval implements WorkFlow_ApprovalApi {

    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private WorkflowTrackDao workFlowTrackDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    @Transactional
    public WorkFlow_ApprovalResponse execute(WorkFlow_ApprovalRequest workFlow_ApprovalRequest) throws ApiException {
        Token token = tokenDao.findByToken(workFlow_ApprovalRequest.getToken());
        User user = token.getUser();

        long currentTime = System.currentTimeMillis();

        // 找到审批的流程
        Workflow workFlow = workFlowDao.findById(workFlow_ApprovalRequest.getWorkFlowId());
        if (workFlow == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        // 取到待审核级数
        Integer currentStep = workFlow.getCurrentStep();
        // 判断是否轮到该审批员
        List<WorkflowTrack> tracks = workFlowTrackDao.findByApproveStepAndWorkflowId(currentStep, workFlow.getId());
        WorkflowTrack workflowTrack = null;
        for (WorkflowTrack track : tracks) {
            if (track.getUser() == user) {
                workflowTrack = track;
                break;
            }
        }
        if (workflowTrack == null) {
            throw new ApiException(ErrorCode.operationNotPermitted);
        }

        // 审批的文件密级
        workFlow.setApplyFileLevel(workFlow_ApprovalRequest.getApproveLevel());
        // 审批的保密期限
        workFlow.setDurationType(workFlow_ApprovalRequest.getDurationType());
        workFlow.setApplyValidPeriod(workFlow_ApprovalRequest.getApproveValidPeriod());
        workFlow.setFileDecryptTime(workFlow_ApprovalRequest.getFileDecryptTime());
        workFlow.setDurationDescription(workFlow_ApprovalRequest.getDurationDescription());
        // 权限
        workFlow.setPermission(JSON.toJSONString(workFlow_ApprovalRequest.getPermission()));
        // 文件的签发范围
        List<FileScopeItem> scos = workFlow_ApprovalRequest.getFileScope();
        List<FileScopeInfo> scope = new ArrayList<>();
        for (FileScopeItem sco : scos) {
            FileLevelDecideUnit major = fileLevelDecideUnitDao.findByUnitNo(sco.getUnitNo());
            if (major != null && major.getMajor() == 1) {
                FileScopeInfo info = new FileScopeInfo(sco.getUid(), null, null, null, null, sco.getUnitNo());
                User user1 = userDao.findByPkUid(sco.getUid());
                if (user1 != null) {
                    info.setAccount(user1.getAccount());
                    info.setLevel(user1.getUserLevel());
                    info.setName(user1.getName());
                    info.setOnline(UserConsts.userOnline(user1));
                }
                scope.add(info);
            }
        }
        workFlow.setApplyDispatch(JSON.toJSONString(scope));
        workFlow.setApplyDispatchDesc(workFlow_ApprovalRequest.getFileScopeDesc());
        // 审批的定密依据
        if (workFlow_ApprovalRequest.getBasisType() != null) {
            workFlow.setApplyBasis(JSON.toJSONString(workFlow_ApprovalRequest.getApproveBasis()));
            workFlow.setApplyBasisType(workFlow_ApprovalRequest.getBasisType());
            workFlow.setApplyBasisDesc(workFlow_ApprovalRequest.getBasisDesc());
        }
        // 定密单位
        workFlow.setApplyMajorUnit(JSON.toJSONString(workFlow_ApprovalRequest.getMajorUnit()));
        workFlow.setApplyMinorUnit(JSON.toJSONString(workFlow_ApprovalRequest.getApproveMinorUnit()));

        workFlow.setMarkVersion(workFlow_ApprovalRequest.getMarkVersion());
        workFlow.setUrgency(workFlow_ApprovalRequest.getUrgency());

        workflowTrack.setApproveState(workFlow_ApprovalRequest.getApproveState());
        workflowTrack.setApproveTime(currentTime);
        workflowTrack.setApproveComment(workFlow_ApprovalRequest.getApproveComment());
        workFlowTrackDao.save(workflowTrack);
        File file = fileDao.findByPkFid(workFlow.getFkSrcFid());
        // 如果审批人不通过，则流程结束，否则流程正常结束
        if (workFlow_ApprovalRequest.getApproveState() == WorkflowTrackConsts.state.notPassed.getCode()) {
            workFlow.setFlowState(WorkFlowConsts.State.complete.getCode());
            workFlow.setFlowResult(WorkflowTrackConsts.state.notPassed.getCode());
            workFlow.setCurrentStep(0);
            MqttMessageHandler.tryPublish(workFlow.getUser().getPkUid(), MqttMessageHandler.TYPE_WORKFLOW,
                    MqttMessageHandler.MSG_WORKFLOW_BACK + "  [" + workFlow.getFileName() + "]",
                    MqttMessageHandler.URL_WORKFLOW + "?workflowId=" + workFlow.getId());
        }
        if (workFlow_ApprovalRequest.getApproveState() == WorkflowTrackConsts.state.pass.getCode()) {
            // 是否流程最后一步（级）
            if (workflowTrack.getApproveStep() == workFlow.getTotalStep()) {
                workFlow.setFlowState(WorkFlowConsts.State.complete.getCode());
                workFlow.setFlowResult(WorkflowTrackConsts.state.pass.getCode());
                // workFlowDao.save(workFlow);

                // 源文件
                String cacheDir = SystemProperties.getInstance().getCacheDir() + java.io.File.separator + "workflow";

                java.io.File attachment = new java.io.File(cacheDir + java.io.File.separator + workFlow.getId());
                ClassificationInfo prop = new ClassificationInfo(workFlow.getFkSrcFid(), workFlow.getFileName(),
                        workFlow.getUrgency(), workFlow.getApplyFileLevel(), workFlow.getApplyValidPeriod(),
                        workFlow.getDurationType(), workFlow.getDurationDescription(), workFlow.getFileDecryptTime(),
                        workFlow_ApprovalRequest.getFileScope(), workFlow.getApplyDispatchDesc(),
                        workFlow_ApprovalRequest.getApproveBasis(), workFlow.getApplyBasisType(),
                        workFlow.getApplyBasisDesc(), workFlow.getIssueNumber(), workFlow.getDocNumber(),
                        workFlow.getDuplicationAmount(), workFlow_ApprovalRequest.getMajorUnit(),
                        workFlow_ApprovalRequest.getApproveMinorUnit(), workFlow_ApprovalRequest.getPermission(),
                        workFlow.getMarkVersion(), workFlow.getCreateComment(), workFlow.getBusiness());

                // 流程最后一次的审批人----- 正式定密
                User approveUser = user;
                java.io.File target = null;
                Integer lastState = file.getFileState();
                if (workFlow.getWorkFlowType() == WorkFlowConsts.Type.makeSecret.getCode()) {
                    if (lastState != FileConsts.State.makeSecret.getCode()) {
                        prop.setFid(IdUtils.randomId());
                    }
                    target = fileService.fileClassified(attachment, prop, approveUser.getPkUid(),
                            workFlow.getFkSrcFid());
                }
                // 文件签发
                if (workFlow.getWorkFlowType() == WorkFlowConsts.Type.dispatch.getCode()) {
                    if (lastState != FileConsts.State.dispatch.getCode()) {
                        prop.setFid(IdUtils.randomId());
                    }
                    target = fileService.fileIssued(attachment, prop, approveUser.getPkUid(), workFlow.getFkSrcFid());
                }
                // 密级变更
                if (workFlow.getWorkFlowType() == WorkFlowConsts.Type.changeSecret.getCode()) {
                    target = fileService.fileClassifiedChange(attachment, prop, approveUser.getPkUid());
                }
                // 文件解密
                if (workFlow.getWorkFlowType() == WorkFlowConsts.Type.unSecret.getCode()) {
                    if (lastState != FileConsts.State.unSecret.getCode()) {
                        prop.setFid(IdUtils.randomId());
                    }
                    target = fileService.fileDeclassified(attachment, prop, approveUser.getPkUid(),
                            workFlow.getFkSrcFid());
                }
                // 密文还原
                if (workFlow.getWorkFlowType() == WorkFlowConsts.Type.restore.getCode()) {
                    target = fileService.fileRestore(attachment);
                }
                if (target != null) {
                    java.io.File targetFile = new java.io.File(
                            cacheDir + java.io.File.separator + "t" + workFlow.getId());
                    try {
                        FileUtils.copyFile(target, targetFile);
                    } catch (IOException e) {
                    }
                }
                MqttMessageHandler.tryPublish(workFlow.getUser().getPkUid(), MqttMessageHandler.TYPE_WORKFLOW,
                        MqttMessageHandler.MSG_WORKFLOW_PASS + "  [" + workFlow.getFileName() + "]",
                        MqttMessageHandler.URL_WORKFLOW + "?workflowId=" + workFlow.getId());
            } else {
                workFlow.setCurrentStep(currentStep + 1);

                Role approver = null;
                List<Integer> approvers = workFlow_ApprovalRequest.getApproverByStep();
                if (approvers != null && !approvers.isEmpty()) {
                    approver = roleDao.findById(approvers.get(0));
                }
                if (approver == null) {
                    throw new ApiException(ErrorCode.operationNotPermitted);
                }

                List<WorkflowTrack> nextTracks = workFlowTrackDao.findByApproveStepAndWorkflowId(currentStep + 1,
                        workFlow.getId());
                if (!nextTracks.isEmpty()) {
                    WorkflowTrack tra = nextTracks.get(0);
                    User agent = approver.getUserByFkAgentUid();
                    Long invalidTime = approver.getFkAgentInvalidTime();
                    if (agent != null && invalidTime != null && invalidTime > currentTime) {
                        tra.setUser(agent);
                    } else {
                        User userByFkUid = approver.getUserByFkUid();
                        tra.setUser(userByFkUid);
                    }
                    workFlowTrackDao.save(tra);
                    if (tra.getUser() != null) {
                        MqttMessageHandler.tryPublish(tra.getUser().getPkUid(), MqttMessageHandler.TYPE_APPROVAL,
                                MqttMessageHandler.MSG_APPROVAL + "  [" + workFlow.getFileName() + "]",
                                MqttMessageHandler.URL_WORKFLOW + "?workflowId=" + workFlow.getId());
                        MqttMessageHandler.push(tra.getUser().getPkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                                MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
                    }
                }
            }
        }
        workFlowDao.save(workFlow);

        String operateType = WorkFlowConsts.Type.parse(workFlow.getWorkFlowType()).getDescription() + "审批";
        String way = workFlow_ApprovalRequest.getApproveState() == WorkflowTrackConsts.state.pass.getCode() ? "通过"
                : "拒绝";
        ClientLog clientLog = new ClientLog(token.getClient(), file, token.getUser(), currentTime,
                workFlow_ApprovalRequest.httpServletRequest().getRemoteAddr(), "", operateType, "", 1, way,
                workFlow.getFileName(), "");
        clientLogDao.save(clientLog);
        String desc = logArchiveService.parseClientLogDescription(clientLog.getId(), false);
        clientLog.setOperateDescription(desc);
        clientLogDao.save(clientLog);
        return new WorkFlow_ApprovalResponse();
    }

}
