package cn.bestsec.dcms.platform.impl.systemConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryLogArchiveKeepTimeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLogArchiveKeepTimeRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLogArchiveKeepTimeResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 查询日志归档保留时间配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午6:00:41
 */
@Component
public class SystemConfigQueryLogArchiveKeepTime implements SystemConfig_QueryLogArchiveKeepTimeApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public SystemConfig_QueryLogArchiveKeepTimeResponse execute(SystemConfig_QueryLogArchiveKeepTimeRequest request)
            throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        String json = systemConfig.getLogArchiveKeepTime();
        String riskLevel1 = "3";
        String riskLevel2 = "6";
        String riskLevel3 = "12";
        Integer enable = 0;
        try {
            JSONObject obj = JSONObject.parseObject(json);
            riskLevel1 = obj.getString("riskLevel1");
            riskLevel2 = obj.getString("riskLevel2");
            riskLevel3 = obj.getString("riskLevel3");
            enable = obj.getInteger("autoLogArchiveEnable");
        } catch (Exception e) {
        }
        return new SystemConfig_QueryLogArchiveKeepTimeResponse(riskLevel1, riskLevel2, riskLevel3, enable);
    }

}
