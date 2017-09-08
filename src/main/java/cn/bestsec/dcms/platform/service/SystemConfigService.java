package cn.bestsec.dcms.platform.service;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年5月26日 下午3:40:49
 */
public interface SystemConfigService {
    SystemConfig getSystemConfig() throws ApiException;
    StorageLocation getBackupLocation() throws ApiException;
    StorageLocation getPatchLocation() throws ApiException;
    StorageLocation getLogLocation() throws ApiException;
    StorageLocation getFileLocation(Integer level) throws ApiException;
}
