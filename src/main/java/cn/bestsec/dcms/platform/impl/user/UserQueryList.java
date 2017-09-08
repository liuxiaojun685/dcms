
package cn.bestsec.dcms.platform.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.UserInfo;
import cn.bestsec.dcms.platform.api.model.User_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.User_QueryListResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.impl.admin.AdminLogin;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 查询用户列表
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月25日下午4:32:34
 */
@Component
public class UserQueryList implements User_QueryListApi {
    private static final Logger log = LoggerFactory.getLogger(AdminLogin.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;

    private List<Department> getChildDepartment(Department department) {
        List<Department> ret = new ArrayList<Department>();
        List<Department> childs = departmentDao.findByFkParentId(department.getPkDid());
        ret.addAll(childs);
        for (Department child : childs) {
            ret.addAll(getChildDepartment(child));
        }
        return ret;
    }

    @Transactional
    @Override
    public User_QueryListResponse execute(User_QueryListRequest user_QueryListRequest) throws ApiException {
        Specification<User> spec = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (user_QueryListRequest.getGid() != null && !user_QueryListRequest.getGid().isEmpty()) {
                    Join<User, Group> join = root.join("userToGroups", JoinType.LEFT).join("group", JoinType.LEFT);
                    list.add(cb.equal(join.get("pkGid"), user_QueryListRequest.getGid()));

                }

                if (user_QueryListRequest.getDid() != null && !user_QueryListRequest.getDid().isEmpty()) {
                    Join<User, Department> join = root.join("userToDepartments", JoinType.LEFT).join("department",
                            JoinType.LEFT);
                    Department dep = departmentDao.findByPkDid(user_QueryListRequest.getDid());
                    List<Department> childs = getChildDepartment(dep);
                    childs.add(dep);
                    In<String> in = cb.in(join.get("pkDid").as(String.class));
                    for (Department item : childs) {
                        in.value(item.getPkDid());
                    }
                    list.add(in);
                }

                if (user_QueryListRequest.getLevel() != null) {

                    list.add(cb.equal(root.get("userLevel").as(Integer.class), user_QueryListRequest.getLevel()));

                }
                if (user_QueryListRequest.getState() != null) {
                    list.add(cb.equal(root.get("userState").as(Integer.class), user_QueryListRequest.getState()));
                }
                if (user_QueryListRequest.getOnlineState() != null) {

                    if (user_QueryListRequest.getOnlineState() == UserConsts.OnlineState.online.getCode()) {
                        list.add(cb.gt(root.get("heartbeatTime").as(Long.class),
                                System.currentTimeMillis() - SystemProperties.getInstance().getUserOfflineTime()));

                    } else {
                        list.add(cb.le(root.get("heartbeatTime").as(Long.class),
                                System.currentTimeMillis() - SystemProperties.getInstance().getUserOfflineTime()));
                    }
                }
                if (user_QueryListRequest.getCreateFrom() != null) {
                    list.add(cb.equal(root.get("createFrom").as(Integer.class), user_QueryListRequest.getCreateFrom()));
                }
                // 筛选userState不等于已删除的
                list.add(cb.notEqual(root.get("userState").as(Integer.class), UserConsts.State.deleted.getCode()));

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));

                if (user_QueryListRequest.getKeyword() != null && !user_QueryListRequest.getKeyword().isEmpty()) {
                    Predicate p1 = cb.like(root.get("name").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");
                    Predicate p2 = cb.like(root.get("mail").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");
                    Predicate p3 = cb.like(root.get("position").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");
                    Predicate p4 = cb.like(root.get("account").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");
                    Predicate p5 = cb.like(root.get("pkUid").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");

                    Join<User, Group> join1 = root.join("userToGroups", JoinType.LEFT).join("group", JoinType.LEFT);
                    Predicate p6 = cb.like(join1.get("name").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");

                    Join<User, Department> join2 = root.join("userToDepartments", JoinType.LEFT).join("department",
                            JoinType.LEFT);
                    Predicate p7 = cb.like(join2.get("name").as(String.class),
                            "%" + user_QueryListRequest.getKeyword() + "%");

                    predicate = cb.and(predicate, cb.or(p1, p2, p3, p4, p5, p7));
                }

                return predicate;
            }

        };

        Pageable pageable = new PageRequest(user_QueryListRequest.getPageNumber(), user_QueryListRequest.getPageSize(),
                Sort.Direction.ASC, "account");
        Page<User> page = userDao.findAll(spec, pageable);
        List<User> users = page.getContent();
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = null;
        for (User user : users) {
            userInfo = new UserInfo();
            userInfo.setAccount(user.getAccount());
            userInfo.setCreateFrom(user.getCreateFrom());
            userInfo.setCreateTime(user.getCreateTime());
            if (!user.getUserToDepartments().isEmpty()) {
                Department dep = user.getUserToDepartments().iterator().next().getDepartment();
                if (dep != null) {
                    userInfo.setDepartmentName(dep.getName());
                }
            }

            userInfo.setFirstLoginTime(user.getFirstLoginTime());
            userInfo.setGender(user.getGender());
            userInfo.setLastLoginAddress(user.getLastLoginIp());
            userInfo.setLastLoginTime(user.getLastLoginTime());
            userInfo.setLastLoginType(user.getLastLoginType());
            userInfo.setLevel(user.getUserLevel());
            userInfo.setMail(user.getMail());
            userInfo.setName(user.getName());
            userInfo.setOnline(UserConsts.userOnline(user));
            userInfo.setPasswdState(user.getPasswdState());
            userInfo.setPhone(user.getPhone());
            userInfo.setPosition(user.getPosition());
            userInfo.setState(user.getUserState());
            userInfo.setUid(user.getPkUid());
            userInfo.setIsHaveRole(0);
            // 管理员角色
            Admin admin = adminDao.findByAccountAndAdminStateNot(user.getAccount(),
                    AdminConsts.AdminState.deleted.getCode());
            if (admin != null) {
                userInfo.setIsHaveRole(1);
                userInfo.setAdminType(admin.getAdminType());
            }
            // 该用户是否存在配置
            List<Role> rolesForFkUid = user.getRolesForFkUid();
            if (rolesForFkUid == null) {
                rolesForFkUid = new ArrayList<>();
            }
            List<Role> rolesForFkAgent = user.getRolesForFkAgentUid();
            if (rolesForFkAgent != null && rolesForFkAgent.size() > 0) {
                for (Role role : rolesForFkAgent) {
                    rolesForFkUid.add(role);
                }
            }

            List<WorkflowPolicyStep> rolesForFkUids = workflowPolicyStepDao.findByRoleIn(rolesForFkUid);
            if (rolesForFkUids != null && rolesForFkUids.size() > 0) {
                userInfo.setIsHaveRole(1);
            }
            userInfos.add(userInfo);
        }

        User_QueryListResponse resp = new User_QueryListResponse();
        resp.setTotalElements((int) page.getTotalElements());
        resp.setTotalPages(page.getTotalPages());
        resp.setUserList(userInfos);

        return resp;
    }
}
