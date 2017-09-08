package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdateKeywordPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateKeywordPolicyRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateKeywordPolicyResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改密点分析关键词策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午4:59:59
 */
@Component
public class SecurePolicyUpdateKeywordPolicy implements SecurePolicy_UpdateKeywordPolicyApi {
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SecurePolicy_UpdateKeywordPolicyResponse execute(SecurePolicy_UpdateKeywordPolicyRequest request)
            throws ApiException {
        if (request.getKeyword0() != null) {
            saveKeyword(FileConsts.Level.open.getCode(), request.getKeyword0());
        }
        if (request.getKeyword1() != null) {
            saveKeyword(FileConsts.Level.inside.getCode(), request.getKeyword1());
        }
        if (request.getKeyword2() != null) {
            saveKeyword(FileConsts.Level.secret.getCode(), request.getKeyword2());
        }
        if (request.getKeyword3() != null) {
            saveKeyword(FileConsts.Level.confidential.getCode(), request.getKeyword3());
        }
        if (request.getKeyword4() != null) {
            saveKeyword(FileConsts.Level.topSecret.getCode(), request.getKeyword4());
        }

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_updateKeywordPolicy)
                .admin(request.tokenWrapper().getAdmin()).operateDescription();
        return new SecurePolicy_UpdateKeywordPolicyResponse();
    }

    private void saveKeyword(Integer code, String keyword) {
        FileLevelValidPeriod fileLevelValidPeriod = fileLevelValidPeriodDao.findByfilelevel(code);
        fileLevelValidPeriod.setKeyword(keyword);
        fileLevelValidPeriodDao.save(fileLevelValidPeriod);
    }

}
