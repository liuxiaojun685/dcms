package cn.bestsec.dcms.platform.impl.department;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.DepartmentInfo;
import cn.bestsec.dcms.platform.api.model.Department_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Department_QueryListResponse;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.entity.Department;

/**
 * 查询部门列表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月5日 下午5:47:11
 */
@Component
public class DepartmentQueryList implements Department_QueryListApi {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Department_QueryListResponse execute(Department_QueryListRequest department_QueryListRequest)
            throws ApiException {
        // 分页数据,根据关键字模糊查询,未删除数据
        String keyword = department_QueryListRequest.getKeyword();
        Specification<Department> spec = new Specification<Department>() {

            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate queryCriteria = cb.notEqual(root.get("departmentState").as(Integer.class),
                        DepartmentConsts.state.deleted.getCode());
                Predicate pName = null;
                Predicate pDescription = null;
                if (keyword != null && !keyword.isEmpty()) {
                    pName = cb.like(root.get("name").as(String.class), "%" + keyword + "%");
                    pDescription = cb.like(root.get("description").as(String.class), "%" + keyword + "%");
                    queryCriteria = cb.and(queryCriteria, cb.or(pDescription, pName));
                }
                return queryCriteria;
            }
        };
        Pageable pageable = new PageRequest(department_QueryListRequest.getPageNumber(),
                department_QueryListRequest.getPageSize(), Sort.Direction.ASC, "name");
        Page<Department> page = departmentDao.findAll(spec, pageable);
        List<Department> departmentList = page.getContent();
        if (departmentList == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        // 返回响应数据
        List<DepartmentInfo> departmentInfoList = new ArrayList<DepartmentInfo>();
        for (Department department : departmentList) {
            if (department.getDepartmentState() == DepartmentConsts.state.deleted.getCode()) {
                continue;
            }
            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setDescription(department.getDescription());
            departmentInfo.setDid(department.getPkDid());
            departmentInfo.setName(department.getName());
            // 父部们名字
            if (department.getFkParentId() != null && !department.getFkParentId().isEmpty()) {
                Department findByPkDid = departmentDao.findByPkDid(department.getFkParentId());
                departmentInfo.setParentName(findByPkDid.getName());
            } else {
                departmentInfo.setParentName("");
            }
            departmentInfoList.add(departmentInfo);
        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        Department_QueryListResponse resp = new Department_QueryListResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setDepartmentList(departmentInfoList);
        return resp;
    }

}
