package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryKeywordPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryKeywordPolicyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryKeywordPolicyResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;

/**
 * 查询密点分析关键词策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日上午11:39:10
 */
@Component
public class SecurePolicyQueryKeywordPolicy implements SecurePolicy_QueryKeywordPolicyApi {
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SecurePolicy_QueryKeywordPolicyResponse execute(SecurePolicy_QueryKeywordPolicyRequest request)
            throws ApiException {
        String keyword0 = "";
        String keyword1 = "";
        String keyword2 = "";
        String keyword3 = "";
        String keyword4 = "";
        List<FileLevelValidPeriod> fileLevelValidPeriods = fileLevelValidPeriodDao.findAll();
        if (fileLevelValidPeriods == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        for (FileLevelValidPeriod fileLevelValidPeriod : fileLevelValidPeriods) {
            Integer level = fileLevelValidPeriod.getFilelevel();
            if (level == FileConsts.Level.open.getCode()) {
                keyword0 = fileLevelValidPeriod.getKeyword();
            } else if (level == FileConsts.Level.inside.getCode()) {
                keyword1 = fileLevelValidPeriod.getKeyword();
            } else if (level == FileConsts.Level.secret.getCode()) {
                keyword2 = fileLevelValidPeriod.getKeyword();
            } else if (level == FileConsts.Level.confidential.getCode()) {
                keyword3 = fileLevelValidPeriod.getKeyword();
            } else if (level == FileConsts.Level.topSecret.getCode()) {
                keyword4 = fileLevelValidPeriod.getKeyword();
            }
        }

        return new SecurePolicy_QueryKeywordPolicyResponse(keyword0, keyword1, keyword2, keyword3, keyword4);
    }

}
