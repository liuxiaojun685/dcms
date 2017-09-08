package cn.bestsec.dcms.platform.impl.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Role_UpdateScopeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Role_UpdateScopeRequest;
import cn.bestsec.dcms.platform.api.model.Role_UpdateScopeResponse;
import cn.bestsec.dcms.platform.api.model.ScopeSimpleInfo;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月27日 上午11:34:59
 */
@Component
public class RoleUpdateScope implements Role_UpdateScopeApi {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleScopeDao roleScopeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Role_UpdateScopeResponse execute(Role_UpdateScopeRequest role_UpdateScopeRequest) throws ApiException {
        Role role = roleDao.findById(role_UpdateScopeRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        AdminLogBuilder adminLogBuilder = role_UpdateScopeRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.role_scope)
                .admin(role_UpdateScopeRequest.tokenWrapper().getAdmin()).operateDescription(
                        role.getUserByFkUid().getName(), RoleConsts.Type.parse(role.getRoleType()).getDescription());

        StringBuilder scopeBuiler = new StringBuilder();
        List<ScopeSimpleInfo> scopeList = role_UpdateScopeRequest.getScopeList();
        if (scopeList != null) {
            List<RoleScope> scopes = roleScopeDao.findByRole(role);
            if (scopes != null) {
                roleScopeDao.delete(scopes);
            }
            scopes = new ArrayList<RoleScope>();
            for (ScopeSimpleInfo scopeInfo : scopeList) {
                Integer varType = scopeInfo.getVarType();
                String varId = scopeInfo.getVarId();
                String varName = null;
                if (varType == 1) {
                    // 用户
                    User obj = userDao.findByPkUid(varId);
                    if (obj != null) {
                        scopeBuiler.append(obj.getName() + " ");
                        varName = obj.getName();
                    }
                } else if (varType == 2) {
                    // 组
                    Group obj = groupDao.findByPkGid(varId);
                    if (obj != null) {
                        scopeBuiler.append(obj.getName() + " ");
                        varName = obj.getName();
                    }
                } else if (varType == 3) {
                    // 部门
                    Department obj = departmentDao.findByPkDid(varId);
                    if (obj != null) {
                        scopeBuiler.append(obj.getName() + " ");
                        varName = obj.getName();
                    }
                }
                if (varName != null) {
                    scopes.add(new RoleScope(role, varId, varType, varName));
                }
            }
            roleScopeDao.save(scopes);
        }

        Role_UpdateScopeResponse resp = new Role_UpdateScopeResponse();
        resp.setRoleDispScope(scopeBuiler.toString());
        return resp;
    }

}
