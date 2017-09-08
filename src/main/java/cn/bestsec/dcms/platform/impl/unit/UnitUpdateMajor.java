package cn.bestsec.dcms.platform.impl.unit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Unit_UpdateMajorApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Unit_UpdateMajorRequest;
import cn.bestsec.dcms.platform.api.model.Unit_UpdateMajorResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改主定密单位（只有一个）
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月29日 上午11:12:44
 */
@Component
public class UnitUpdateMajor implements Unit_UpdateMajorApi {

    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;

    @Override
    @Transactional
    public Unit_UpdateMajorResponse execute(Unit_UpdateMajorRequest unit_UpdateMajorRequest) throws ApiException {
        Unit_UpdateMajorResponse resp = new Unit_UpdateMajorResponse();
        String no = unit_UpdateMajorRequest.getUnitNo();
        FileLevelDecideUnit unit = fileLevelDecideUnitDao.findByUnitNo(no);
        // 如果已存在同样编号的辅助定密单位，报错
        if (unit != null && unit.getMajor() == CommonConsts.Bool.no.getInt()) {
            throw new ApiException(ErrorCode.unitNoAlreadyExists);
        }

        // 得到主定密单位
        FileLevelDecideUnit major = fileLevelDecideUnitDao.findByMajor(CommonConsts.Bool.yes.getInt());
        if (major == null) {
            major = new FileLevelDecideUnit();
        } else if (no != null && !no.equals(major.getUnitNo())) {
            FileLevelDecideUnit tmp = new FileLevelDecideUnit(no, major.getName(), major.getDescription(),
                    major.getMajor());
            fileLevelDecideUnitDao.delete(major);
            major = tmp;
        }

        major.setName(unit_UpdateMajorRequest.getName());
        major.setDescription(unit_UpdateMajorRequest.getDescription());

        AdminLogBuilder adminLogBuilder = unit_UpdateMajorRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.unit_updateMajor)
                .admin(unit_UpdateMajorRequest.tokenWrapper().getAdmin()).operateDescription(major.getName());
        fileLevelDecideUnitDao.save(major);

        return resp;
    }

}
