package cn.bestsec.dcms.platform.impl.systemConfig;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryFileLocationApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryFileLocationRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryFileLocationResponse;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 获取文件上传存储位置 系统管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 上午11:05:13
 */
@Component
public class SystemConfigQueryFileLocation implements SystemConfig_QueryFileLocationApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryFileLocationResponse execute(
            SystemConfig_QueryFileLocationRequest systemConfig_QueryFileLocationRequest) throws ApiException {
        SystemConfig_QueryFileLocationResponse resp = new SystemConfig_QueryFileLocationResponse();
        Integer fileLevel = systemConfig_QueryFileLocationRequest.getFileLevel();
        // 文件存储位置类,查找不同密级下文件存放的位置
        StorageLocation storageLocation = systemConfigService.getFileLocation(fileLevel);
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
