package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryEnvironmentApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryEnvironmentRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryEnvironmentResponse;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * 查看系统信息和资源使用情况 权限:系统管理员
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午4:34:14
 */
@Component
public class SystemConfigQueryEnvironment implements SystemConfig_QueryEnvironmentApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryEnvironmentResponse execute(
            SystemConfig_QueryEnvironmentRequest systemConfig_QueryEnvironmentRequest) throws ApiException {
        // 得到系统的配置
        SystemConfig config = systemConfigService.getSystemConfig();
        SystemConfig_QueryEnvironmentResponse resp = new SystemConfig_QueryEnvironmentResponse();
        // 操作系统

        StorageLocation fileLocation = config.getStorageLocationByFkFileLevel3locationId();
        String fileFree = LocationUtils.getFreeSpace(fileLocation);
        String fileTotal = LocationUtils.getTotalSpace(fileLocation);
        StorageLocation logLocation = config.getStorageLocationByFkLogLocationId();
        String logFree = LocationUtils.getFreeSpace(logLocation);
        String logTotal = LocationUtils.getTotalSpace(logLocation);

        resp.setCpuFree(ServerEnv.getCpuRatio());
        resp.setCpuInfo(ServerEnv.getCpuInfo());
        resp.setDatebase("MySQL Server 5.7 64bit for Windows");
        resp.setFileFree(fileFree);
        resp.setFileTotal(fileTotal);
        resp.setHdFree(ServerEnv.getFreeHdSpace());
        resp.setHdTotal(ServerEnv.getTotalHdSpace());
        resp.setLogFree(logFree);
        resp.setLogTotal(logTotal);
        resp.setMemFree(ServerEnv.getFreeMemory());
        resp.setMemTotal(ServerEnv.getTotalMemory());
        resp.setOsType(ServerEnv.getOsType());
        resp.setPlatformVersion(ServerEnv.getServerVersion());
        // resp.setPlatformVersion(SystemProperties.getInstance().getVersionBranch()
        // + " "
        // + SystemProperties.getInstance().getVersionMajor() + "."
        // + SystemProperties.getInstance().getVersionCode());
        return resp;
    }

}
