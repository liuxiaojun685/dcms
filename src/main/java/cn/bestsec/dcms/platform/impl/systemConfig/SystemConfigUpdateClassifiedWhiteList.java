package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateClassifiedWhiteListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateClassifiedWhiteListRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateClassifiedWhiteListResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

@Component
public class SystemConfigUpdateClassifiedWhiteList implements SystemConfig_UpdateClassifiedWhiteListApi {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    @Transactional
    public SystemConfig_UpdateClassifiedWhiteListResponse execute(SystemConfig_UpdateClassifiedWhiteListRequest request)
            throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        systemConfig.setClassifiedWhiteList(request.getClassifiedWhiteList());
        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_classifiedWhiteList)
                .admin(request.tokenWrapper().getAdmin()).operateDescription();
        return new SystemConfig_UpdateClassifiedWhiteListResponse();
    }

}
