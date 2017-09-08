package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryValidPeriodListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryValidPeriodListRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryValidPeriodListResponse;
import cn.bestsec.dcms.platform.api.model.ValidPeriodInfo;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;

/**
 * 查询默认保密期限列表
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日上午11:39:10
 */
@Component
public class SecurePolicyQueryValidPeriodList implements SecurePolicy_QueryValidPeriodListApi {
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SecurePolicy_QueryValidPeriodListResponse execute(
            SecurePolicy_QueryValidPeriodListRequest securePolicy_QueryValidPeriodListRequest) throws ApiException {

        SecurePolicy_QueryValidPeriodListResponse resp = new SecurePolicy_QueryValidPeriodListResponse();
        List<FileLevelValidPeriod> fileLevelValidPeriods = fileLevelValidPeriodDao.findAll();
        if (fileLevelValidPeriods == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<ValidPeriodInfo> validPeriodList = new ArrayList<>();

        ValidPeriodInfo info = null;
        for (FileLevelValidPeriod fileLevelValidPeriod : fileLevelValidPeriods) {
            info = new ValidPeriodInfo();
            info.setFileLevel(fileLevelValidPeriod.getFilelevel());
            info.setValidPeriod(fileLevelValidPeriod.getValidPeriod());
            validPeriodList.add(info);
        }
        resp.setValidPeriodList(validPeriodList);

        return resp;
    }

}
