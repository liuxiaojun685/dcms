package cn.bestsec.dcms.platform.impl.group;

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

import cn.bestsec.dcms.platform.api.Group_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.GroupInfo;
import cn.bestsec.dcms.platform.api.model.Group_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Group_QueryListResponse;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.entity.Group;

/**
 * 查询组列表(keyword现在还没有定)
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午9:50:15
 */
@Component
public class GroupQueryList implements Group_QueryListApi {

    @Autowired
    private GroupDao groupDao;

    @Override
    @Transactional
    public Group_QueryListResponse execute(Group_QueryListRequest group_QueryListRequest) throws ApiException {
        Specification<Group> spec = new Specification<Group>() {

            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                if (group_QueryListRequest.getKeyword() != null && !group_QueryListRequest.getKeyword().equals("")) {
                    Predicate p1 = cb.like(root.get("name").as(String.class),
                            "%" + group_QueryListRequest.getKeyword() + "%");
                    Predicate p2 = cb.like(root.get("description").as(String.class),
                            "%" + group_QueryListRequest.getKeyword() + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2));
                }
                return predicate;
            }
        };
        Pageable pageable = new PageRequest(group_QueryListRequest.getPageNumber(),
                group_QueryListRequest.getPageSize(), Sort.Direction.ASC, "name");
        Page<Group> page = groupDao.findAll(spec, pageable);

        List<Group> groups = page.getContent();
        List<GroupInfo> groupList = new ArrayList<>();
        GroupInfo groupInfo = null;
        for (Group group : groups) {
            groupInfo = new GroupInfo();
            groupInfo.setCreateTime(group.getCreateTime());
            groupInfo.setDepartmentName(group.getDepartment().getName());
            groupInfo.setDescription(group.getDescription());
            groupInfo.setGid(group.getPkGid());
            groupInfo.setName(group.getName());
            groupList.add(groupInfo);
        }
        Long totalElements = page.getTotalElements();
        Group_QueryListResponse resp = new Group_QueryListResponse();
        resp.setTotalElements(new Integer(totalElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setGroupList(groupList);
        return resp;
    }

}
