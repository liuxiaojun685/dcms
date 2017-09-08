package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateOrganizationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateOrganizationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateOrganizationResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 更新组织机构
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年4月8日 下午8:08:38
 */
@Component
public class SystemConfigUpdateOrganization implements SystemConfig_UpdateOrganizationApi {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateOrganizationResponse execute(
            SystemConfig_UpdateOrganizationRequest systemConfig_UpdateOrganizationRequest) throws ApiException {
        String organizationCode = systemConfig_UpdateOrganizationRequest.getOrganizationCode();
        String organizationName = systemConfig_UpdateOrganizationRequest.getOrganizationName();
        // 获取到根部门
//        Department department = departmentDao.findByPkDid("did-root");
//        department.setName(organizationName);
        String organizationDescription = systemConfig_UpdateOrganizationRequest.getOrganizationDescription();
//        if (organizationDescription != null && !organizationDescription.isEmpty()) {
//            department.setDescription(organizationDescription);
//        }
//        departmentDao.save(department);
        // 更新组织机构代码
        SystemConfig SystemConfig = systemConfigService.getSystemConfig();
        SystemConfig.setOrganizationCode(organizationCode);
        systemConfigDao.save(SystemConfig);

        FileLevelDecideUnit majorUnit = fileLevelDecideUnitDao
                .findByMajor(FileConsts.ToFileLevelDecideUnitType.main.getCode());
        if (majorUnit == null) {
            majorUnit = new FileLevelDecideUnit(organizationCode, organizationName, organizationDescription, 1);
            fileLevelDecideUnitDao.save(majorUnit);
        }

        // 操作日志
        AdminLogBuilder adminLogBuilder = systemConfig_UpdateOrganizationRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_updateOrganization)
                .admin(systemConfig_UpdateOrganizationRequest.tokenWrapper().getAdmin())
                .operateDescription(systemConfig_UpdateOrganizationRequest.getOrganizationName(),
                        systemConfig_UpdateOrganizationRequest.getOrganizationCode());
        return new SystemConfig_UpdateOrganizationResponse();
    }

}
