package cn.bestsec.dcms.platform.impl.license;

import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.License_ExportApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.License_ExportRequest;
import cn.bestsec.dcms.platform.api.model.License_ExportResponse;
import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * 授权导出
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 上午10:39:37
 */
@Component
public class LicenseExport implements License_ExportApi {

    @Override
    public License_ExportResponse execute(License_ExportRequest license_ExportRequest) throws ApiException {
        License_ExportResponse resp = new License_ExportResponse();
        resp.setSource(ServerEnv.getHardwareFeature());
        return resp;
    }

}
