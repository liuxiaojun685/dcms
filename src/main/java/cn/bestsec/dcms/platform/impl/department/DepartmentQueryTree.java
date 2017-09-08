package cn.bestsec.dcms.platform.impl.department;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Department_QueryTreeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.DepartmentNode;
import cn.bestsec.dcms.platform.api.model.Department_QueryTreeRequest;
import cn.bestsec.dcms.platform.api.model.Department_QueryTreeResponse;
import cn.bestsec.dcms.platform.api.model.GroupNode;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.consts.GroupConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.FileLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.FileLevelAccessPolicy;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 查看部门树
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午10:11:44
 */
@Component
public class DepartmentQueryTree implements Department_QueryTreeApi {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileLevelAccessPolicyDao fileLevelAccessPolicyDao;

    @Override
    @Transactional
    public Department_QueryTreeResponse execute(Department_QueryTreeRequest department_QueryTreeRequest)
            throws ApiException {
        boolean includeGroup = department_QueryTreeRequest.getHasGroup() != 0;
        boolean includeUser = department_QueryTreeRequest.getHasUser() != 0;
        Integer fileLevel = department_QueryTreeRequest.getFileLevel();
        String keyword = department_QueryTreeRequest.getKeyword();

        Department_QueryTreeResponse resp = new Department_QueryTreeResponse();

        // 查询根部门
        List<Department> ret = departmentDao.findByRoot(1);
        if (ret == null) {
            throw new ApiException(ErrorCode.departmentNotExistsRoot);
        }
        Department root = ret.iterator().next();

        resp.setDid(root.getPkDid());
        resp.setName(root.getName());
        // 如果需要包含用户，装填根部门的用户节点
        if (includeUser) {
            List<UserSimpleInfo> clildUsers = installUserNode(root, fileLevel, keyword);
            // if (!clildUsers.isEmpty()) {
            resp.setChildUserList(clildUsers);
            // }
        }
        // 如果需要包含组，装填根部门的组节点。
        if (includeGroup) {
            List<GroupNode> clildGroups = installGroupNode(root, includeUser, fileLevel, keyword);
            // if (!clildGroups.isEmpty()) {
            resp.setChildGroupList(clildGroups);
            // }
        }
        // 装填部门节点
        List<DepartmentNode> clildDepartments = installDepartmentNode(root, includeUser, includeGroup, fileLevel,
                keyword);
        // if (!clildDepartments.isEmpty()) {
        resp.setChildDepartmentList(clildDepartments);
        // }

        // 筛选部门关键词
        List<String> likelyDids = null;
        if (keyword != null && keyword.length() > 0 && !includeUser && !includeGroup) {
            likelyDids = departmentDao.findDidLikeKeyword("%" + keyword + "%");
            if (!likelyDids.isEmpty()) {
                filterLikelyChildDepartment(clildDepartments, likelyDids);
            }
        }

        return resp;
    }

    public boolean filterLikelyChildDepartment(List<DepartmentNode> nodes, List<String> likelyDids) {
        boolean likely = false;
        Iterator<DepartmentNode> it = nodes.iterator();
        while (it.hasNext()) {
            DepartmentNode node = it.next();
            if (likelyDids.contains(node.getDid())) {
                likely = true;
                continue;
            }
            if (filterLikelyChildDepartment(node.getChildDepartmentList(), likelyDids)) {
                likely = true;
                continue;
            }
            it.remove();
        }
        return likely;
    }

