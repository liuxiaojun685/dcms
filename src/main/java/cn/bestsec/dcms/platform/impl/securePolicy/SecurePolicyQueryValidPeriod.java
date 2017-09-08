package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryValidPeriodApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryValidPeriodRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryValidPeriodResponse;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;

/**
 * 查询默认保密期限
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午12:21:51
 */
@Component
public class SecurePolicyQueryValidPeriod implements SecurePolicy_QueryValidPeriodApi {
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SecurePolicy_QueryValidPeriodResponse execute(
            SecurePolicy_QueryValidPeriodRequest securePolicy_QueryValidPeriodRequest) throws ApiException {
        SecurePolicy_QueryValidPeriodResponse resp = new SecurePolicy_QueryValidPeriodResponse();
        FileLevelValidPeriod fileLevelValidPeriod = fileLevelValidPeriodDao
                .findByfilelevel(securePolicy_QueryValidPeriodRequest.getFileLevel());
        if (fileLevelValidPeriod == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        resp.setValidPeriod(fileLevelValidPeriod.getValidPeriod());

        return resp;
    }

}
