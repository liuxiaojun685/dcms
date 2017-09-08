package cn.bestsec.dcms.platform.impl.unit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Unit_QueryMajorListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Unit_QueryMajorListRequest;
import cn.bestsec.dcms.platform.api.model.Unit_QueryMajorListResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;

/**
 * 查询主定密单位
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月29日 上午11:49:21
 */
@Component
public class UnitQueryMajorList implements Unit_QueryMajorListApi {

    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;

    @Override
    @Transactional
    public Unit_QueryMajorListResponse execute(Unit_QueryMajorListRequest unit_QueryMajorListRequest)
            throws ApiException {
        Unit_QueryMajorListResponse resp = new Unit_QueryMajorListResponse();
        // 查找主定密单位
        FileLevelDecideUnit fileLevelDecideUnit = fileLevelDecideUnitDao
                .findByMajor(FileConsts.ToFileLevelDecideUnitType.main.getCode());
        if (fileLevelDecideUnit == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        resp.setName(fileLevelDecideUnit.getName());
        resp.setUnitNo(fileLevelDecideUnit.getUnitNo());
        resp.setDescription(fileLevelDecideUnit.getDescription());
        return resp;
    }

}
