package cn.bestsec.dcms.platform.impl.securePolicy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_UpdateValidPeriodApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateValidPeriodRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_UpdateValidPeriodResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改默认保密期限策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午4:59:59
 */
@Component
public class SecurePolicyUpdateValidPeriod implements SecurePolicy_UpdateValidPeriodApi {
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SecurePolicy_UpdateValidPeriodResponse execute(
            SecurePolicy_UpdateValidPeriodRequest securePolicy_UpdateValidPeriodRequest) throws ApiException {

        SecurePolicy_UpdateValidPeriodResponse resp = new SecurePolicy_UpdateValidPeriodResponse();

        FileLevelValidPeriod fileLevelValidPeriod = fileLevelValidPeriodDao
                .findByfilelevel(securePolicy_UpdateValidPeriodRequest.getFileLevel());
        if (fileLevelValidPeriod == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = securePolicy_UpdateValidPeriodRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.secure_secretPeriod)
                .admin(securePolicy_UpdateValidPeriodRequest.tokenWrapper().getAdmin())
                .operateDescription(FileConsts.Level.parse(fileLevelValidPeriod.getFilelevel()),
                        TimeConsts.printClassificationPeriod(fileLevelValidPeriod.getValidPeriod()),
                        TimeConsts.printClassificationPeriod(securePolicy_UpdateValidPeriodRequest.getValidPeriod()));

        if (securePolicy_UpdateValidPeriodRequest.getValidPeriod() != null) {
            fileLevelValidPeriod.setValidPeriod(securePolicy_UpdateValidPeriodRequest.getValidPeriod());
        }
        fileLevelValidPeriodDao.save(fileLevelValidPeriod);
        return resp;
    }

}
