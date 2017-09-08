package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_DeleteTrustedAppByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SystemConfig_DeleteTrustedAppByIdRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_DeleteTrustedAppByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.TrustedApplicationDao;
import cn.bestsec.dcms.platform.entity.TrustedApplication;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除可信应用程序
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午5:44:26
 */
@Component
public class SystemConfigDeleteTrustedAppById implements SystemConfig_DeleteTrustedAppByIdApi {

    @Autowired
    private TrustedApplicationDao trustedApplicationDao;

    @Override
    @Transactional
    public SystemConfig_DeleteTrustedAppByIdResponse execute(
            SystemConfig_DeleteTrustedAppByIdRequest systemConfig_DeleteTrustedAppByIdRequest) throws ApiException {
        SystemConfig_DeleteTrustedAppByIdResponse resp = new SystemConfig_DeleteTrustedAppByIdResponse();
        TrustedApplication trustedApplication = trustedApplicationDao
                .findById(systemConfig_DeleteTrustedAppByIdRequest.getTrustedAppId());
        if (trustedApplication == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        trustedApplicationDao.delete(trustedApplication);

        AdminLogBuilder adminLogBuilder = systemConfig_DeleteTrustedAppByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_deleteTrust)
                .admin(systemConfig_DeleteTrustedAppByIdRequest.tokenWrapper().getAdmin())
                .operateDescription(trustedApplication.getProcessName());
        return resp;
    }

}
