package cn.bestsec.dcms.platform.impl.securePolicy;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_ImportMarkKeyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_ImportMarkKeyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_ImportMarkKeyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午8:13:41
 */
@Component
public class SecurePolicyImportMarkKey implements SecurePolicy_ImportMarkKeyApi {
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public SecurePolicy_ImportMarkKeyResponse execute(SecurePolicy_ImportMarkKeyRequest request) throws ApiException {
        String key = null;
        File attachment = request.getAttachment();
        if (attachment == null) {
            throw new ApiException(ErrorCode.attachmentNotFound);
        }
        FileInputStream input = null;
        try {
            input = new FileInputStream(attachment);
            List<String> list = IOUtils.readLines(input, "GB2312");
            if (list == null || list.isEmpty()) {
                throw new ApiException(ErrorCode.invalidMarkKeyFile);
            }
            key = list.get(0);
            if (key.isEmpty()) {
                throw new ApiException(ErrorCode.invalidMarkKeyFile);
            }
        } catch (Throwable e) {
            throw new ApiException(ErrorCode.invalidMarkKeyFile);
        }
        if (input != null) {
            IOUtils.closeQuietly(input);
        }
        Admin admin = request.tokenWrapper().getAdmin();
        Integer markVersion = 1;
        String keyId = IdUtils.randomId();
        String keyName = request.getKeyName();
        Integer keyLength = key.length();
        Long createTime = System.currentTimeMillis();
        String keyEnc = StringEncrypUtil.encrypt(key);
        MarkKey markKey = new MarkKey(admin, keyEnc, keyEnc, markVersion, keyId, keyName, keyLength, createTime, 2, 0);
        markKeyDao.save(markKey);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_importMarkKey).admin(admin)
                .operateDescription(keyId);
        return new SecurePolicy_ImportMarkKeyResponse();
    }

}
