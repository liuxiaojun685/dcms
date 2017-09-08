package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdateMarkKeyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateMarkKeyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateMarkKeyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.dao.MarkKeyHistoryDao;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.entity.MarkKeyHistory;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午8:13:41
 */
@Component
public class SecurePolicyUpdateMarkKey implements SecurePolicy_UpdateMarkKeyApi {
    @Autowired
    private MarkKeyDao markKeyDao;
    @Autowired
    private MarkKeyHistoryDao markKeyHistoryDao;

    @Override
    public SecurePolicy_UpdateMarkKeyResponse execute(SecurePolicy_UpdateMarkKeyRequest request) throws ApiException {
        long currentTime = System.currentTimeMillis();
        String keyId = request.getKeyId();
        MarkKey markKey = markKeyDao.findByKeyId(keyId);
        if (markKey == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        if (request.getKeyName() != null && !request.getKeyName().isEmpty()) {
            markKey.setKeyName(request.getKeyName());
            markKeyDao.save(markKey);
        }
        if (request.getEnable() != null) {
            if (request.getEnable() == 0) {
                List<MarkKey> enables = markKeyDao.findEnableKey();
                if (enables.size() == 1 && enables.get(0).getKeyId() == keyId) {
                    throw new ApiException(ErrorCode.disableMarkKeyFailed);
                }
            }
            if (markKey.getEnable() != request.getEnable()) {
                if (request.getEnable() == 1) {
                    List<MarkKey> enables = markKeyDao.findEnableKey();
                    for (MarkKey key : enables) {
                        disableKeys(key);
                    }
                    enableKey(markKey);
                } else {
                    disableKeys(markKey);
                }
                AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
                adminLogBuilder.operateTime(currentTime).operation(AdminLogOp.secure_updateMarkKey)
                        .admin(request.tokenWrapper().getAdmin()).operateDescription(keyId);
            }
        }
        return new SecurePolicy_UpdateMarkKeyResponse();
    }

    private void enableKey(MarkKey markKey) {
        markKey.setEnable(1);
        markKeyDao.save(markKey);
        List<MarkKeyHistory> newestKeys = markKeyHistoryDao.findNewestByKeyId(markKey.getKeyId());
        MarkKeyHistory newestKey = null;
        if (!newestKeys.isEmpty()) {
            newestKey = newestKeys.get(0);
        }
        if (newestKey == null || newestKey.getDisableTime() > 0) {
            Long currentTime = System.currentTimeMillis();
            markKeyHistoryDao
                    .save(new MarkKeyHistory(markKey.getKeyId(), markKey.getKeyName(), currentTime, currentTime, 0L));
        }
    }

    private void disableKeys(MarkKey markKey) {
        markKey.setEnable(0);
        markKeyDao.save(markKey);
        List<MarkKeyHistory> newestKeys = markKeyHistoryDao.findNewestByKeyId(markKey.getKeyId());
        MarkKeyHistory newestKey = null;
        if (!newestKeys.isEmpty()) {
            newestKey = newestKeys.get(0);
        }
        if (newestKey != null && newestKey.getDisableTime() <= 0) {
            Long currentTime = System.currentTimeMillis();
            newestKey.setDisableTime(currentTime);
            markKeyHistoryDao.save(newestKey);
        }

    }

}
