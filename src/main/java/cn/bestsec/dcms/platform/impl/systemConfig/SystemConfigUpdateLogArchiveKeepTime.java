package cn.bestsec.dcms.platform.impl.systemConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateLogArchiveKeepTimeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLogArchiveKeepTimeRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLogArchiveKeepTimeResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改日志归档保留时间配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午6:00:41
 */
@Component
public class SystemConfigUpdateLogArchiveKeepTime implements SystemConfig_UpdateLogArchiveKeepTimeApi {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    public SystemConfig_UpdateLogArchiveKeepTimeResponse execute(SystemConfig_UpdateLogArchiveKeepTimeRequest request)
            throws ApiException {
        JSONObject obj = new JSONObject();
        obj.put("riskLevel1", request.getRiskLevel1());
        obj.put("riskLevel2", request.getRiskLevel2());
        obj.put("riskLevel3", request.getRiskLevel3());
        obj.put("autoLogArchiveEnable", request.getAutoLogArchiveEnable());
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        systemConfig.setLogArchiveKeepTime(obj.toJSONString());
        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_logArchiveKeepTime)
                .admin(request.tokenWrapper().getAdmin()).operateDescription();
        return new SystemConfig_UpdateLogArchiveKeepTimeResponse();
    }

}
