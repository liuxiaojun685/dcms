package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryOrganizationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryOrganizationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryOrganizationResponse;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 查询组织机构代码
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年4月8日 下午8:18:03
 */
@Component
public class SystemConfigQueryOrganization implements SystemConfig_QueryOrganizationApi {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public SystemConfig_QueryOrganizationResponse execute(
            SystemConfig_QueryOrganizationRequest systemConfig_QueryOrganizationRequest) throws ApiException {
        SystemConfig_QueryOrganizationResponse resp = new SystemConfig_QueryOrganizationResponse();

        // 获取组织机构代码
        SystemConfig SystemConfig = systemConfigService.getSystemConfig();
        resp.setOrganizationCode(SystemConfig.getOrganizationCode());
        // 获取到根部门
        Department department = departmentDao.findByPkDid("did-root");
        if (department != null) {
            resp.setOrganizationName(department.getName());
            resp.setOrganizationDescription(department.getDescription());
        } else {
            return new SystemConfig_QueryOrganizationResponse(null, null, SystemConfig.getOrganizationCode());
        }
        return resp;
    }

}
