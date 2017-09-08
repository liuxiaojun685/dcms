package cn.bestsec.dcms.platform.impl.securePolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_AddMarkKeyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_AddMarkKeyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_AddMarkKeyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午7:49:28
 */
@Component
public class SecurePolicyAddMarkKey implements SecurePolicy_AddMarkKeyApi {
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public SecurePolicy_AddMarkKeyResponse execute(SecurePolicy_AddMarkKeyRequest request) throws ApiException {
        Admin admin = request.tokenWrapper().getAdmin();
        String key = IdUtils.randomId();
        Integer markVersion = 1;
        String keyId = IdUtils.randomId();
        String keyName = request.getKeyName();
        Integer keyLength = key.length();
        Long createTime = System.currentTimeMillis();
        String keyEnc = StringEncrypUtil.encrypt(key);
        MarkKey markKey = new MarkKey(admin, keyEnc, keyEnc, markVersion, keyId, keyName, keyLength, createTime, 1, 0);
        markKeyDao.save(markKey);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_addMarkKey).admin(admin)
                .operateDescription(keyId);
        return new SecurePolicy_AddMarkKeyResponse();
    }

}
