package cn.bestsec.dcms.platform.impl.workFlow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.WorkFlow_DownloadFileByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.WorkFlow_DownloadFileByIdRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_DownloadFileByIdResponse;
import cn.bestsec.dcms.platform.api.support.TokenWrapper;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月12日 下午3:55:15
 */
@Component
public class WorkFlowDownloadFileById implements WorkFlow_DownloadFileByIdApi {
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    public WorkFlow_DownloadFileByIdResponse execute(WorkFlow_DownloadFileByIdRequest workFlow_DownloadFileByIdRequest)
            throws ApiException {
        TokenWrapper token = workFlow_DownloadFileByIdRequest.tokenWrapper();
        User user = token.getUser();

        Workflow workflow = workFlowDao.findById(workFlow_DownloadFileByIdRequest.getWorkFlowId());
        if (workflow == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        String fileName = workflow.getFileName();

        // 判断是否有访问权限
        if (!securityPolicyService.canAccessFile(user.getUserLevel(), workflow.getApplyFileLevel())) {
            throw new ApiException(ErrorCode.permissionDenied);
        }

        String cacheDir = SystemProperties.getInstance().getCacheDir();
        // String suffix = fileName.substring(fileName.lastIndexOf("."));
        String workflowAttachmentPath = cacheDir + java.io.File.separator + "workflow" + java.io.File.separator
                + workflow.getId();
        java.io.File attachment = new java.io.File(workflowAttachmentPath);
        // 如果流程完成并通过，下载的是结果文件
        if (workflow.getFlowState() == WorkFlowConsts.State.complete.getCode() && workflow.getFlowResult() == 1) {
            attachment = new java.io.File(
                    cacheDir + java.io.File.separator + "workflow" + java.io.File.separator + "t" + workflow.getId());
        }
        if (!attachment.exists()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 下载的文件名 文件名+状态
        // String attachmentName = null;
        File file = null;
        if (workflow.getFlowState() == WorkFlowConsts.State.complete.getCode() && workflow.getFlowResult() == 1) {
            // attachmentName = TextUtils.changeString(fileName,
            // workflow.getWorkFlowType(), workflow.getApplyFileLevel());
        } else {
            file = fileDao.findByPkFid(workflow.getFkSrcFid());
            // attachmentName = TextUtils.changeString(fileName,
            // file.getFileState(), file.getFileLevel());
        }
        WorkFlow_DownloadFileByIdResponse resp = new WorkFlow_DownloadFileByIdResponse();
        if (attachment.exists()) {
            resp.setDownload(attachment);
            resp.setDownloadName(fileName);
        }

        String operateType = FileConsts.downLoadType.attachment.getDescription();
        ClientLog clientLog = new ClientLog(token.getRaw().getClient(), file, token.getUser(),
                System.currentTimeMillis(), workFlow_DownloadFileByIdRequest.httpServletRequest().getRemoteAddr(), "",
                operateType, "", 1, "", workflow.getFileName(), "");
        clientLogDao.save(clientLog);
        String desc = logArchiveService.parseClientLogDescription(clientLog.getId(), false);
        clientLog.setOperateDescription(desc);
        clientLogDao.save(clientLog);
        return resp;
    }
}
