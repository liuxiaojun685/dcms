package cn.bestsec.dcms.platform.impl.securePolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_DeleteMarkKeyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_DeleteMarkKeyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_DeleteMarkKeyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午8:13:41
 */
@Component
public class SecurePolicyDeleteMarkKey implements SecurePolicy_DeleteMarkKeyApi {
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public SecurePolicy_DeleteMarkKeyResponse execute(SecurePolicy_DeleteMarkKeyRequest request) throws ApiException {
        String keyId = request.getKeyId();
        MarkKey markKey = markKeyDao.findByKeyId(keyId);
        if (markKey == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        markKeyDao.delete(markKey);
        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_deleteMarkKey)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(keyId);
        return new SecurePolicy_DeleteMarkKeyResponse();
    }

}
