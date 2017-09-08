package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryPermissionPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryPermissionPolicyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryPermissionPolicyResponse;
import cn.bestsec.dcms.platform.dao.FileLevelDecidePolicyDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecidePolicy;

/**
 * 9.14 查询文件默认权限策略
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月8日 下午3:37:25
 */
@Component
public class SecurePolicyQueryPermissionPolicy implements SecurePolicy_QueryPermissionPolicyApi {

    @Autowired
    private FileLevelDecidePolicyDao fileLevelDecidePolicyDao;

    @Override
    @Transactional
    public SecurePolicy_QueryPermissionPolicyResponse execute(
            SecurePolicy_QueryPermissionPolicyRequest securePolicy_QueryPermissionPolicyRequest) throws ApiException {
        Integer fileState = securePolicy_QueryPermissionPolicyRequest.getFileState();
        Integer fileLevel = securePolicy_QueryPermissionPolicyRequest.getFileLevel();
        Integer roleType = securePolicy_QueryPermissionPolicyRequest.getRoleType();
        FileLevelDecidePolicy fileLevelDecidePolicy = fileLevelDecidePolicyDao
                .findByFileStateAndFileLevelAndRoleType(fileState, fileLevel, roleType);
        if (fileLevelDecidePolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 定密策略信息
        PermissionInfo permissionInfo = new PermissionInfo();
        permissionInfo.setContentCopy(fileLevelDecidePolicy.getContentCopy());
        permissionInfo.setFileAuthorization(fileLevelDecidePolicy.getFileAuthorization());
        permissionInfo.setFileCopy(fileLevelDecidePolicy.getFileCopy());
        permissionInfo.setFileDispatch(fileLevelDecidePolicy.getFileDispatch());
        permissionInfo.setFileDecrypt(fileLevelDecidePolicy.getFileDecrypt());
        permissionInfo.setFileLevelChange(fileLevelDecidePolicy.getFileLevelChange());
        permissionInfo.setFileLevelDecide(fileLevelDecidePolicy.getFileLevelDecide());
        permissionInfo.setFileSaveCopy(fileLevelDecidePolicy.getFileSaveCopy());
        permissionInfo.setFileUnbunding(fileLevelDecidePolicy.getFileUnbunding());
        permissionInfo.setContentModify(fileLevelDecidePolicy.getContentModify());
        permissionInfo.setContentPrint(fileLevelDecidePolicy.getContentPrint());
        permissionInfo.setContentPrintHideWater(fileLevelDecidePolicy.getContentPrintHideWater());
        permissionInfo.setContentRead(fileLevelDecidePolicy.getContentRead());
        permissionInfo.setContentScreenShot(fileLevelDecidePolicy.getContentScreenShot());

        SecurePolicy_QueryPermissionPolicyResponse resp = new SecurePolicy_QueryPermissionPolicyResponse();
        resp.setPermission(permissionInfo);

        return resp;
    }

}
