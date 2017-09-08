package cn.bestsec.dcms.platform.impl.securePolicy;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import cn.bestsec.dcms.platform.api.SecurePolicy_RecoverMarkKeyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_RecoverMarkKeyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_RecoverMarkKeyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午8:13:41
 */
@Component
public class SecurePolicyRecoverMarkKey implements SecurePolicy_RecoverMarkKeyApi {
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public SecurePolicy_RecoverMarkKeyResponse execute(SecurePolicy_RecoverMarkKeyRequest request) throws ApiException {
        File attachment = request.getAttachment();
        if (attachment == null) {
            throw new ApiException(ErrorCode.attachmentNotFound);
        }

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_recoverMarkKey)
                .admin(request.tokenWrapper().getAdmin()).operateDescription();

        XStream xstream = new XStream();
        List<MarkKey> markKeys = null;
        try {
            markKeys = (List<MarkKey>) xstream.fromXML(attachment);
        } catch (Throwable e) {
            throw new ApiException(ErrorCode.invalidMarkKeyBackupFile);
        }
        if (markKeys == null) {
            throw new ApiException(ErrorCode.invalidMarkKeyBackupFile);
        }
        for (MarkKey markKey : markKeys) {
            if (markKey.getEnable() == 1) {
                markKey.setEnable(0);
            }
        }
        markKeyDao.save(markKeys);
        return new SecurePolicy_RecoverMarkKeyResponse();
    }

}
