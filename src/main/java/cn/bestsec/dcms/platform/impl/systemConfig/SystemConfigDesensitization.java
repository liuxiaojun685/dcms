package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_DesensitizationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Desensity;
import cn.bestsec.dcms.platform.api.model.SystemConfig_DesensitizationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_DesensitizationResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 脱敏配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午4:50:47
 */
@Component
public class SystemConfigDesensitization implements SystemConfig_DesensitizationApi {

    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SystemConfig_DesensitizationResponse execute(
            SystemConfig_DesensitizationRequest systemConfig_DesensitizationRequest) throws ApiException {
        List<Desensity> desensityList = systemConfig_DesensitizationRequest.getDesensityList();

        // 需要脱敏的密级
        StringBuffer levelName = new StringBuffer("");
        for (Desensity desensity : desensityList) {

            if (desensity.getIsDesensity() == CommonConsts.Bool.yes.getInt()) {
                levelName.append(TextUtils.getLevel(desensity.getFileLevel()) + "|");
            }
            FileLevelValidPeriod fileLevelValidPeriod = fileLevelValidPeriodDao
                    .findByfilelevel(desensity.getFileLevel());
            fileLevelValidPeriod.setIsDesensity(desensity.getIsDesensity());
            fileLevelValidPeriodDao.save(fileLevelValidPeriod);
        }

        // 记录操作日志
        AdminLogBuilder adminLogBuilder = systemConfig_DesensitizationRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_desensitization)
                .admin(systemConfig_DesensitizationRequest.tokenWrapper().getAdmin()).operateDescription(levelName);

        return new SystemConfig_DesensitizationResponse();
    }

}
