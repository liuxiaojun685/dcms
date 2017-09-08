package cn.bestsec.dcms.platform.impl.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.File_CanUploadApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.File_CanUploadRequest;
import cn.bestsec.dcms.platform.api.model.File_CanUploadResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月12日 下午3:21:23
 */
@Component
public class FileCanUpload implements File_CanUploadApi {
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private WorkFlowDao workFlowDao;

    @Override
    @Transactional
    public File_CanUploadResponse execute(File_CanUploadRequest request) throws ApiException {
        User user = request.tokenWrapper().getUser();
        try {
            if (!securityPolicyService.canUploadFile(request.getFid(), user.getPkUid(), request.getUploadType(),
                    request.getFileState(), request.getFileLevel())) {
                String level = FileConsts.Level.parse(request.getFileLevel()).getDescription();
                String state = FileConsts.State.parse(request.getFileState()).getDescription();
                String type = FileConsts.UploadType.parse(request.getUploadType()).getDescription();
                return new File_CanUploadResponse(ErrorCode.operationNotPermitted.getCode(),
                        "您无权对该" + state + "文件 进行" + type + "操作");
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
        List<Workflow> workflows = workFlowDao.findSame(request.getUploadType(), request.getFid());
        if (!workflows.isEmpty()) {
            throw new ApiException(ErrorCode.fileAlreadyExistWorkflow);
        }
        return new File_CanUploadResponse();
    }

}
