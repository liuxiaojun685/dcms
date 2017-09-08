package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryLogLocationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLogLocationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryLogLocationResponse;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 获取日志上传存储位置
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月29日下午12:04:36
 */
@Component
public class SystemConfigQueryLogLocation implements SystemConfig_QueryLogLocationApi {
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryLogLocationResponse execute(
            SystemConfig_QueryLogLocationRequest systemConfig_QueryLogLocationRequest) throws ApiException {
        StorageLocation storageLocation = systemConfigService.getLogLocation();
        SystemConfig_QueryLogLocationResponse resp = new SystemConfig_QueryLogLocationResponse();
        if (storageLocation != null) {
            resp.setAccount(storageLocation.getAccount());
            resp.setDomainName(storageLocation.getDomainName());
            resp.setIp(storageLocation.getIp());
            resp.setPasswd(StringEncrypUtil.decrypt(storageLocation.getPasswd()));
            resp.setPath(storageLocation.getFilePath());
            resp.setPort(storageLocation.getPort());
            resp.setProtocol(storageLocation.getProtocol());
        }

        return resp;
    }

}
