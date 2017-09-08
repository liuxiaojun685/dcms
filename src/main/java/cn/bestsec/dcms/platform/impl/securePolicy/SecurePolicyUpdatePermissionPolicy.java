package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdatePermissionPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdatePermissionPolicyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdatePermissionPolicyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.FileLevelDecidePolicyDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecidePolicy;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改定密策略
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 上午10:05:00
 */
@Component
public class SecurePolicyUpdatePermissionPolicy implements SecurePolicy_UpdatePermissionPolicyApi {

    @Autowired
    private FileLevelDecidePolicyDao fileLevelDecidePolicyDao;

    @Override
    @Transactional
    public SecurePolicy_UpdatePermissionPolicyResponse execute(
            SecurePolicy_UpdatePermissionPolicyRequest securePolicy_UpdatePermissionPolicyRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = securePolicy_UpdatePermissionPolicyRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_permissionPolicy)
                .admin(securePolicy_UpdatePermissionPolicyRequest.tokenWrapper().getAdmin()).operateDescription();

        SecurePolicy_UpdatePermissionPolicyResponse resp = new SecurePolicy_UpdatePermissionPolicyResponse();
        // fileState&fileLevel&roleType逻辑唯一
        Integer fileState = securePolicy_UpdatePermissionPolicyRequest.getFileState();
        Integer fileLevel = securePolicy_UpdatePermissionPolicyRequest.getFileLevel();
        Integer roleType = securePolicy_UpdatePermissionPolicyRequest.getRoleType();
        FileLevelDecidePolicy fileLevelDecidePolicy = fileLevelDecidePolicyDao
                .findByFileStateAndFileLevelAndRoleType(fileState, fileLevel, roleType);
        if (fileLevelDecidePolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        // 获取定密策略权限
        PermissionInfo permissionInfo = securePolicy_UpdatePermissionPolicyRequest.getPermission();
        // 是否允许内容拷贝 1是 0否
        Integer contentCopy = permissionInfo.getContentCopy();
        if (contentCopy != null) {
            fileLevelDecidePolicy.setContentCopy(contentCopy);
        }
        // 是否允许文件授权
        Integer fileAuthorization = permissionInfo.getFileAuthorization();
        if (fileAuthorization != null) {
            fileLevelDecidePolicy.setFileAuthorization(fileAuthorization);
        }
        // 是否允许文件拷贝
        Integer fileCopy = permissionInfo.getFileCopy();
        if (fileCopy != null) {
            fileLevelDecidePolicy.setFileCopy(fileCopy);
        }
        // 是否允许文件解密
        Integer fileDecrypt = permissionInfo.getFileDecrypt();
        if (fileDecrypt != null) {
            fileLevelDecidePolicy.setFileDecrypt(fileDecrypt);
        }
        // 是否允许文件签发
        Integer fileDispatch = permissionInfo.getFileDispatch();
        if (fileDispatch != null) {
            fileLevelDecidePolicy.setFileDispatch(fileDispatch);
        }
        // 是否允许密级变更
        Integer fileLevelChange = permissionInfo.getFileLevelChange();
        if (fileLevelChange != null) {
            fileLevelDecidePolicy.setFileLevelChange(fileLevelChange);
        }
        // 是否允许文件定密
        Integer fileLevelDecide = permissionInfo.getFileLevelDecide();
        if (fileLevelDecide != null) {
            fileLevelDecidePolicy.setFileLevelDecide(fileLevelDecide);
        }
        // 是否允许文件另存副本
        Integer fileSaveCopy = permissionInfo.getFileSaveCopy();
        if (fileSaveCopy != null) {
            fileLevelDecidePolicy.setFileSaveCopy(fileSaveCopy);
        }
        // 是否允许文件解绑
        Integer fileUnbunding = permissionInfo.getFileUnbunding();
        if (fileUnbunding != null) {
            fileLevelDecidePolicy.setFileUnbunding(fileUnbunding);
        }
        // 是否允许内容修改
        Integer contentModify = permissionInfo.getContentModify();
        if (contentModify != null) {
            fileLevelDecidePolicy.setContentModify(contentModify);
        }
        // 是否允许内容打印
        Integer contentPrint = permissionInfo.getContentPrint();
        if (contentPrint != null) {
            fileLevelDecidePolicy.setContentPrint(contentPrint);
        }
        // 是否允许打印时隐藏水印
        Integer contentPrintHideWater = permissionInfo.getContentPrintHideWater();
        if (contentPrintHideWater != null) {
            fileLevelDecidePolicy.setContentPrintHideWater(contentPrintHideWater);
        }
        // 是否允许内容阅读
        Integer contentRead = permissionInfo.getContentRead();
        if (contentRead != null) {
            fileLevelDecidePolicy.setContentRead(contentRead);
        }
        // 是否允许内容截屏
        Integer contentScreenShot = permissionInfo.getContentScreenShot();
        if (contentScreenShot != null) {
            fileLevelDecidePolicy.setContentScreenShot(contentScreenShot);
        }
        fileLevelDecidePolicyDao.save(fileLevelDecidePolicy);

        return resp;
    }

}
