package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryTrustedAppListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryTrustedAppListRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryTrustedAppListResponse;
import cn.bestsec.dcms.platform.api.model.TrustedAppInfo;
import cn.bestsec.dcms.platform.dao.TrustedApplicationDao;
import cn.bestsec.dcms.platform.entity.TrustedApplication;

/**
 * 查询可信应用程序列表
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日上午11:14:04
 */
@Component
public class SystemConfigQueryTrustedAppList implements SystemConfig_QueryTrustedAppListApi {
    @Autowired
    private TrustedApplicationDao trustedApplicationDao;

    @Override
    @Transactional
    public SystemConfig_QueryTrustedAppListResponse execute(
            SystemConfig_QueryTrustedAppListRequest systemConfig_QueryTrustedAppListRequest) throws ApiException {
        SystemConfig_QueryTrustedAppListResponse resp = new SystemConfig_QueryTrustedAppListResponse();
        Pageable pageable = new PageRequest(systemConfig_QueryTrustedAppListRequest.getPageNumber(),
                systemConfig_QueryTrustedAppListRequest.getPageSize());
        Page<TrustedApplication> page = trustedApplicationDao.findAll(pageable);
        List<TrustedApplication> trustedApplications = page.getContent();
        List<TrustedAppInfo> trustedAppInfos = new ArrayList<>();
        for (TrustedApplication trustedApplication : trustedApplications) {
            TrustedAppInfo info = new TrustedAppInfo();
            info.setDescription(trustedApplication.getDescription());
            info.setProcessName(trustedApplication.getProcessName());
            info.setTrustedAppId(trustedApplication.getId());
            info.setExtensionName(trustedApplication.getExtensionName());
            trustedAppInfos.add(info);
        }
        // 总行数
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setTrustedAppList(trustedAppInfos);

        return resp;
    }

}
