package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateLogLocationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LocationInfo;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLogLocationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateLogLocationResponse;
import cn.bestsec.dcms.platform.dao.StorageLocationDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改日志上传位置 系统管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午1:46:18
 */
@Component
public class SystemConfigUpdateLogLocation implements SystemConfig_UpdateLogLocationApi {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private StorageLocationDao storageLocationDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_UpdateLogLocationResponse execute(
            SystemConfig_UpdateLogLocationRequest systemConfig_UpdateLogLocationRequest) throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        LocationInfo location = systemConfig_UpdateLogLocationRequest.getLogLocation();
        StorageLocation storageLocation = new StorageLocation();
        List<StorageLocation> sls = storageLocationDao.findByDomainNameAndIpAndPortAndFilePathAndProtocol(
                location.getDomainName(), location.getIp(), location.getPort(), location.getPath(),
                location.getProtocol());
        if (!sls.isEmpty()) {
            storageLocation = sls.get(sls.size() - 1);
        }
        storageLocation.setDomainName(location.getDomainName());
        storageLocation.setIp(location.getIp());
        storageLocation.setPort(location.getPort());
        storageLocation.setFilePath(location.getPath());
        storageLocation.setProtocol(location.getProtocol());
        storageLocation.setAccount(location.getAccount());
        storageLocation.setPasswd(StringEncrypUtil.encrypt(location.getPasswd()));

        systemConfig.setStorageLocationByFkLogLocationId(storageLocation);
        storageLocationDao.save(storageLocation);
        systemConfigDao.save(systemConfig);

        return new SystemConfig_UpdateLogLocationResponse();
    }

}
