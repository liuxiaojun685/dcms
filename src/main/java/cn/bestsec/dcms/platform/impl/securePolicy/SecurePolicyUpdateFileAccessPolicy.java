package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdateFileAccessPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateFileAccessPolicyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateFileAccessPolicyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.FileLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.entity.FileLevelAccessPolicy;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改文件密级访问控制策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午2:36:04
 */
@Component
public class SecurePolicyUpdateFileAccessPolicy implements SecurePolicy_UpdateFileAccessPolicyApi {
    @Autowired
    private FileLevelAccessPolicyDao fileLevelAccessPolicyDao;

    @Override
    @Transactional
    public SecurePolicy_UpdateFileAccessPolicyResponse execute(
            SecurePolicy_UpdateFileAccessPolicyRequest securePolicy_UpdateFileAccessPolicyRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = securePolicy_UpdateFileAccessPolicyRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_fileAccessPolicy)
                .admin(securePolicy_UpdateFileAccessPolicyRequest.tokenWrapper().getAdmin()).operateDescription();

        SecurePolicy_UpdateFileAccessPolicyResponse resp = new SecurePolicy_UpdateFileAccessPolicyResponse();
        FileLevelAccessPolicy fileLevelAccessPolicy = fileLevelAccessPolicyDao.findByFileLevelAndUserLevel(
                securePolicy_UpdateFileAccessPolicyRequest.getFileLevel(),
                securePolicy_UpdateFileAccessPolicyRequest.getUserLevel());
        if (fileLevelAccessPolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        fileLevelAccessPolicy.setEnable(securePolicy_UpdateFileAccessPolicyRequest.getEnable());
        fileLevelAccessPolicyDao.save(fileLevelAccessPolicy);

        return resp;
    }

}
