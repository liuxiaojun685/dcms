package cn.bestsec.dcms.platform.impl.role;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_QueryScopeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Role_QueryScopeRequest;
import cn.bestsec.dcms.platform.api.model.Role_QueryScopeResponse;
import cn.bestsec.dcms.platform.api.model.ScopeInfo;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月27日 上午11:35:13
 */
@Component
public class RoleQueryScope implements Role_QueryScopeApi {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleScopeDao roleScopeDao;

    @Override
    @Transactional
    public Role_QueryScopeResponse execute(Role_QueryScopeRequest role_QueryScopeRequest) throws ApiException {
        Role role = roleDao.findById(role_QueryScopeRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        List<ScopeInfo> scopeList = new ArrayList<ScopeInfo>();
        List<RoleScope> scopes = roleScopeDao.findByRole(role);
        if (scopes != null) {
            for (RoleScope scope : scopes) {
                ScopeInfo scopeInfo = new ScopeInfo();
                scopeInfo.setVarId(scope.getFkVarId());
                scopeInfo.setVarType(scope.getVarType());
                scopeInfo.setVarName(scope.getVarName());
                scopeList.add(scopeInfo);
            }
        }

        Role_QueryScopeResponse resp = new Role_QueryScopeResponse();
        resp.setScopeList(scopeList);
        return resp;
    }

}
