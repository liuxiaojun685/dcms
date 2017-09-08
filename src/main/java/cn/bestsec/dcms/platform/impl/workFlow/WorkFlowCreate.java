package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.api.WorkFlow_CreateApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.ClassificationInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeItem;
import cn.bestsec.dcms.platform.api.model.WorkFlow_CreateRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_CreateResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;
import cn.bestsec.dcms.platform.mqtt.MqttMessageHandler;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 17.1 流程申请 终端用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月13日 下午5:02:22
 */
@Component
public class WorkFlowCreate implements WorkFlow_CreateApi {

    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private WorkflowTrackDao workFlowTrackDao;
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    @Transactional
    public WorkFlow_CreateResponse execute(WorkFlow_CreateRequest workFlow_CreateRequest) throws ApiException {
        Token token = tokenDao.findByToken(workFlow_CreateRequest.getToken());
        long currentTime = System.currentTimeMillis();

        try {
            if (!securityPolicyService.canCreateWorkflow(workFlow_CreateRequest.getSrcFid(), token.getUser().getPkUid(),
                    workFlow_CreateRequest.getWorkFlowType(), workFlow_CreateRequest.getFileState(),
                    workFlow_CreateRequest.getApplyFileLevel())) {
                throw new ApiException(ErrorCode.permissionDenied);
            }
        } catch (ApiException e) {
            ErrorCode ec = e.getErrorCode();
            if (ec.getCode() == ErrorCode.permissionDenied.getCode()) {
                throw new ApiException(ErrorCode.fileNoPermission);
            }
            throw e;
        }

        // 同一文件，不能申请相同流程，如果流程审批被退回，则可以再次申请
        // 1.申请后流程未完成 2.流程通过完成后不能申请（除了审批不通过）
        List<Workflow> workflows = workFlowDao.findSame(workFlow_CreateRequest.getWorkFlowType(),
                workFlow_CreateRequest.getSrcFid());
        if (!workflows.isEmpty()) {
            throw new ApiException(ErrorCode.workflowAlreadyExists);
        }

        Workflow workflow = new Workflow();
        // 定密依据
        if (workFlow_CreateRequest.getBasisType() != null) {
            workflow.setApplyBasis(JSON.toJSONString(workFlow_CreateRequest.getApplyBasis()));
            workflow.setApplyBasisType(workFlow_CreateRequest.getBasisType());
            workflow.setApplyBasisDesc(workFlow_CreateRequest.getBasisDesc());
        }
        List<FileScopeItem> scos = workFlow_CreateRequest.getFileScope();
        List<FileScopeInfo> scope = new ArrayList<>();
        for (FileScopeItem sco : scos) {
            FileLevelDecideUnit major = fileLevelDecideUnitDao.findByUnitNo(sco.getUnitNo());
            if (major != null && major.getMajor() == 1) {
                FileScopeInfo info = new FileScopeInfo(sco.getUid(), null, null, null, null, sco.getUnitNo());
                User user = userDao.findByPkUid(sco.getUid());
                if (user != null) {
                    info.setAccount(user.getAccount());
                    info.setLevel(user.getUserLevel());
                    info.setName(user.getName());
                    info.setOnline(UserConsts.userOnline(user));
                }
                scope.add(info);
            }
        }
        workflow.setApplyDispatch(JSON.toJSONString(scope));
        workflow.setApplyDispatchDesc(workFlow_CreateRequest.getFileScopeDesc());
        workflow.setApplyFileLevel(workFlow_CreateRequest.getApplyFileLevel());
        workflow.setApplyMajorUnit(JSON.toJSONString(workFlow_CreateRequest.getApplyMajorUnit()));
        workflow.setApplyMinorUnit(JSON.toJSONString(workFlow_CreateRequest.getApplyMinorUnit()));
        workflow.setDurationType(workFlow_CreateRequest.getDurationType());
        workflow.setApplyValidPeriod(workFlow_CreateRequest.getApplyValidPeriod());
        workflow.setFileDecryptTime(workFlow_CreateRequest.getFileDecryptTime());
        workflow.setDurationDescription(workFlow_CreateRequest.getDurationDescription());
        workflow.setPermission(JSON.toJSONString(workFlow_CreateRequest.getPermission()));
        workflow.setCreateComment(workFlow_CreateRequest.getCreateComment());
        workflow.setCreateTime(currentTime);
        workflow.setIssueNumber(workFlow_CreateRequest.getIssueNumber());
        workflow.setDocNumber(workFlow_CreateRequest.getDocNumber());
        workflow.setDuplicationAmount(workFlow_CreateRequest.getDuplicationAmount());
        workflow.setMarkVersion(workFlow_CreateRequest.getMarkVersion());
        workflow.setUrgency(workFlow_CreateRequest.getUrgency());
        workflow.setBusiness(workFlow_CreateRequest.getBusiness());

        // 文件
        workflow.setFkSrcFid(workFlow_CreateRequest.getSrcFid());
        // 根据工作流名称和文件密级获取审核级数(步骤)
        WorkflowPolicy workflowPolicy = workflowPolicyDao.findByWorkFlowTypeAndFileLevel(
                workFlow_CreateRequest.getWorkFlowType(), workFlow_CreateRequest.getApplyFileLevel());
        if (workflowPolicy == null) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }

