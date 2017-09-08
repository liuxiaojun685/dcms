package cn.bestsec.dcms.platform.impl.securePolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import cn.bestsec.dcms.platform.api.SecurePolicy_BackupMarkKeyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_BackupMarkKeyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_BackupMarkKeyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.ZipUtil;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午8:13:41
 */
@Component
public class SecurePolicyBackupMarkKey implements SecurePolicy_BackupMarkKeyApi {
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public SecurePolicy_BackupMarkKeyResponse execute(SecurePolicy_BackupMarkKeyRequest request) throws ApiException {
        List<MarkKey> markKeys = markKeyDao.findAll(new Sort(Direction.DESC, "createTime"));
        XStream xstream = new XStream();
        File file = new File(SystemProperties.getInstance().getCacheDir() + File.separator + "mark_key.bak");

        try {
            FileOutputStream out = FileUtils.openOutputStream(file);
            xstream.toXML(markKeys, out);
            out.close();
        } catch (IOException e) {
            throw new ApiException(ErrorCode.backupMarkKeyFailed);
        }

        String zipPath = ZipUtil.zip(file.getPath(), request.getPasswd());
        if (zipPath == null) {
            throw new ApiException(ErrorCode.backupMarkKeyFailed);
        }

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_backupMarkKey)
                .admin(request.tokenWrapper().getAdmin()).operateDescription();

        SecurePolicy_BackupMarkKeyResponse resp = new SecurePolicy_BackupMarkKeyResponse();
        resp.setDownload(new File(zipPath));
        resp.setDownloadName(zipPath);
        return resp;
    }

}
