package cn.bestsec.dcms.platform.impl.role;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.RoleInfo;
import cn.bestsec.dcms.platform.api.model.RoleLevelInfo;
import cn.bestsec.dcms.platform.api.model.Role_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Role_QueryListResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 查询角色列表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 下午2:16:01
 */
@Component
public class RoleQueryList implements Role_QueryListApi {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public Role_QueryListResponse execute(Role_QueryListRequest role_QueryListRequest) throws ApiException {
        Role_QueryListResponse resp = new Role_QueryListResponse();

        // 分页带参数查询
        Specification<Role> spec = new Specification<Role>() {

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (role_QueryListRequest.getRoleType() != null) {
                    list.add(cb.equal(root.get("roleType").as(Integer.class), role_QueryListRequest.getRoleType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        List<Role> roleList = roleDao.findAll(spec);
        List<RoleInfo> infos = new ArrayList<RoleInfo>();
        RoleInfo roleInfo = null;
        for (Role role : roleList) {
            if (role_QueryListRequest.getRoleLevel() != null && ((role.getFileLevel() >> role_QueryListRequest.getRoleLevel()) & 1) == 0) {
                continue;
            }
            roleInfo = new RoleInfo();
            // 获取角色对应的用户
            User user = role.getUserByFkUid();
            roleInfo.setAccount(user.getAccount());
            roleInfo.setLevel(user.getUserLevel());
            roleInfo.setName(user.getName());
            roleInfo.setOnline(UserConsts.userOnline(user));
            roleInfo.setUid(user.getPkUid());
            roleInfo.setRoleId(role.getId());
            roleInfo.setRoleType(role.getRoleType());
            
            Integer fileLevel = role.getFileLevel();
            List<RoleLevelInfo> levelInfos = new ArrayList<RoleLevelInfo>();
            List<Integer> supportLevel = securityPolicyService.getSupportFileLevel(user.getUserLevel());
            for (int level = FileConsts.Level.open.getCode(); level <= FileConsts.Level.topSecret.getCode(); level++) {
                RoleLevelInfo levelInfo = new RoleLevelInfo(level, 0, 0);
                if (supportLevel.contains(level)) {
                    levelInfo.setEnable((fileLevel >> level) & 1);
                    levelInfo.setCare(1);
                }
                levelInfos.add(levelInfo);
            }
            roleInfo.setRoleLevelList(levelInfos);
            List<RoleScope> scopes = role.getRoleScopes();
            if (scopes != null) {
                StringBuilder scopeBuiler = new StringBuilder();
                for (RoleScope scope : scopes) {
                    scopeBuiler.append(scope.getVarName() + " ");
                }
                roleInfo.setRoleDispScope(scopeBuiler.toString());
            }
            infos.add(roleInfo);
        }
        // 响应数据
        resp.setRoleList(infos);
        return resp;
    }

}
