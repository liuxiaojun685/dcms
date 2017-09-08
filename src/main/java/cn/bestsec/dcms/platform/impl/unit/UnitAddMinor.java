package cn.bestsec.dcms.platform.impl.unit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Unit_AddMinorApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Unit_AddMinorRequest;
import cn.bestsec.dcms.platform.api.model.Unit_AddMinorResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts.ToFileLevelDecideUnitType;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 创建定密单位
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午11:22:20
 */
@Component
public class UnitAddMinor implements Unit_AddMinorApi {
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;

    @Override
    @Transactional
    public Unit_AddMinorResponse execute(Unit_AddMinorRequest unit_AddMinorRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = unit_AddMinorRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.unit_addMinor)
                .admin(unit_AddMinorRequest.tokenWrapper().getAdmin())
                .operateDescription(unit_AddMinorRequest.getName());

        FileLevelDecideUnit fileLevelDecideUnit = fileLevelDecideUnitDao.findByUnitNo(unit_AddMinorRequest.getUnitNo());
        if (fileLevelDecideUnit != null) {
            throw new ApiException(ErrorCode.unitNoAlreadyExists);
        }
        fileLevelDecideUnit = new FileLevelDecideUnit();
        if (unit_AddMinorRequest.getName() != null) {
            fileLevelDecideUnit.setName(unit_AddMinorRequest.getName());
        }
        if (unit_AddMinorRequest.getUnitNo() != null) {
            fileLevelDecideUnit.setUnitNo(unit_AddMinorRequest.getUnitNo());
        }
        if (unit_AddMinorRequest.getDescription() != null) {
            fileLevelDecideUnit.setDescription(unit_AddMinorRequest.getDescription());
        }
        fileLevelDecideUnit.setMajor(ToFileLevelDecideUnitType.minor.getCode());
        fileLevelDecideUnitDao.save(fileLevelDecideUnit);
        Unit_AddMinorResponse resp = new Unit_AddMinorResponse();
        resp.setUnitNo(fileLevelDecideUnit.getUnitNo());
        return resp;
    }
}