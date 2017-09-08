package cn.bestsec.dcms.platform.impl.license;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.License_ImportApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.License_ImportRequest;
import cn.bestsec.dcms.platform.api.model.License_ImportResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.LicenseDao;
import cn.bestsec.dcms.platform.entity.License;
import cn.bestsec.dcms.platform.service.InitializationService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.LicenseConfiguration;
import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * 授权导入
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 上午10:42:24
 */
@Component
public class LicenseImport implements License_ImportApi {
    @Autowired
    private LicenseDao licenseDao;
    @Autowired
    private InitializationService initializationService;

    @Override
    @Transactional
    public License_ImportResponse execute(License_ImportRequest license_ImportRequest) throws ApiException {
        long currentTime = System.currentTimeMillis();

        AdminLogBuilder adminLogBuilder = license_ImportRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(currentTime).operation(AdminLogOp.license_import).operateDescription();

        String licenseText = license_ImportRequest.getLicenseText();
        try {
            if (!licenseText.isEmpty()) {
                LicenseConfiguration.License lic = LicenseConfiguration.getInstance().decodeLicense(licenseText);
                if (lic.features == null || !lic.features.equals(ServerEnv.getHardwareFeature())) {
                    throw new ApiException(ErrorCode.invalidLicense);
                }
                if (lic.invalidTime == null || lic.invalidTime.getTime() <= currentTime) {
                    throw new ApiException(ErrorCode.invalidLicense);
                }
            } else {
                throw new ApiException(ErrorCode.invalidLicense);
            }
            License license;
            List<License> licenses = licenseDao.findAll();
            if (licenses.isEmpty()) {
                license = new License();
            } else {
                license = licenses.get(0);
            }
            if (license.getLicense() == null || license.getLicense().isEmpty()) {
                initializationService.initialize();
            }
            license.setCreateTime(currentTime);
            license.setLicense(licenseText);
            licenseDao.save(license);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.invalidLicense);
        }

        License_ImportResponse resp = new License_ImportResponse();
        return resp;
    }

}