        List<Integer> approvers = workFlow_CreateRequest.getApproverByStep();
        if (approvers == null) {
            approvers = new ArrayList<>();
        }

        List<Role> approverRoleList = new ArrayList<>();
        int step = 1;
        for (;; step++) {
            List<WorkflowPolicyStep> stepList = workflowPolicyStepDao
                    .findByFkWorkFlowPolicyIdAndStep(workflowPolicy.getId(), step);
            if (stepList.isEmpty()) {
                break;
            }
            Role approver = null;
            if (approvers.size() >= step) {
                int roleId = approvers.get(step - 1);
                Role role = roleDao.findById(roleId);
                approver = role;
            }
            if (approver == null && step == 1) {
                approver = stepList.get(0).getRole();
            }
            approverRoleList.add(approver);
        }
        // 一级没有审批人
        if (step == 1) {
            throw new ApiException(ErrorCode.configNotCorrect);
        }
        workflow.setTotalStep(step - 1);
        workflow.setUser(token.getUser());
        workflow.setWorkFlowType(workFlow_CreateRequest.getWorkFlowType());
        // 一个流程创建好，现在该审批的级数为1
        workflow.setCurrentStep(1);
        workflow.setFlowState(0);

        java.io.File attachment = workFlow_CreateRequest.getAttachment();
        String attachmentName = workFlow_CreateRequest.getAttachmentName();
        if (attachment == null) {
            throw new ApiException(ErrorCode.attachmentNotFound);
        }

        File file = fileDao.findByPkFid(workflow.getFkSrcFid());
        workflow.setFileName(attachmentName);
        /*
         * if (file != null) { workflow.setFileName(file.getName()); }
         */
        workFlowDao.save(workflow);

        if (file == null) {
            ClassificationInfo prop = new ClassificationInfo();
            prop.setFid(workFlow_CreateRequest.getSrcFid());
            prop.setFileName(attachmentName);
            file = fileService.fileUpdate(attachment, prop, token.getUser().getPkUid(), null);
        }

        String cacheDir = SystemProperties.getInstance().getCacheDir() + java.io.File.separator + "workflow";
        String suffix = FileUtils.getSuffix(attachmentName);
        FileUtils.copyFile(attachment.getPath(), cacheDir + java.io.File.separator + workflow.getId());

        for (int index = 0; index < approverRoleList.size(); index++) {
            Role approver = approverRoleList.get(index);
            WorkflowTrack workflowTrack = new WorkflowTrack();
            // 工作流ID
            workflowTrack.setWorkflow(workflow);
            // 审批人
            if (approver == null) {
                workflowTrack.setUser(null);
            } else {
                User agent = approver.getUserByFkAgentUid();
                Long invalidTime = approver.getFkAgentInvalidTime();
                if (agent != null && invalidTime != null && invalidTime > currentTime) {
                    workflowTrack.setUser(agent);
                } else {
                    User userByFkUid = approver.getUserByFkUid();
                    workflowTrack.setUser(userByFkUid);
                }
            }
            workflowTrack.setApproveState(0);
            workflowTrack.setApproveStep(index + 1);
            workFlowTrackDao.save(workflowTrack);
            if (workflowTrack.getUser() != null) {
                MqttMessageHandler.tryPublish(workflowTrack.getUser().getPkUid(), MqttMessageHandler.TYPE_APPROVAL,
                        MqttMessageHandler.MSG_APPROVAL + "  [" + workflow.getFileName() + "]",
                        MqttMessageHandler.URL_WORKFLOW + "?workflowId=" + workflow.getId());
                MqttMessageHandler.push(workflowTrack.getUser().getPkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                        MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
            }
        }
        // 名称脱敏处理
        String fileName = workflow.getFileName();
        if (logArchiveService.isFileDesensity(workflow.getApplyFileLevel())) {
            fileName = TextUtils.getDealWithName(fileName);
        }
        String operateType = WorkFlowConsts.Type.parse(workFlow_CreateRequest.getWorkFlowType()).getDescription()
                + "申请";
        ClientLog clientLog = new ClientLog(token.getClient(), file, token.getUser(), currentTime,
                workFlow_CreateRequest.httpServletRequest().getRemoteAddr(), "", operateType, "", 1, "",
                workflow.getFileName(), "");
        clientLogDao.save(clientLog);
        String desc = logArchiveService.parseClientLogDescription(clientLog.getId(), false);
        clientLog.setOperateDescription(desc);
        clientLogDao.save(clientLog);

        WorkFlow_CreateResponse resp = new WorkFlow_CreateResponse();
        resp.setWorkFlowId(workflow.getId());
        return resp;
    }

}
