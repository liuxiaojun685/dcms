package cn.bestsec.dcms.platform.impl.license;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.License_ImportFileApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.License_ImportFileRequest;
import cn.bestsec.dcms.platform.api.model.License_ImportFileResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.LicenseDao;
import cn.bestsec.dcms.platform.entity.License;
import cn.bestsec.dcms.platform.service.InitializationService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.LicenseConfiguration;
import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月21日 下午4:57:08
 */
@Component
public class LicenseImportFile implements License_ImportFileApi {
    @Autowired
    private LicenseDao licenseDao;
    @Autowired
    private InitializationService initializationService;

    @Override
    public License_ImportFileResponse execute(License_ImportFileRequest license_ImportFileRequest) throws ApiException {
        long currentTime = System.currentTimeMillis();
        File attachment = license_ImportFileRequest.getAttachment();
        try {
            List<String> lines = IOUtils.readLines(new FileInputStream(attachment), "UTF-8");
            String licenseText = lines.get(0);

            AdminLogBuilder adminLogBuilder = license_ImportFileRequest.createAdminLogBuilder();
            adminLogBuilder.operateTime(currentTime).operation(AdminLogOp.license_import).operateDescription();

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
        return new License_ImportFileResponse();
    }

}
