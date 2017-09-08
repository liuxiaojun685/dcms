package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_CheckWorkFlowApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ScopeInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_CheckWorkFlowRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_CheckWorkFlowResponse;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 检查没有审批人的部门
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月29日 下午3:19:51
 */
@Component
public class SecurePolicyCheckWorkFlow implements SecurePolicy_CheckWorkFlowApi {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public SecurePolicy_CheckWorkFlowResponse execute(
            SecurePolicy_CheckWorkFlowRequest securePolicy_CheckWorkFlowRequest) throws ApiException {

        // 所有已经配置了审批人的部门id
        List<String> scopes = new ArrayList<>();
        List<Integer> roleIds = securePolicy_CheckWorkFlowRequest.getRoleId();
        for (Integer roleId : roleIds) {
            Role role = roleDao.findById(roleId);
            List<RoleScope> roleScopes = role.getRoleScopes();
            for (RoleScope roleScope : roleScopes) {
                scopes.add(roleScope.getFkVarId());
            }
        }

        if (scopes.contains("did-root")) {
            return new SecurePolicy_CheckWorkFlowResponse(new ArrayList<>());

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
        SecurePolicy_CheckWorkFlowResponse resp = new SecurePolicy_CheckWorkFlowResponse();
        resp.setCheckList(checkList);
        return resp;
    }

}
