package cn.bestsec.dcms.platform.impl.role;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_CheckRoleScopeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Role_CheckRoleScopeRequest;
import cn.bestsec.dcms.platform.api.model.Role_CheckRoleScopeResponse;
import cn.bestsec.dcms.platform.api.model.ScopeInfo;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 检测缺少责任人的部门
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月28日 下午5:45:40
 */
@Component
public class RoleCheckRoleScope implements Role_CheckRoleScopeApi {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Role_CheckRoleScopeResponse execute(Role_CheckRoleScopeRequest role_CheckRoleScopeRequest)
            throws ApiException {

        // 获取所有定密责任角色
        List<Role> findByRoleType = null;
        if (role_CheckRoleScopeRequest.getRoleType() == RoleConsts.Type.makeSecret.getCode()) {
            findByRoleType = roleDao.findByRoleType(RoleConsts.Type.makeSecret.getCode());

        }
        // 获取所有签发角色
        if (role_CheckRoleScopeRequest.getRoleType() == RoleConsts.Type.dispatchman.getCode()) {
            findByRoleType = roleDao.findByRoleType(RoleConsts.Type.dispatchman.getCode());

        }

        // 所有已经配置了定密责任人的部门id
        List<String> scopes = new ArrayList<>();
        for (Role role : findByRoleType) {
            List<RoleScope> roleScopes = role.getRoleScopes();
            for (RoleScope roleScope : roleScopes) {
                scopes.add(roleScope.getFkVarId());
            }
        }

        // if所有部门都没有配置责任人
        if (scopes == null || scopes.isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        if (scopes.contains("did-root")) {
            return new Role_CheckRoleScopeResponse(new ArrayList<>());

        }
        // 所有部门的子部门
        List<String> scopeAll = new ArrayList<>();
        for (String did : scopes) {
            securityPolicyService.childDepartment(did, scopeAll);
        }
        // 根据得到的部门筛选没有责任人的部门
        List<Department> departments = departmentDao
                .findByDepartmentStateAndPkDidNotIn(DepartmentConsts.state.undelete.getCode(), scopeAll);
        List<ScopeInfo> checkList = new ArrayList<>();
        for (Department department : departments) {
            ScopeInfo scopeInfo = new ScopeInfo();
            scopeInfo.setVarId(department.getPkDid());
            scopeInfo.setVarName(department.getName());
            scopeInfo.setVarType(3);
            checkList.add(scopeInfo);
        }
        Role_CheckRoleScopeResponse resp = new Role_CheckRoleScopeResponse();
        resp.setCheckList(checkList);

        return resp;
    }

}
