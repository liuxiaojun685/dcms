package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryDesensityApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Desensity;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryDesensityRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryDesensityResponse;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;

/**
 * 查询密级数据脱敏处理配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午6:00:41
 */
@Component
public class SystemConfigQueryDesensity implements SystemConfig_QueryDesensityApi {

    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SystemConfig_QueryDesensityResponse execute(
            SystemConfig_QueryDesensityRequest systemConfig_QueryDesensityRequest) throws ApiException {
        List<Desensity> desensityList = new ArrayList<>();
        List<FileLevelValidPeriod> findAll = fileLevelValidPeriodDao.findAll();
        for (FileLevelValidPeriod fileLevelValidPeriod : findAll) {
            Desensity desensity = new Desensity();
            desensity.setFileLevel(fileLevelValidPeriod.getFilelevel());
            desensity.setIsDesensity(fileLevelValidPeriod.getIsDesensity());
            desensityList.add(desensity);

        }
        SystemConfig_QueryDesensityResponse resp = new SystemConfig_QueryDesensityResponse();
        resp.setDesensityList(desensityList);
        return resp;
    }

}
