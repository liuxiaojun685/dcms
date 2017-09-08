package cn.bestsec.dcms.platform.impl.unit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Unit_QueryMinorListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.UnitItem;
import cn.bestsec.dcms.platform.api.model.Unit_QueryMinorListRequest;
import cn.bestsec.dcms.platform.api.model.Unit_QueryMinorListResponse;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;

/**
 * 查询辅助定密单位列表 无权限限制
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月29日 上午10:52:34
 */
@Component
public class UnitQueryMinorList implements Unit_QueryMinorListApi {

    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;

    @Override
    @Transactional
    public Unit_QueryMinorListResponse execute(Unit_QueryMinorListRequest unit_QueryMinorListRequest)
            throws ApiException {
        Unit_QueryMinorListResponse resp = new Unit_QueryMinorListResponse();
        // 查询辅助定密单位
        Specification<FileLevelDecideUnit> spec = new Specification<FileLevelDecideUnit>() {

            @Override
            public Predicate toPredicate(Root<FileLevelDecideUnit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicate = cb.equal(root.get("major"), CommonConsts.Bool.no.getInt());
                return predicate;
            }
        };
        List<FileLevelDecideUnit> unitData = fileLevelDecideUnitDao.findAll(spec, new Sort(Sort.Direction.ASC, "unitNo"));
        List<UnitItem> unitList = new ArrayList<UnitItem>();
        for (FileLevelDecideUnit fileLevelDecideUnit : unitData) {
            unitList.add(new UnitItem(fileLevelDecideUnit.getUnitNo(), fileLevelDecideUnit.getName(), fileLevelDecideUnit.getDescription()));
        }
        resp.setUnitList(unitList);
        return resp;
    }

}
