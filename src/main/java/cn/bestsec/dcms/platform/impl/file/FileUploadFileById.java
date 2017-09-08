package cn.bestsec.dcms.platform.impl.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.File_UploadFileByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.ClassificationInfo;
import cn.bestsec.dcms.platform.api.model.File_UploadFileByIdRequest;
import cn.bestsec.dcms.platform.api.model.File_UploadFileByIdResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 文件上传 权限:终端用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月12日 下午3:05:32
 */
@Component
public class FileUploadFileById implements File_UploadFileByIdApi {
    // private static final Logger log =
    // LoggerFactory.getLogger(FileUploadFileById.class);
    @Autowired
    private FileDao fileDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private FileService fileService;

    @Override
    @Transactional
    public File_UploadFileByIdResponse execute(File_UploadFileByIdRequest file_UploadFileByIdRequest)
            throws ApiException {
        File_UploadFileByIdRequest fileDto = file_UploadFileByIdRequest;
        java.io.File attachment = fileDto.getAttachment();
        if (attachment == null) {
            throw new ApiException(ErrorCode.attachmentNotFound);
        }

        User user = fileDto.tokenWrapper().getUser();

        try {
            if (!securityPolicyService.canUploadFile(fileDto.getFid(), user.getPkUid(), fileDto.getUploadType(),
                    fileDto.getFileState(), fileDto.getFileLevel())) {
                throw new ApiException(ErrorCode.permissionDenied);
            }
        } catch (ApiException e) {
            ErrorCode ec = e.getErrorCode();
            if (ec.getCode() == ErrorCode.permissionDenied.getCode()) {
                throw new ApiException(ErrorCode.fileNoPermission);
            }
            throw e;
        }

        String remoteName = fileDto.getAttachmentName();
        if (fileDto.getFileName() != null && !fileDto.getFileName().isEmpty()) {
            remoteName = fileDto.getFileName();
        }
        ClassificationInfo prop = new ClassificationInfo(null/* 表示取文件头更新fid */, remoteName, fileDto.getUrgency(),
                fileDto.getFileLevel(), fileDto.getFileValidPeriod(), fileDto.getDurationType(),
                fileDto.getDurationDescription(), fileDto.getFileDecryptTime(), fileDto.getFileScope(),
                fileDto.getFileScopeDesc(), fileDto.getBasis(), fileDto.getBasisType(), fileDto.getBasisDesc(),
                fileDto.getIssueNumber(), fileDto.getDocNumber(), fileDto.getDuplicationAmount(),
                fileDto.getFileMajorUnit(), fileDto.getFileMinorUnit(), fileDto.getPermission(),
                fileDto.getMarkVersion(), fileDto.getDescription(), fileDto.getBusiness());

        if (fileDto.getUploadType() == FileConsts.UploadType.update.getCode()) {
            fileService.fileUpdate(attachment, prop, user.getPkUid(), fileDto.getFid());
        } else if (fileDto.getUploadType() == FileConsts.UploadType.makeSecret.getCode()) {
            fileService.fileClassified(attachment, prop, user.getPkUid(), fileDto.getFid());
        } else if (fileDto.getUploadType() == FileConsts.UploadType.dispatch.getCode()) {
            fileService.fileIssued(attachment, prop, user.getPkUid(), fileDto.getFid());
        } else if (fileDto.getUploadType() == FileConsts.UploadType.changeSecret.getCode()) {
            fileService.fileClassifiedChange(attachment, prop, user.getPkUid());
        } else if (fileDto.getUploadType() == FileConsts.UploadType.unSecret.getCode()) {
            fileService.fileDeclassified(attachment, prop, user.getPkUid(), fileDto.getFid());
        }

        return new File_UploadFileByIdResponse();
    }
}
