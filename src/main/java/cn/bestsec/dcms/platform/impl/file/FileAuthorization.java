package cn.bestsec.dcms.platform.impl.file;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_AuthorizationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.DRMInfo;
import cn.bestsec.dcms.platform.api.model.File_AuthorizationRequest;
import cn.bestsec.dcms.platform.api.model.File_AuthorizationResponse;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileDrmDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileDrm;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 文件授权
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午4:40:01
 */
@Component
public class FileAuthorization implements File_AuthorizationApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileDrmDao fileDrmDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public File_AuthorizationResponse execute(File_AuthorizationRequest file_AuthorizationRequest) throws ApiException {
        File_AuthorizationResponse resp = new File_AuthorizationResponse();
        // 通过token得到用户
        Token token = tokenDao.findByToken(file_AuthorizationRequest.getToken());
        User user = token.getUser();

        String fid = file_AuthorizationRequest.getFid();
        // 得到授权的文件
        File file = fileDao.findByPkFid(fid);
        if (file == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 判断是否有访问权限
        if (!securityPolicyService.canAccessFile(user.getUserLevel(), file.getFileLevel())) {
            throw new ApiException(ErrorCode.permissionDenied);
        }

        PermissionInfo permission = securityPolicyService.getPrivatePermissionInfo(file.getPkFid(), user.getPkUid());
        if ((permission.getFileAuthorization() & 1) != 0) {
            List<DRMInfo> drmList = file_AuthorizationRequest.getDrmList();
            for (DRMInfo drmInfo : drmList) {
                // 查找是否已对该用户/组/部门授权，是则更新
                FileDrm fileDrmData = fileDrmDao.findByFkFidAndFkVarId(fid, drmInfo.getVarId());
                if (fileDrmData == null) {
                    fileDrmData = new FileDrm();
                }
                fileDrmData.setContentCopy(drmInfo.getContentCopy());
                fileDrmData.setFileCopy(drmInfo.getFileCopy());
                fileDrmData.setFileSaveCopy(drmInfo.getFileSaveCopy());
                fileDrmData.setFkFid(fid);
                fileDrmData.setFkVarId(drmInfo.getVarId());
                fileDrmData.setContentModify(drmInfo.getContentModify());
                fileDrmData.setContentPrint(drmInfo.getContentPrint());
                fileDrmData.setContentPrintHideWater(drmInfo.getContentPrintHideWater());
                fileDrmData.setContentRead(drmInfo.getContentRead());
                fileDrmData.setContentScreenShot(drmInfo.getContentScreenShot());
                fileDrmData.setVarIdType(drmInfo.getVarIdType());
                fileDrmDao.save(fileDrmData);
            }
        } else {
            throw new ApiException(ErrorCode.permissionDenied);
        }

        return resp;
    }

}
