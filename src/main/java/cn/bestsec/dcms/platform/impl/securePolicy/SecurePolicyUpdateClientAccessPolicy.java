package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdateClientAccessPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateClientAccessPolicyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateClientAccessPolicyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.ClientLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.entity.ClientLevelAccessPolicy;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改终端密级访问控制策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午1:58:30
 */
@Component
public class SecurePolicyUpdateClientAccessPolicy implements SecurePolicy_UpdateClientAccessPolicyApi {
    @Autowired
    private ClientLevelAccessPolicyDao clientLevelAccessPolicyDao;

    @Override
    @Transactional
    public SecurePolicy_UpdateClientAccessPolicyResponse execute(
            SecurePolicy_UpdateClientAccessPolicyRequest securePolicy_UpdateClientAccessPolicyRequest)
            throws ApiException {
        AdminLogBuilder adminLogBuilder = securePolicy_UpdateClientAccessPolicyRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_clientAccessPolicy)
                .admin(securePolicy_UpdateClientAccessPolicyRequest.tokenWrapper().getAdmin()).operateDescription();

        SecurePolicy_UpdateClientAccessPolicyResponse resp = new SecurePolicy_UpdateClientAccessPolicyResponse();
        ClientLevelAccessPolicy clientLevelAccessPolicy = clientLevelAccessPolicyDao.findByUserLevelAndClientLevel(
                securePolicy_UpdateClientAccessPolicyRequest.getUserLevel(),
                securePolicy_UpdateClientAccessPolicyRequest.getClientLevel());
        if (clientLevelAccessPolicy == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        clientLevelAccessPolicy.setEnable(securePolicy_UpdateClientAccessPolicyRequest.getEnable());
        clientLevelAccessPolicyDao.save(clientLevelAccessPolicy);

        return resp;
    }

}