    private List<DepartmentNode> installDepartmentNode(Department department, boolean includeUser, boolean includeGroup,
            Integer fileLevel, String keyword) {
        // 筛选部门
        Specification<Department> departmentSpec = new Specification<Department>() {

            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate notDelete = cb.notEqual(root.get("departmentState").as(Integer.class),
                        DepartmentConsts.state.deleted.getCode());
                Predicate parent = cb.equal(root.get("fkParentId").as(String.class), department.getPkDid());
                Predicate predicate = cb.and(notDelete, parent);
                return predicate;
            }
        };
        List<DepartmentNode> departmentNodes = new ArrayList<DepartmentNode>();
        List<Department> departments = departmentDao.findAll(departmentSpec, new Sort(Sort.Direction.ASC, "name"));
        if (departments != null) {
            for (Department childDepartment : departments) {
                // 无keyword筛选时或者筛选部门时必须装入此节点。如果有筛选时有子节点才装入
                boolean needInstallNode = (keyword == null || keyword.isEmpty() || !(includeUser || includeGroup));
                DepartmentNode departmentNode = new DepartmentNode();
                departmentNode.setDid(childDepartment.getPkDid());
                departmentNode.setName(childDepartment.getName());
                // 如果需要包含用户，装填部门的用户节点
                if (includeUser) {
                    List<UserSimpleInfo> clildUsers = installUserNode(childDepartment, fileLevel, keyword);
                    // if (!clildUsers.isEmpty()) {
                    departmentNode.setChildUserList(clildUsers);
                    if (keyword != null && keyword.length() > 0 && !clildUsers.isEmpty()) {
                        // 如果需要keyword筛选并且有匹配的子节点，装入此节点
                        needInstallNode |= true;
                    }
                    // }
                }
                // 如果需要包含组，装填部门的组节点。
                if (includeGroup) {
                    List<GroupNode> clildGroups = installGroupNode(childDepartment, includeUser, fileLevel, keyword);
                    // if (!clildGroups.isEmpty()) {
                    departmentNode.setChildGroupList(clildGroups);
                    if (keyword != null && keyword.length() > 0 && !clildGroups.isEmpty()) {
                        // 如果需要keyword筛选并且有匹配的子节点，装入此节点
                        needInstallNode |= true;
                    }
                    // }
                }
                // 装填部门节点
                List<DepartmentNode> clildDepartments = installDepartmentNode(childDepartment, includeUser,
                        includeGroup, fileLevel, keyword);
                // if (!clildDepartments.isEmpty()) {
                departmentNode.setChildDepartmentList(clildDepartments);
                if (keyword != null && keyword.length() > 0 && !clildDepartments.isEmpty()) {
                    // 如果需要keyword筛选并且有匹配的子节点，装入此节点
                    needInstallNode |= true;
                }
                // }
                if (needInstallNode)
                    departmentNodes.add(departmentNode);
            }
        }
        return departmentNodes;
    }

    private List<GroupNode> installGroupNode(Department department, boolean includeUser, Integer fileLevel,
            String keyword) {
        // 筛选组
        Specification<Group> groupSpec = new Specification<Group>() {

            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate notDelete = cb.notEqual(root.get("groupState").as(Integer.class),
                        GroupConsts.State.deleted.getCode());
                Predicate parent = cb.equal(root.get("department").as(Department.class), department);
                Predicate predicate = cb.and(notDelete, parent);

                if (keyword != null && keyword.length() > 0 && !includeUser) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + keyword + "%");
                    Predicate p2 = cb.like(root.get("pkGid").as(String.class), "%" + keyword + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2));
                }
                return predicate;
            }
        };
        List<GroupNode> groupNodes = new ArrayList<GroupNode>();
        List<Group> groups = groupDao.findAll(groupSpec, new Sort(Sort.Direction.ASC, "name"));
        if (groups != null) {
            for (Group group : groups) {
                // 无keyword筛选时或者筛选组时必须装入此节点。如果有keyword筛选用户时有子节点才装入
                boolean needInstallNode = (keyword == null || keyword.isEmpty() || !includeUser);
                GroupNode groupNode = new GroupNode();
                groupNode.setGid(group.getPkGid());
                groupNode.setName(group.getName());
                // 如果需要包含用户，装填组的用户节点。
                if (includeUser) {
                    List<UserSimpleInfo> clildUsers = installUserNode(group, fileLevel, keyword);
                    // if (!clildUsers.isEmpty()) {
                    groupNode.setChildUserList(clildUsers);
                    if (keyword != null && keyword.length() > 0 && !clildUsers.isEmpty()) {
                        // 如果需要keyword筛选并且有匹配的子节点，装入此节点
                        needInstallNode |= true;
                    }
                    // }
                }
                if (needInstallNode) {
                    groupNodes.add(groupNode);
                }
            }
        }
        return groupNodes;
    }

    private List<UserSimpleInfo> installUserNode(Department department, Integer fileLevel, String keyword) {
        // 筛选部门内用户
        Specification<User> userSpec = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate notDelete = cb.notEqual(root.get("userState").as(Integer.class),
                        UserConsts.State.deleted.getCode());
                Join<User, Department> join = root.join("userToDepartments", JoinType.INNER).join("department",
                        JoinType.INNER);
                Predicate parent = cb.equal(join.get("pkDid"), department.getPkDid());
                Predicate predicate = cb.and(notDelete, parent);

                if (fileLevel != null) {
                    List<FileLevelAccessPolicy> userLevels = fileLevelAccessPolicyDao
                            .findByFileLevelAndEnable(fileLevel, 1);
                    List<Predicate> list = new ArrayList<Predicate>();
                    In<Integer> in = cb.in(root.get("userLevel").as(Integer.class));
                    for (FileLevelAccessPolicy item : userLevels) {
                        in.value(item.getUserLevel());
                    }
                    list.add(in);
                    predicate = cb.and(predicate, cb.and(list.toArray(new Predicate[list.size()])));
                }

                if (keyword != null && keyword.length() > 0) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + keyword + "%");
                    Predicate p2 = cb.like(root.get("account").as(String.class), "%" + keyword + "%");
                    Predicate p3 = cb.like(root.get("pkUid").as(String.class), "%" + keyword + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2, p3));
                }
                return predicate;
            }
        };
        List<UserSimpleInfo> userNodes = new ArrayList<UserSimpleInfo>();
        List<User> users = userDao.findAll(userSpec, new Sort(Sort.Direction.ASC, "account"));
        if (users != null) {
            for (User user : users) {
                UserSimpleInfo userNode = new UserSimpleInfo();
                userNode.setAccount(user.getAccount());
                userNode.setLevel(user.getUserLevel());
                userNode.setName(user.getName());
                userNode.setOnline(UserConsts.userOnline(user));
                userNode.setUid(user.getPkUid());
                userNodes.add(userNode);
            }
        }
        return userNodes;
    }

    private List<UserSimpleInfo> installUserNode(Group group, Integer fileLevel, String keyword) {
        // 筛选组内用户
        Specification<User> userSpec = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate notDelete = cb.notEqual(root.get("userState").as(Integer.class),
                        UserConsts.State.deleted.getCode());
                Join<User, Group> join = root.join("userToGroups", JoinType.INNER).join("group", JoinType.INNER);
                Predicate parent = cb.equal(join.get("pkGid"), group.getPkGid());
                Predicate predicate = cb.and(notDelete, parent);

                if (fileLevel != null) {
                    List<FileLevelAccessPolicy> userLevels = fileLevelAccessPolicyDao
                            .findByFileLevelAndEnable(fileLevel, 1);
                    List<Predicate> list = new ArrayList<Predicate>();
                    In<Integer> in = cb.in(root.get("userLevel").as(Integer.class));
                    for (FileLevelAccessPolicy item : userLevels) {
                        in.value(item.getUserLevel());
                    }
                    list.add(in);
                    predicate = cb.and(predicate, cb.and(list.toArray(new Predicate[list.size()])));
                }

                if (keyword != null && keyword.length() > 0) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + keyword + "%");
                    Predicate p2 = cb.like(root.get("account").as(String.class), "%" + keyword + "%");
                    Predicate p3 = cb.like(root.get("pkUid").as(String.class), "%" + keyword + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2, p3));
                }
                return predicate;
            }
        };
        List<UserSimpleInfo> userNodes = new ArrayList<UserSimpleInfo>();
        List<User> users = userDao.findAll(userSpec, new Sort(Sort.Direction.ASC, "account"));
        if (users != null) {
            for (User user : users) {
                UserSimpleInfo userNode = new UserSimpleInfo();
                userNode.setAccount(user.getAccount());
                userNode.setLevel(user.getUserLevel());
                userNode.setName(user.getName());
                userNode.setOnline(UserConsts.userOnline(user));
                userNode.setUid(user.getPkUid());
                userNodes.add(userNode);
            }
        }
        return userNodes;
    }

}
