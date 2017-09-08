package cn.bestsec.dcms.platform.impl.unit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Unit_DeleteMinorApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Unit_DeleteMinorRequest;
import cn.bestsec.dcms.platform.api.model.Unit_DeleteMinorResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除定密单位
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午3:29:08
 */
@Component
public class UnitDeleteMinor implements Unit_DeleteMinorApi {
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;

    @Override
    @Transactional
    public Unit_DeleteMinorResponse execute(Unit_DeleteMinorRequest unit_DeleteMinorRequest) throws ApiException {
        Unit_DeleteMinorResponse resp = new Unit_DeleteMinorResponse();
        FileLevelDecideUnit fileLevelDecideUnit = fileLevelDecideUnitDao
                .findByUnitNo(unit_DeleteMinorRequest.getUnitNo());
        if (fileLevelDecideUnit == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        fileLevelDecideUnitDao.delete(fileLevelDecideUnit);

        AdminLogBuilder adminLogBuilder = unit_DeleteMinorRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.unit_deleteMinor)
                .admin(unit_DeleteMinorRequest.tokenWrapper().getAdmin())
                .operateDescription(fileLevelDecideUnit.getName());
        
        return resp;
    }

}
