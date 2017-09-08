package cn.bestsec.dcms.platform.impl.license;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.License_QueryApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.License_QueryRequest;
import cn.bestsec.dcms.platform.api.model.License_QueryResponse;
import cn.bestsec.dcms.platform.dao.LicenseDao;
import cn.bestsec.dcms.platform.entity.License;
import cn.bestsec.dcms.platform.utils.LicenseConfiguration;
import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * 查询授权信息
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日上午11:58:49
 */
@Component
public class LicenseQuery implements License_QueryApi {
    @Autowired
    private LicenseDao licenseDao;

    @Override
    @Transactional
    public License_QueryResponse execute(License_QueryRequest license_QueryRequest) throws ApiException {
        License_QueryResponse resp = new License_QueryResponse();
        resp.setSource(ServerEnv.getHardwareFeature());
        List<License> licenses = licenseDao.findAll();
        if (licenses.isEmpty()) {
            return resp;
        }
        String licenseText = licenses.get(0).getLicense();
        try {
            if (licenseText != null && !licenseText.isEmpty()) {
                LicenseConfiguration.License lic = LicenseConfiguration.getInstance().decodeLicense(licenseText);
                resp.setCustomId(lic.customId);
                resp.setCustomName(lic.customName);
                resp.setState(lic.versionState);
                resp.setOnlineMax(lic.onlineMax);
                resp.setOfflineMax(lic.offlineMax);
                resp.setMiddlewareMax(lic.middlewareMax);
                if (lic.invalidTime != null) {
                    resp.setEndTime(lic.invalidTime.getTime());
                }
                resp.setStartTime(licenses.get(0).getCreateTime());
            }
        } catch (Throwable e) {
        }
        return resp;
    }
}
